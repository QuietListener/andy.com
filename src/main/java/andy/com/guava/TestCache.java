package andy.com.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;


public class TestCache {

    @Test
    public void test1()  throws Exception{

        //线程安全的~
        Cache<Object,String> c1 = CacheBuilder.newBuilder().concurrencyLevel(12).expireAfterWrite(1, TimeUnit.SECONDS).build();
        c1.put("key1","c2");

        String r1 = c1.getIfPresent("key1");
        System.out.println(r1);
        TimeUnit.SECONDS.sleep(2);

        String r2 = c1.getIfPresent("key1");
        System.out.println(r2);

        c1.put("key3",null);
        String r3 = c1.getIfPresent("key3");
        System.out.println(r3);
    }

    @Test
    public void test2() throws Exception{
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors() + 1)
                .maximumSize(100)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("load data....");
                        TimeUnit.SECONDS.sleep(2);
                        return "";
                    }
                });

//        String v1 = cache.get("aaa");
//        System.out.println(Thread.currentThread().getName()+"  v1 = " + v1);
//
//
//        String v2 = cache.get("aaa");
//        System.out.println(Thread.currentThread().getName()+"  v2 = " + v2);

        CyclicBarrier barrier = new CyclicBarrier(5);
        for(int i = 0; i < 5;i+=1) {
            Thread t1 = new Thread() {
                @Override
                public void run()  {
                    try {
                        String name = Thread.currentThread().getName();
                        barrier.await();
                        System.out.println(name+": "+System.currentTimeMillis()+": start");
                        String v1 = cache.get("aaa");
                        System.out.println(name +": "+System.currentTimeMillis()+"  v1 = " + v1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };

            t1.start();
        }

        TimeUnit.SECONDS.sleep(4);
    }



    @Test
    public void test3() throws Exception{
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors() + 1)
                .maximumSize(100)
                .build();

//        String v1 = cache.get("aaa");
//        System.out.println(Thread.currentThread().getName()+"  v1 = " + v1);
//
//
//        String v2 = cache.get("aaa");
//        System.out.println(Thread.currentThread().getName()+"  v2 = " + v2);

        CyclicBarrier barrier = new CyclicBarrier(5);
        for(int i = 0; i < 5;i+=1) {
            final int j = i;
            Thread t1 = new Thread() {
                @Override
                public void run()  {
                    try {

                        String name = Thread.currentThread().getName();


                        //try{ TimeUnit.MILLISECONDS.sleep(j*10);}catch (Exception e){}
                        barrier.await();

                        System.out.println(name+": "+System.currentTimeMillis()+": start");
                        String v1 = cache.get("aaa", new Callable<String>() {
                            @Override
                            public String call() throws Exception {
                                System.out.println("load data....");
                                TimeUnit.SECONDS.sleep(2);
                                return null;
                            }
                        });

//                        if(v1 == null){
//                            cache.put("aaa","1");
//                        }
                        System.out.println(name +": "+System.currentTimeMillis()+"  v1 = " + v1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };

            t1.start();
        }

        TimeUnit.SECONDS.sleep(4);
    }
}
