package andy.com.internet.distributeLock.redission;


import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissionTests {

    static private Config config =  new Config();{

    }
    @Test
    public void test1(){
        RedissonClient client = Redisson.create(config);
    }
}
