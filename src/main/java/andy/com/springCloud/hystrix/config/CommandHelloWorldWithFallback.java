package andy.com.springCloud.hystrix.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 使用 fallback实现降级
 * 在执行 run的时候，除了抛出HystrixBadRequestException ，其他异常都会被看做是failure并触发getFallback方法，
 * 如果想要上层知道出了了异常，而不计入failure，可以将异常包装成HystrixBadRequestException再抛出。
 */
public class CommandHelloWorldWithFallback extends HystrixCommand<String> {

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
    public CommandHelloWorldWithFallback(String name, int isolationStrategy, int timeoutMs, int exeTimeoutMs) {


        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(isolationStrategy == IsolationStrategyThread ? HystrixCommandProperties.ExecutionIsolationStrategy.THREAD : HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE) //线程池隔离
                        .withExecutionTimeoutInMilliseconds(timeoutMs)) //timeout
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
            String s = new CommandHelloWorldWithFallback(name, IsolationStrategyThread, testTimeOutMs, testTimeOutMs - 80).execute();
            System.out.println(s);
            assert s.equals(Prefix + name);

            //会timeout 返回getFallback中的内容
            s = new CommandHelloWorldWithFallback(name, IsolationStrategyThread, testTimeOutMs, testTimeOutMs + 1).execute();
            System.out.println(s);
            assert s.equals(PrefixFallback + name);

        }


        @Test
        public void testSemophore() {
            int testTimeOutMs = 200;
            String name = "junjun";

            //正常执行
            String s = new CommandHelloWorldWithFallback(name, IsolationStrategySemophore, testTimeOutMs, testTimeOutMs - 80).execute();
            System.out.println(s);
            assert s.equals(Prefix + name);

            //会timeout 返回getFallback中的内容
            s = new CommandHelloWorldWithFallback(name, IsolationStrategySemophore, testTimeOutMs, testTimeOutMs + 50).execute();
            System.out.println(s);
            assert s.equals(PrefixFallback + name);

        }
    }


}


