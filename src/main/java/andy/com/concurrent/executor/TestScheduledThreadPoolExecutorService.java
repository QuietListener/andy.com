package andy.com.concurrent.executor;

import org.junit.Test;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService 继承了 Executor 为Executor提供了生命周期管理
 * shutdown()
 * shutdownNow()
 * isShutdown()
 * isTerminated()
 * awaitTermination(long, TimeUnit)
 * submit(Callable<T>)
 * submit(Runnable, T)
 * submit(Runnable)
 * invokeAll(Collection<? extends Callable<T>>)
 * invokeAll(Collection<? extends Callable<T>>, long, TimeUnit)
 * invokeAny(Collection<? extends Callable<T>>)
 * invokeAny(Collection<? extends Callable<T>>, long, TimeUnit)
 *
 * @author Admin
 */

public class TestScheduledThreadPoolExecutorService {

    //线程池
    static ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(2, new ScheduledThreadPoolExecutor.DiscardPolicy());

    /**
     * 定时任务
     */
    @Test
    public void testSchedule() {

        for (int i = 0; i < 40; i++) {
            long now = System.currentTimeMillis();
            int delay = new Random().nextInt(1000) + 1000;
            Task t = new Task("task:" + now + ":" + delay);
            ScheduledFuture f = pool.schedule(t, delay, TimeUnit.MILLISECONDS);
            System.out.println("add task:" + t.name);
        }

        close();
    }


    @Test
    public void testScheduleAtFixRate() {

        int delay = new Random().nextInt(1000) + 1000;
        long now = System.currentTimeMillis();
        Task t1 = new Task("task:" + now + ":" + 1);
        Task t2 = new Task("task:" + now + ":" + 2);
        ScheduledFuture f1 = pool.scheduleAtFixedRate(t1, 3000, 1000, TimeUnit.MILLISECONDS);
        ScheduledFuture f2 = pool.scheduleAtFixedRate(t2, 2000, 1000, TimeUnit.MILLISECONDS);

        /**
         * cancel a task
         */
        Thread removeThread = new Thread(){
            public void run(){
                try{
                    TimeUnit.SECONDS.sleep(5);
                }catch (Exception e){}

                System.out.println("remove "+ t1.name);
                f1.cancel(true);
            }
        };

        removeThread.start();
        close();
    }

    static void close() {
        try {
            pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
            System.out.println("all thread complete");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class Task implements Runnable {
        private String name = null;

        public Task(String name) {
            this.name = name;
        }

        public void run() {

            try {
                TimeUnit.MILLISECONDS.sleep(50);
                long interval = System.currentTimeMillis() - Long.parseLong(this.name.split(":")[1]);
                System.out.println(this.name + " finished interval:" + interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return Objects.equals(name, task.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}

