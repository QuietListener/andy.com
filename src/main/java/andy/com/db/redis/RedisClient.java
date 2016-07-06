package andy.com.db.redis;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


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
