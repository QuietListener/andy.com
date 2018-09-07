package andy.com.concurrent.synchronizers;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class BarrierTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int count = 3;
		CyclicBarrier barrier = new CyclicBarrier(count);
	    for(int i = 0 ; i < count; i++)
	    {
	    	new Competor(barrier).start();
	    }
	    
	    System.out.println("let's gogogo");
	}

	static class Competor extends Thread
	{
		private CyclicBarrier barrier;
		public Competor(CyclicBarrier barrier) {
			this.barrier = barrier;
		}	
		
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(10));
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName() +" is ready to action");
			try {
				barrier.await();
				
				System.out.println(Thread.currentThread().getName() +" start ...");
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
