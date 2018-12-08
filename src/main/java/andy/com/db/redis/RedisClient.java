package andy.com.db.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;


public class RedisClient {

	public static int MaxTotal = 20;
	public static int MaxIdle = 5;
	public static long MaxWait = 1000l;
	public static int DbIndex = 0;

	private static String host = "127.0.0.1";
	private static int Port = 6379;
	
	private static JedisPool pool = null;
	
	public RedisClient() {
		synchronized ( this.getClass()) {
			if (pool == null)
			{
				initPool();
			}
			
		}
	}
	
	private void initPool()
	{
		JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxTotal(MaxTotal);
        config.setMaxIdle(MaxIdle); 
        config.setMaxWaitMillis(MaxWait);
        config.setTestOnBorrow(false); 
        
        pool = new JedisPool(config, host, Port);
	}
	
	public void saveInfo(String key,String value,int timeout)
	{
		 Jedis jedis = null;
	     try {
			 jedis = pool.getResource();
			 jedis.select(DbIndex);
			 
			 jedis.set(key, value);
			 jedis.expire(key, timeout);	
	     }
	     finally{
	    	 if(jedis != null)
	    		 jedis.close();
	     }
	}
	

	public String get(String key)
	{
		 if (StringUtils.isBlank(key))
			 return null;
				 
		 Jedis jedis = null;
	     try {
			 jedis = pool.getResource();
			 jedis.select(DbIndex);
			 
			 String value = jedis.get(key);
			 return value;
	     }
	     finally{
	    	 if(jedis != null)
	    		 jedis.close();
	     }
	}

	public void batchWriteCache(ArrayList<String> keys, ArrayList<String> values, int expire) {
		Assert.notEmpty(keys,"keys is empty");
		Assert.notEmpty(values,"values is empty");
		Assert.isTrue(expire > 0,"expire is required");
		Assert.isTrue(keys.size() == values.size(),"key size not eq values size");
		List<String> kvs = new ArrayList<>(keys.size() * 2);
		for (int i = 0 ; i < keys.size() ; i++) {
			kvs.add(keys.get(i));
			kvs.add(values.get(i));
		}
		try (Jedis jedis = pool.getResource()) {
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
		try (Jedis  jedis = pool.getResource()) {
			return jedis.mget(keys.toArray(new String[keys.size()]));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void main(String [] args) throws InterruptedException
	{
		RedisClient client = new RedisClient();
		
		String key = "key";
		String value = "value";
		int timeout = 5;
		client.saveInfo(key, value, timeout);
		String v = client.get(key);
		System.out.println(value);
		
		TimeUnit.SECONDS.sleep(timeout-1);
		
		v = client.get(key);
		System.out.println(v);	
	}
}
