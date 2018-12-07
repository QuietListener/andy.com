package andy.com.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

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
    }
}
