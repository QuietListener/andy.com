/**
 * 一个锁和多个Condition(条件队列)可以更精细的控制并发
 */

package andy.com.concurrent.sync.cp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestConsumerProducer_Lock {

    private static final Lock lock = new ReentrantLock();
    //用于放producer线程的条件队列
    private static final Condition producerCondition = lock.newCondition();
    //用于放consumer线程的条件队列
    private static final Condition consumerCondition = lock.newCondition();


    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        List<String> pool = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            new ConsumerL(pool, "consumer-" + i).start();
        }

        TimeUnit.SECONDS.sleep(5);

        for (int i = 0; i < 2; i++) {
            new ProducerL(pool, "producer-" + i).start();
        }

        TimeUnit.SECONDS.sleep(60 * 5);
    }


    /**
     * 生产者 往pool里面添加数据
     *
     * @author Admin
     */
    static class ConsumerL extends Thread {
        private List<String> pool = null;

        public ConsumerL(List<String> pool, String name) {
            this.pool = pool;
            this.setName(name);

            if (this.pool == null)
                throw new AssertionError("pool is null");
        }

        @Override
        public void run()  {
            while (true) {
                String i = null;
                lock.lock();
                try {
                    while (pool.size() == 0) {
                        System.out.println(this.getName() + " pool is empty waitting ");
                        //只在consumer 条件队列中等待
                        consumerCondition.await(); //
                    }

                    i = pool.remove(0);

                    //只唤醒Producer中的线程
                    producerCondition.signalAll();
                }
                catch (Exception e){ e.printStackTrace();}
                finally { lock.unlock(); }

                System.out.println(this.getName() + " consume " + i);

                //随机睡几秒
                sleepMs();
            }

        }

        private static void sleepMs() {
            try {
                TimeUnit.SECONDS.sleep(2 + new Random().nextInt(5));
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }


    /**
     * 生产者 往pool里面添加数据
     *
     * @author Admin
     */
    static class ProducerL extends Thread {
        //max size of pool
        private static final int MAX_SIZE = 3;

        //shared pool
        private List<String> pool = null;

        public ProducerL(List<String> pool, String name) {
            this.pool = pool;
            this.setName(name);

            if (this.pool == null)
                throw new AssertionError("pool is null");
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (pool.size() == MAX_SIZE) {
                        System.out.println(this.getName() + " pool is full(" + pool.size() + ") waitting ");
                        //只在producer 条件队列中等待
                        producerCondition.await();
                    }

                    String i = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
                    pool.add(i);

                    //只唤醒生产者线程
                    consumerCondition.signalAll();
                    System.out.println(this.getName() + " produce(" + pool.size() + ") " + i);
                }
                catch (Exception e){ e.printStackTrace();}
                finally { lock.unlock(); }
                sleepMs();
            }
        }


        private static void sleepMs() {
            try {
                TimeUnit.SECONDS.sleep(2 + new Random().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
