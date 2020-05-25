package andy.com.internet.cache;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import java.util.concurrent.TimeUnit;

public class RedissionTest {


    @Test
    public void test1(){

        Config config =  new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://127.0.0.1:6379");
        singleServerConfig.setConnectionMinimumIdleSize(2);
        singleServerConfig.setConnectionPoolSize(10);
        singleServerConfig.setKeepAlive(true);
        singleServerConfig.setTimeout(100);
        singleServerConfig.setPassword("123456");

        RedissonClient client = Redisson.create(config);


        String Key1 = "key1";
        RBucket<String> b1 =  client.getBucket(Key1);
        b1.setAsync("1111",10, TimeUnit.SECONDS);


        client.shutdown();

    }
}
