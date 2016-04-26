package andy.com.concurrent.containers;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * blockQueue实现生产者与消费者
 * @author Admin
 *
 */
public class TestBlockQueue {

	//blockQueue可以指定大小也可以不指定大小
	static BlockingQueue<Integer> q1 = new LinkedBlockingDeque<Integer>(2);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		for(int i = 0; i < 3; i++)
		{
			new Producer().start();
		}

		new Consumer().start();
	}

	static class Consumer extends Thread
	{
		@Override
		public void run() {
			try 
			{
				while(true)
				{			
					System.out.println(Thread.currentThread().getName() + " will consume ");
					int i = q1.take();	
					System.out.println(Thread.currentThread().getName() + " consume "+i);
					TimeUnit.SECONDS.sleep(5);
				}
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	static class Producer extends Thread
	{
		@Override
		public void run() {
			try 
			{
				while(true)
				{			
					int i = new Random().nextInt(10);
					System.out.println(Thread.currentThread().getName() + " will produce "+i);
					q1.put(i);	
					System.out.println(Thread.currentThread().getName() + " produce "+i);
					TimeUnit.SECONDS.sleep(5);
				}
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
}
