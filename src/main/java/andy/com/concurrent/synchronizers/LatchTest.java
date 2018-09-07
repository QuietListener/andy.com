package andy.com.concurrent.synchronizers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 闭锁 用于同步，一个或者几个线程必须等待其他若干个线程结束
 * @author Admin
 *
 */
public class LatchTest {
	
	private static int InitializerCount = 2;
	private final static CountDownLatch initializerEndLatch = new CountDownLatch(InitializerCount);
	private final static CountDownLatch initializerStartLatch = new CountDownLatch(1);
	public static void main(String[] args) throws InterruptedException {
		
		for(int i = 0; i < InitializerCount; i++)
		{
			new ResourceInitializer().start();
		}
		
		//所有initialer开始初始化
		initializerStartLatch.countDown();
		
		//等待所有initialer初始化完成
		initializerEndLatch.await();
		
		//完成之后干自己的事情
		System.out.println("all ResourceInitialer finished; let's gogogo");
	}
	
	/**
	 * 资源初始器
	 * @author Admin
	 *
	 */
	static class ResourceInitializer extends Thread
	{
		static private int count = 5;
		@Override
		public void run() {
			int i = 0; 
			try
			{
				//等待发起开始命令
				initializerStartLatch.await();
				
				while(i++ < count)
				{	
					TimeUnit.SECONDS.sleep(1+new Random().nextInt(10));
					System.out.println(Thread.currentThread().getName()+":initializing for ("+i +" seconds)");		
				}
				
				System.out.println(Thread.currentThread().getName()+":initializor finished" );
			
			}
			catch (InterruptedException e) 
			{
				
			}
			finally
			{
				//完成了一个初始化任务
				initializerEndLatch.countDown();
			}
		}
		
	}

}
