/**
 * cp = consumer and producer
 */

package andy.com.concurrent.sync.cp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestConsumerProducer_Lock {

	private static final Lock lock = new ReentrantLock();
	private static final Condition condition = lock.newCondition();
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		List<String> pool = new ArrayList<>();
		for(int i = 0; i < 1;i++)
		{
			new Consumer(pool,"consumer-"+i).start();
		}
		
		TimeUnit.SECONDS.sleep(5);
		
		for(int i = 0; i < 10;i++)
		{
			new Producer(pool,"producer-"+i).start();
		}

		TimeUnit.SECONDS.sleep(60*5);
	}

	
	

/**
 * 生产者 往pool里面添加数据
 * @author Admin
 *
 */
static class ConsumerL extends Thread
{
	private List<String> pool = null;
	public ConsumerL(List<String> pool,String name) {
		this.pool = pool;
		this.setName(name);
		
		if (this.pool ==  null) 
			throw new AssertionError("pool is null");
	}
	
	public void run()
	{
		while(true)
		{
			String i = null;
			lock.lock();
			try{
				while(pool.size() == 0)
					try {
						System.out.println(this.getName()+" pool is empty waitting ");
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			
				i = pool.remove(0);
				condition.signalAll();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				lock.unlock();
			}
			
			System.out.println(this.getName()+" consume "+i);
			
			//随机睡几秒
			try {
				TimeUnit.SECONDS.sleep(2+new Random().nextInt(5));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public List<String> getPool() {
		return pool;
	}
	public void setPool(List<String> pool) {
		this.pool = pool;
	}
}


/**
 * 生产者 往pool里面添加数据
 * @author Admin
 *
 */
static class ProducerL extends Thread
{
	//max size of pool
	private static final int MAX_SIZE = 3;
	
	//shared pool
	private List<String> pool = null;
	
	public ProducerL(List<String> pool,String name) {
		this.pool = pool;
		this.setName(name);
		
		if (this.pool ==  null) 
			throw new AssertionError("pool is null");
	}
	
	public void run()
	{
		while(true)
		{
			lock.lock();
			try
			{
				while(pool.size() == MAX_SIZE)
					try {
						System.out.println(this.getName()+" pool is full("+pool.size()+") waitting ");
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
				
				String i = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
				pool.add(i);	
				condition.signalAll();
				System.out.println(this.getName()+" produce("+pool.size()+") "+i);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				lock.unlock();
			}
			
			//随机睡几秒
			try {
				TimeUnit.SECONDS.sleep(2+new Random().nextInt(5));
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
	}
		
	
	public List<String> getPool() {
		return pool;
	}
	public void setPool(List<String> pool) {
		this.pool = pool;
	}
}
}
