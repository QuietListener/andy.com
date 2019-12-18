package andy.com.springCloud.hystrix.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 使用 fallback实现降级
 * 在执行 run的时候，除了抛出HystrixBadRequestException ，其他异常都会被看做是failure并触发getFallback方法，
 * 如果想要上层知道出了了异常，而不计入failure，可以将异常包装成HystrixBadRequestException再抛出。
 */
public class CommandTestParamExecution extends HystrixCommand<String> {

    private String name;
    private long timeoutMs = 1000;
    private long exeTimeoutMs = timeoutMs;
    public static final int IsolationStrategyThread = 1;
    public static final int IsolationStrategySemophore = 2;

    public static final String PrefixFallback = "hello failure ";
    public static final String Prefix = "hello ";

    /**
     * @param name
     * @param isolationStrategy
     * @param timeoutMs
     * @param exeTimeoutMs      执行时间
     */
    public CommandTestParamExecution(String name, int isolationStrategy, int timeoutMs, int exeTimeoutMs) {

        this(name, isolationStrategy, timeoutMs, exeTimeoutMs, 10, 10);

    }

    public CommandTestParamExecution(String name, int isolationStrategy, int timeoutMs, int exeTimeoutMs, int semaphoreMaxConcurrentRequests, int allbackIsolationSemaphoreMaxConcurrentRequests) {


        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()

                        /**execution*/
                        .withExecutionIsolationStrategy(isolationStrategy == IsolationStrategyThread ? HystrixCommandProperties.ExecutionIsolationStrategy.THREAD : HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE) //线程池还是信号量隔离
                        .withExecutionTimeoutInMilliseconds(timeoutMs) //run方法timeout时间
                        .withExecutionTimeoutEnabled(true)//hystrix.command.default.execution.timeout.enabled 是否开启timeout
                        .withExecutionIsolationThreadInterruptOnTimeout(true)// hystrix.command.HystrixCommandKey.execution.isolation.thread.interruptOnTimeout 当timeout时候是否应该中断HystrixCommand.run()
                        .withExecutionIsolationThreadInterruptOnFutureCancel(true)
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(semaphoreMaxConcurrentRequests)  //信号量的上线。

                        /**fallback*/
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(allbackIsolationSemaphoreMaxConcurrentRequests) //isolation为Semaphore时,Caller执行返回Fallback的上限


                        /**Circuit Breaker*/
                        .withCircuitBreakerEnabled(true)
                        //在一个窗口期内,激活断路器的最小请求量，比如设置为30，但是只有29个请求，就算这19个请求全部挂了，都不会断路.默认20个
                        .withCircuitBreakerRequestVolumeThreshold(20)
                        //当断路发生时候，会拒绝请求，睡眠一段时间再去请求看服务 是否恢复。这个参数就是这个时间间隔。 默认5000毫秒
                        .withCircuitBreakerSleepWindowInMilliseconds(2000)
                        //当错误率高于这个数，就会断路执行fallback中的逻辑
                        .withCircuitBreakerErrorThresholdPercentage(60)


                ).andThreadPoolPropertiesDefaults( //线程池配置
                        HystrixThreadPoolProperties.Setter().withCoreSize(1).withMaxQueueSize(1)
                )
        );

        this.timeoutMs = timeoutMs;
        this.exeTimeoutMs = exeTimeoutMs;
        this.name = name;
    }


    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(exeTimeoutMs);
        return Prefix + name;
    }

    /**
     * 如果抛出异常会默认返回这个数据
     *
     * @return
     */
    @Override
    public String getFallback() {
        return PrefixFallback + name;
    }

    static public class TestCommandHelloWorldFallback {

        @Test
        public void testTread() {
            int testTimeOutMs = 200;
            String name = "junjun";

            //正常执行
            String s = new CommandTestParamExecution(name, IsolationStrategyThread, testTimeOutMs, testTimeOutMs - 80).execute();
            System.out.println(s);
            assert s.equals(Prefix + name);

            //会timeout 返回getFallback中的内容
            s = new CommandTestParamExecution(name, IsolationStrategyThread, testTimeOutMs, testTimeOutMs + 1).execute();
            System.out.println(s);
            assert s.equals(PrefixFallback + name);
        }

        @Test
        public void testSemophore() {
            int testTimeOutMs = 200;
            String name = "junjun";

            //正常执行
            String s = new CommandTestParamExecution(name, IsolationStrategySemophore, testTimeOutMs, testTimeOutMs - 80).execute();
            System.out.println(s);
            assert s.equals(Prefix + name);

            //会timeout 返回getFallback中的内容
            s = new CommandTestParamExecution(name, IsolationStrategySemophore, testTimeOutMs, testTimeOutMs + 50).execute();
            System.out.println(s);
            assert s.equals(PrefixFallback + name);

        }


        @Test
        public void testExecutionIsolationSemaphoreMaxConcurrentRequests() throws Exception {
            int testTimeOutMs = 200;
            String name = "junjun";

            CyclicBarrier barrier = new CyclicBarrier(5);
            for (int i = 0; i < 5; i++) {
                Thread t = new Thread() {

                    @Override
                    public void run() {
                        try {
                            barrier.await();
                            TimeUnit.MILLISECONDS.sleep(20 + new Random().nextInt(40));
                            //sempphore 为4，最多只能有4个执行成功，有一个会挂掉
                            String s = new CommandTestParamExecution(name, IsolationStrategySemophore, testTimeOutMs, testTimeOutMs - 120, 4, 5).execute();
                            System.out.println(getName() + ":" + s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.setName("ExecutionIsolationSemaphoreMaxConcurrentRequests-" + i);
                t.start();
            }

            TimeUnit.SECONDS.sleep(3);
        }


        @Test
        public void testCircuitBreaker() throws Exception {
            int testTimeOutMs = 200;
            String name = "junjun";

            for (int i = 0; i < 40; i++) {

                final int j = i;
                Thread t = new Thread() {

                    @Override
                    public void run() {
                        try {
                            int time = 0;
                            int exeTime = 0;
                            if(j <= 5){
                                time = j*(testTimeOutMs+100);
                                exeTime = testTimeOutMs / 2;
                            }
                            else if(j > 5 && j < 20){
                                time = 5*(testTimeOutMs+100)+(j-5)*3;
                                exeTime = testTimeOutMs + 10;
                            }
                            else {
                                time = j*(testTimeOutMs+100);
                                exeTime = testTimeOutMs /2;
                            }
                            TimeUnit.MILLISECONDS.sleep(time);


                            CommandTestParamExecution cmd = new CommandTestParamExecution(name, IsolationStrategySemophore, testTimeOutMs, exeTime, 5, 5000);
                            String s = cmd.execute();
                            System.out.println(getName() + ":" + s+ "   cmd: isCircuitBreakerOpen="+cmd.isCircuitBreakerOpen()+"  metrics:"+cmd.getMetrics().getHealthCounts());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                t.setName("t"+i);
                t.start();
            }

            TimeUnit.SECONDS.sleep(20);
        }

    }


}


