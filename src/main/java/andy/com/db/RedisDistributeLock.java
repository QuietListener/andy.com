package andy.com.db;

import org.apache.tools.ant.util.WorkerAnt;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisDistributeLock {

    public static long MaxRenew = 1000 * 60 * 10;
    public static int DefaultExpireMs = 1000 * 6;
    public static int MaxTotal = 60;
    public static int MaxIdle = 50;
    public static long MaxWait = 1000l;
    public static int DbIndex = 0;

    private static String host = "127.0.0.1";
    private static int Port = 6379;
    private static JedisPool pool = null;

    private static String OK = "OK";
    private static String UnlockOK = "1";

    static private RedisDistributeLock instance;

    static private Map<String, Long> currKeyValues = new HashMap<>();
    static private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    static private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private Thread watchDog = new WatchDog();

    private RedisDistributeLock() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MaxTotal);
        config.setMaxIdle(MaxIdle);
        config.setMaxWaitMillis(MaxWait);
        config.setTestOnBorrow(false);
        pool = new JedisPool(config, host, Port, 200, "123456");
        watchDog.setName("WatchDog");
        watchDog.start();
    }

    public static RedisDistributeLock getInstance() {

        if (instance == null) {
            synchronized (RedisDistributeLock.class) {
                if (instance == null) {
                    instance = new RedisDistributeLock();

                }
            }
        }
        return instance;
    }


    public boolean lock(String key, String value, int expireMs) {

        try (Jedis jedis = pool.getResource()) {
            String ret = jedis.set(key, value, "NX", "PX", expireMs);
            if (OK.equals(ret)) {
                writeLock.lock();
                try {
                    currKeyValues.put(key, System.currentTimeMillis());
                } finally {
                    writeLock.unlock();
                }
                return true;
            }
            return false;
        }
    }


    public boolean lock(String key, String value) {
        return this.lock(key, value, DefaultExpireMs);
    }


    public boolean unLock(String key, String value) {
        String script = "if  redis.call('get', KEYS[1]) == ARGV[1] " +
                "then " +
                "   return redis.call('del', KEYS[1])" +
                "else" +
                "   return 0 " +
                "end";

        writeLock.lock();
        try {
            currKeyValues.remove(key);
        } finally {
            writeLock.unlock();
        }

        int tryCount = 0;
        while (tryCount < 3) {
            try (Jedis jedis = pool.getResource()) {
                Object ret = jedis.eval(script, Arrays.asList(key), Arrays.asList(value));
                if (UnlockOK.equals(ret + "")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            tryCount += 1;
        }

        return false;
    }

    private boolean renew(String key, int expireS) {
        int tryCount = 0;
        while (tryCount < 3) {
            try (Jedis jedis = pool.getResource()) {
                long ret = jedis.expire(key, expireS);
                return ret == 1l;
            } catch (Exception e) {
                e.printStackTrace();
            }
            tryCount += 1;
        }

        return false;
    }


    static public void test1() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 50, 100, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000), new ThreadPoolExecutor.DiscardPolicy());

        final String key = "dkey";
        for (int i = 0; i < 1000; i++) {
            int j = i;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    int sleepMs = new Random().nextInt(300);
                    try {
                        TimeUnit.MILLISECONDS.sleep(sleepMs);
                    } catch (Exception e) {

                    }

                    int sleepTime = 1000*new Random().nextInt(30);
                    boolean locked = false;
                    String value = Thread.currentThread().getName() + System.currentTimeMillis();
                    try {
                        locked = RedisDistributeLock.getInstance().lock(key, value);
                        if (locked) {
                            System.out.println("#" + j + ":locked and do stuff using "+sleepTime+" Ms");
                            TimeUnit.MILLISECONDS.sleep(sleepTime);
                        } else {
                            System.out.println(j + ":locked failed");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        boolean ret = RedisDistributeLock.getInstance().unLock(key, value);
                        if (locked == true) {
                            System.out.println("#" + j + ":unlocked :" + ret +" using "+sleepTime+" Ms");
                        }
                    }
                }
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(20, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
        pool.shutdownNow();
    }


    static public void test2() throws Exception {

        String key = "kk";
        String value = "value";
        boolean locked = RedisDistributeLock.getInstance().lock(key, value, 2000);
        System.out.println("1locked=" + locked);
        locked = RedisDistributeLock.getInstance().lock(key, value, 3000);
        System.out.println("2locked=" + locked);

        TimeUnit.MILLISECONDS.sleep(2500);
        boolean unlock = RedisDistributeLock.getInstance().unLock(key, value);
        System.out.println("unlocked=" + unlock);

        locked = RedisDistributeLock.getInstance().lock(key, value, 2000);
        System.out.println("3locked=" + locked);

    }

    public static void testWatchDog() throws Exception {

        String key = "kk";
        String value = "value";
        boolean locked = RedisDistributeLock.getInstance().lock(key, value);
        System.out.println("1locked=" + locked);
        locked = RedisDistributeLock.getInstance().lock(key, value);
        System.out.println("2locked=" + locked);

        TimeUnit.MILLISECONDS.sleep(10*1000);
        boolean unlock = RedisDistributeLock.getInstance().unLock(key, value);
        System.out.println("unlocked=" + unlock);

        locked = RedisDistributeLock.getInstance().lock(key, value);
        System.out.println("3locked=" + locked);

    }



    public static void main(String[] args) throws Exception {

        //test1();
        //test2();
        testWatchDog();
    }

    private class WatchDog extends Thread {
//
//        JedisPool jedisPool;
//
//        public WatchDog(JedisPool jedisPool) {
//            this.jedisPool = jedisPool;
//        }

        @Override
        public void run() {

            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    boolean locked = readLock.tryLock(1, TimeUnit.SECONDS);
                    List<String> toRemovedKey = new ArrayList<>();
                    if (locked) {
                        try {
                            for (Map.Entry<String, Long> entry : currKeyValues.entrySet()) {
                                String key = entry.getKey();
                                long startAt = entry.getValue();
                                if (System.currentTimeMillis() - startAt > MaxRenew) {
                                    toRemovedKey.add(key);
                                    continue;
                                }
                                boolean ret = renew(key, 10);
                                System.out.println(name + " renew " + key + " " + ret);
                                if (ret == false) {
                                    toRemovedKey.add(key);
                                }

                            }
                        } finally {
                            readLock.unlock();
                        }
                    }

                    boolean wlocked = writeLock.tryLock(1, TimeUnit.SECONDS);
                    if (wlocked) {
                        try {
                            for (String key : toRemovedKey) {
                                currKeyValues.remove(key);
                                System.out.println(name + " remove " + key + " ");
                            }
                        } finally {
                            writeLock.unlock();
                        }
                    }

                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Throwable e) {
                    e.printStackTrace();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            }
        }
    }

}

