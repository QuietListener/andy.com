package andy.com.db;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RedisDistributeLock {

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

    private RedisDistributeLock() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MaxTotal);
        config.setMaxIdle(MaxIdle);
        config.setMaxWaitMillis(MaxWait);
        config.setTestOnBorrow(false);
        pool = new JedisPool(config, host, Port, 200, "123456");
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
            return OK.equals(ret);
        }
    }


    public boolean unLock(String key, String value) {
        String script = "if  redis.call('get', KEYS[1]) == ARGV[1] " +
                "then " +
                "   return redis.call('del', KEYS[1])" +
                "else" +
                "   return 0 " +
                "end";


        try (Jedis jedis = pool.getResource()) {
            Object ret = jedis.eval(script, Arrays.asList(key), Arrays.asList(value));
            if (UnlockOK.equals(ret + "")) {
                return true;
            }
            return false;
        }
    }

    //测试
    public static void main(String[] args) {
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

                    boolean locked = false;
                    String value = Thread.currentThread().getName() + System.currentTimeMillis();
                    try {
                        locked = RedisDistributeLock.getInstance().lock(key, value, 2000);
                        if (locked) {
                            System.out.println("#" + j + ":locked and do stuff");
                            TimeUnit.MILLISECONDS.sleep(100);
                        } else {
                            System.out.println(j + ":locked failed");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        boolean ret = RedisDistributeLock.getInstance().unLock(key, value);
                        if (locked == true) {
                            System.out.println("#" + j + ":unlocked :" + ret);
                        }
                    }
                }
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(20, TimeUnit.SECONDS);
        } catch (Exception e) { }
        pool.shutdownNow();
    }
}
