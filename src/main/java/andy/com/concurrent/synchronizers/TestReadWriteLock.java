package andy.com.concurrent.synchronizers;

import org.junit.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {

    static volatile boolean flag = true;
    static volatile int count = 1;
    static volatile int readActionCount = 1;
    static int countReader = 10;
    static int countWriter = 1;
    static final private CyclicBarrier barrier = new CyclicBarrier(countReader + countWriter);

    public void sleepMillis(int timeMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用普通锁 模拟读多写少的场景
     * 10个读线程线程，读操作耗时20ms
     * 1个写线程，写操作耗时80ms 每隔100毫秒一个写操作
     * 30秒可以并发读2000+次
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {

        final ReentrantLock lock = new ReentrantLock();

        //10个读线程
        for (int i = 0; i < countReader; i++) {
            Thread readThread1 = new Thread() {
                public void run() {
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(this.getName() + " started");
                    while (flag == true) {
                        lock.lock();
                        try {
                            readActionCount += 1;
                            System.out.println(this.getName() + " read:" + count + " readActionCount:" + readActionCount);
                            sleepMillis(20);
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            };

            readThread1.setName("read-" + i);
            readThread1.start();
        }

        //1个写线程
        for (int i = 0; i < countWriter; i++) {
            Thread writeThread1 = new Thread() {
                public void run() {
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(this.getName() + " started");
                    while (flag == true) {
                        lock.lock();
                        try {
                            count += 1;
                            System.out.println(this.getName() + "write:" + count);
                            sleepMillis(80);
                        } finally {
                            lock.unlock();
                        }

                        sleepMillis(100);
                    }
                }
            };

            writeThread1.setName("write-" + i);
            writeThread1.start();
        }


        System.out.println("main started");
        TimeUnit.SECONDS.sleep(30);
        flag = false;
    }


    /**
     * 使用读写说 模拟读多写少的场景
     * 10个读线程线程，读操作耗时20ms
     * 1个写线程，写操作耗时80ms 每隔100毫秒一个写操作
     * 30秒可以并发读7000+次
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {

        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        //10个读线程
        for (int i = 0; i < countReader; i++) {
            Thread readThread1 = new Thread() {
                public void run() {
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    while (flag == true) {
                        readLock.lock();
                        try {
                            readActionCount += 1;
                            System.out.println(this.getName() + " read:" + count + " readActionCount:" + readActionCount);
                            sleepMillis(20);

                        } finally {
                            readLock.unlock();
                        }


                    }
                }
            };

            readThread1.setName("read-" + i);
            readThread1.start();
        }

        //1个写线程
        for (int i = 0; i < countWriter; i++) {
            Thread writeThread1 = new Thread() {
                public void run() {
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    while (flag == true) {
                        writeLock.lock();
                        try {
                            count += 1;
                            System.out.println(this.getName() + " write:" + count);
                            sleepMillis(80);
                        } finally {
                            writeLock.unlock();
                        }

                        sleepMillis(100);
                    }
                }
            };

            writeThread1.setName("write-" + i);
            writeThread1.start();
        }


        TimeUnit.SECONDS.sleep(30);
        flag = false;
    }
}
