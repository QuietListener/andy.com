package andy.com.db.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

    public Jedis getResource() throws Exception
    {
        if(!this.initializing && this.needUpdatePool(System.currentTimeMillis()))
        {
            this.newPoll();
        }

        if(this.pool == null)
        {
            throw new Exception("Jedis pool is null ,params is "+this.toString());
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
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            String ret = jedis.get(key);
            jedis.close();
            return ret;
        } catch (Exception e) {
            logger.error("redis get failed: key = " + key );
            return null;
        }
    }

    public boolean setex(String key,int expireSeconds, String value)
    {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            String ret = jedis.setex(key,expireSeconds,value);
            jedis.close();
            return true;

        } catch (Exception e) {
            logger.error("redis setex failed: key = " + key );
            return false;
        }
    }

    public boolean set(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            String ret = jedis.set(key,value);
            jedis.close();
            return true;

        } catch (Exception e) {
            logger.error("redis set failed: key = " + key );
            return false;
        }
    }


    @Override
    public String toString() {
        return "JedisWrapper{" +
                ", ip='" + ip +
                ", port=" + port +
                ", timeoutMs=" + timeoutMs +
                ", lastCheckPoolMs=" + lastCheckPoolMs +
                ", maxTotal=" + maxTotal +
                '}';
    }
}
