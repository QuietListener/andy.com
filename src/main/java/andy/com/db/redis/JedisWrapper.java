package andy.com.db.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.function.Function;

public class JedisWrapper {

    Logger logger = LoggerFactory.getLogger(JedisWrapper.class);

    private JedisPool pool;

    private String ip;
    private int  port;
    private String password;
    private int timeoutMs;
    private int  maxTotal = 32;

    private long lastCheckPoolMs = 0L;
    private boolean initializing = false;


    public JedisWrapper(String ip, int port, int timeoutMs, String password, int maxTotal)
    {
            this.ip = ip;
            this.port = port;
            this.timeoutMs = timeoutMs;
            this.password = password;
            this.maxTotal = maxTotal<=0 ? 32 : maxTotal;
            newPoll();
    }


    private synchronized void newPoll()
    {
        try {
            this.initializing = true;
            if(!needUpdatePool(System.currentTimeMillis()))
                return;

            lastCheckPoolMs = System.currentTimeMillis();

            JedisPool oldPool = this.pool;
            JedisPoolConfig config = new JedisPoolConfig();

            if(maxTotal > 0 ) {
                config.setMaxTotal(maxTotal);
            }

            if (this.password != null && !password.trim().isEmpty()) {
                this.pool = new JedisPool(config, this.ip, this.port, this.timeoutMs, this.password);
            } else {
                this.pool = new JedisPool(config, this.ip, this.port, this.timeoutMs);
            }

            if(oldPool != null)
            {
                oldPool.destroy();
            }
        }
        catch(Exception e) {
            logger.error("create pool error, params = "+this.toString(), e);
        }
        finally {
            initializing = false;
        }
    }

    /**
     * 每2分钟检查一下poll
     * 如果pool为空10秒钟检查一下
     * @param now
     * @return
     */
    private boolean needUpdatePool(long now) {
        return this.lastCheckPoolMs + 120000L < now || this.pool == null && this.lastCheckPoolMs + 10000L < now;
    }
    public Jedis getResource()
    {
        if(!this.initializing && this.needUpdatePool(System.currentTimeMillis()))
        {
            this.newPoll();
        }

        if(this.pool == null)
        {
            throw new BizException(ExceptionCode.GENERAL_ERROR, "Jedis pool is null ,params is "+this.toString());
        }
        else
        {
            return pool.getResource();
        }

    }
    public void close()
    {
        if(!this.initializing)
        {
            if(this.pool!=null)
            {
                this.pool.destroy();
            }
        }
    }

    public String get(String key)
    {
        String ret = jedisTemplate( jedis->jedis.get(key),key);
        return ret;
    }

    public boolean setex(String key,int expireSeconds, String value)
    {
        String ret = jedisTemplate(jedis->jedis.setex(key,expireSeconds,value),key);
        return ret != null;
    }

    public boolean set(String key, String value)
    {
        String ret =jedisTemplate(jedis->jedis.set(key,value),key);
        return ret != null;
    }

    public boolean del(String [] keys) {
        Long ret = jedisTemplate(jedis->jedis.del(keys),keys);
        return ret != null;
    }


    public <T> T jedisTemplate(Function<Jedis,T> func,Object key)
    {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            T ret = func.apply(jedis);
            return ret;
        } catch (Exception e) {
            logger.error("redis failed, key = "+key+"; server info:"+this.toString(),e);
            return null;
        }
        finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
    }


    @Override
    public String toString() {
        return "BczJedisWrapper{" +
                "ip='" + ip +
                ", port=" + port +
                ", timeoutMs=" + timeoutMs +
                ", lastCheckPoolMs=" + lastCheckPoolMs +
                ", maxTotal=" + maxTotal +
                '}';
    }
}
