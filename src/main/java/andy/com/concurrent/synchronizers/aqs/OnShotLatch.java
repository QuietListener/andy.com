package andy.com.concurrent.synchronizers.aqs;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 实现一个Latch，所有线程调用 sync.await 会阻塞，直到某个线程调用sync.signal
 */
class OnShotLatch {
    private final Sync sync = new Sync();

    public void await() throws InterruptedException {
        sync.acquireShared(0);
    }

    public void signal() {
        sync.releaseShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {

        /**
         * 被 acquireShared 调用
         *
         * @param ignored
         * @return 小于0表示获取失败阻塞；0表示成功，后续获取操作会失败；大于0表示成功，后续获取操作也会成功
         */
        protected int tryAcquireShared(int ignored) {
            //-1活阻塞，1会成功
            return (getState() == 1) ? 1 : -1;
        }

        /**
         * 被releaseShared调用
         *
         * @param ignored
         * @return
         */
        protected boolean tryReleaseShared(int ignored) {
            setState(1); //闭锁打开
            return true; //现在其他线程可以取得闭锁
        }
    }

    public static void main(String[] args) {

        final OnShotLatch latch = new OnShotLatch();

        for (int i = 0; i < 5; i++) {

            Thread t = new Thread() {

                @Override
                public void run() {

                    try {
                        System.out.println(getName() + ":" + new Date().getTime() + ": wait for job");
                        latch.await();

                        System.out.println(getName() + ":" + new Date().getTime() + ": doing a job");
                        TimeUnit.SECONDS.sleep(3);

                        System.out.println(getName() + ":" + new Date().getTime() + ": finished a job");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            t.setName("t-" + i);
            t.start();
        }


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("打开闭锁开始工作");
        latch.signal();

    }
}
