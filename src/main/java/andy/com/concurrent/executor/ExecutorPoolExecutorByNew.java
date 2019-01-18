package andy.com.concurrent.executor;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorPoolExecutorByNew {

    private static ThreadPoolExecutor getPool() {

        /**
         * 1.maxPoolSize的选择
         * cpu密集型一般用 cpu核数+1
         * io密集型一般用  cpu核数*2+1
         * cpu和io都有的话用 （（io时间+cpu时间)/cpu时间 ） * cpu核数
         *
         * 2. keepAliveTime 如果在这么久的时间内没有执行新的任务，线程将会被杀掉
         *
         * 3.RejectedExecutionHandler
         *   当线程数达到maxPoolSize 并且 queue也满了之后，新加入的任务如何"拒绝".
         */

        int cpuCoreSize = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = cpuCoreSize * 2 + 1;
        int keepAliveTimeS = 60;
        int queueCapacity = 30;

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                cpuCoreSize,
                maxPoolSize,
                keepAliveTimeS,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueCapacity),
                new ThreadPoolExecutor.AbortPolicy()
        );

        return pool;
    }

    public static void main(String [] args){
        ThreadPoolExecutor pool = getPool();
        for(int i = 0; i < 100;i++){
            try {
                int j = i;
                pool.submit(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println(j+ Thread.currentThread().getName());
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        try {
            pool.shutdown();
            pool.awaitTermination(2, TimeUnit.SECONDS);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
