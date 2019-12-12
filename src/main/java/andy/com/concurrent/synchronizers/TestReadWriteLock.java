package andy.com.concurrent.synchronizers;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {

    static volatile boolean flag = true;
    static volatile int count = 1;
    static volatile int readActionCount = 1;

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
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {

        final ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < 10; i++) {
            Thread readThread1 = new Thread() {
                public void run() {
                    while (flag == true) {
                        lock.lock();
                        try {
                            readActionCount += 1;
                            System.out.println(this.getName() + " read:" + count + " readActionCount:" + readActionCount);
                            sleepMillis(20);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            };

            readThread1.setName("read-" + i);
            readThread1.start();
        }

        for (int i = 0; i < 1; i++) {
            Thread writeThread1 = new Thread() {
                public void run() {
                    while (flag == true) {
                        lock.lock();
                        try {
                            count += 1;
                            System.out.println(this.getName() + "write:" + count);
                            sleepMillis(80);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ;
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


        TimeUnit.SECONDS.sleep(30);
        flag = false;
    }


    /**
     * 使用读写说 模拟读多写少的场景
     * 10个读线程线程，读操作耗时20ms
     * 1个写线程，写操作耗时80ms 每隔100毫秒一个写操作
     * 30秒可以并发读7000+次
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {

        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        for (int i = 0; i < 10; i++) {
            Thread readThread1 = new Thread() {
                public void run() {
                    while (flag == true) {
                        readLock.lock();
                        try {
                            readActionCount += 1;
                            System.out.println(this.getName() + " read:" + count + " readActionCount:" + readActionCount);
                            sleepMillis(20);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ;
                        } finally {
                            readLock.unlock();
                        }


                    }
                }
            };

            readThread1.setName("read-" + i);
            readThread1.start();
        }

        for (int i = 0; i < 1; i++) {
            Thread writeThread1 = new Thread() {
                public void run() {
                    while (flag == true) {
                        writeLock.lock();
                        try {
                            count += 1;
                            System.out.println(this.getName() + "write:" + count);
                            TimeUnit.MILLISECONDS.sleep(80);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ;
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
