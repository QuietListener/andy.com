package andy.com.internet.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglongwen on 2018/11/19.
 */
@Component
public class JedisManager {

    @Value("${redis.maxTotal}")
    private int maxTotal;

    @Value("${redis.poolMaxWaitMs}")
    private int poolMaxWaitMs;

    @Value("${redis.timeOutMs}")
    private int timeOutMs;

    @Value("${redis.ip}")
    private String ip;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    private JedisPool jedisPool = null;




    @PostConstruct
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(poolMaxWaitMs);
        config.setMaxIdle(maxTotal / 2);
        if (StringUtils.isEmpty(password)) {
            password = null;
        }
        jedisPool = new JedisPool(config,ip,port,timeOutMs,password);
    }

    @PreDestroy
    public void destroy() {
        jedisPool.close();
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }


    public void batchWriteCache(ArrayList<String> keys,ArrayList<String> values,int expire) {
        Assert.notEmpty(keys,"keys is empty");
        Assert.notEmpty(values,"values is empty");
        Assert.isTrue(expire > 0,"expire is required");
        Assert.isTrue(keys.size() == values.size(),"key size not eq values size");
        List<String> kvs = new ArrayList<>(keys.size() * 2);
        for (int i = 0 ; i < keys.size() ; i++) {
            kvs.add(keys.get(i));
            kvs.add(values.get(i));
        }
        try (Jedis jedis = getJedis()) {
            Pipeline pipeline = jedis.pipelined();
            pipeline.mset(kvs.toArray(new String[kvs.size()]));
            keys.forEach(k -> pipeline.expire(k,expire));
            pipeline.sync();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 不存在的key,数组中对应位置值为null
     * @param keys
     * @return
     */
    public List<String> batchGet(List<String> keys) {
        Assert.notEmpty(keys,"keys is empty");
        try (Jedis jedis = getJedis()) {
            return jedis.mget(keys.toArray(new String[keys.size()]));
        } catch (Exception e) {
            throw e;
        }
    }



}
