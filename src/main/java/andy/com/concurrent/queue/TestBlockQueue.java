package andy.com.concurrent.queue;

import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestBlockQueue {

    static boolean running = true;

    static Queue<RetryKey> delFailedKeys = new LinkedBlockingQueue<>(1000);
    static  RetryThread t = new RetryThread();

    public static void main(String[] args) {
        t.start();
        ;
        for (int i = 0; i < 100000; i++) {
            RetryKey key = new RetryKey("key" + i, System.currentTimeMillis() + 300 + new Random().nextInt(100));
            System.out.println("add " + key);
            delFailedKeys.add(key);

            if (i % 100 == 0 && i > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                }catch (Exception e){

                }
            }
        }
    }

    private static class RetryThread extends Thread {
        @Override
        public void run() {
            while (running) {
                try {
                    Iterator<RetryKey> i = delFailedKeys.iterator();
                    while (i.hasNext()) {
                        RetryKey retryKey = i.next();
                        try {
                            if (System.currentTimeMillis() > retryKey.getRetryTime()) {
                                System.out.println("RetryThread" + retryKey.toString() + " stared");
                                System.out.println("del " + retryKey);
                                TimeUnit.MILLISECONDS.sleep(100);
                                System.out.println("RetryThread" + retryKey.toString() + " ended");
                                i.remove();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    TimeUnit.MILLISECONDS.sleep(100);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        }
    }

    private static class RetryKey {

        private String key;
        private long retryTime;

        RetryKey(String key, long retryTime) {
            this.key = key;
            this.retryTime = retryTime;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getRetryTime() {
            return retryTime;
        }

        public void setRetryTime(long retryTime) {
            this.retryTime = retryTime;
        }

        @Override
        public String toString() {
            return "RetryKey{" +
                    "key='" + key + '\'' +
                    ", retryTime=" + retryTime +
                    '}';
        }
    }
}
