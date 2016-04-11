package andy.com.concurrent.synchronizers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		final BoundedList<Integer> bL = new BoundedList<Integer>(3);
		Thread t = new Thread(){
			@Override
			public void run() {
				
				try {
					System.out.println(Thread.currentThread().getName()+" sleep 10 seconds");
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				int i = 0;
				while(i++ < 10)
				{
					bL.remove(i);
					System.out.println("remove "+i);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		t.start();
		
		for(int i = 0; i < 10; i++)
		{
			bL.add(i);
			System.out.println(Thread.currentThread().getName() + " add "+i);
		}
		
		t.join();
	}
	
	/**
	 * 用信号量来实现一个有边界的List
	 * @author Admin
	 *
	 * @param <T>
	 */
	static class BoundedList<T>
	{
		private Semaphore sem;
		private List<T> list = new ArrayList<T>();
		
		public BoundedList(int maxSize) {
			if (maxSize <= 0)
			{
				throw new AssertionError("BoundedList size should be positive") ;
			}
			
			sem = new Semaphore(maxSize);
		}
		
		public boolean add(T t) throws InterruptedException 
		{
			//看看是不是能获取一个信号量
			this.sem.acquire();
			boolean wasAdded = false;
			try{
				wasAdded = list.add(t);
				return wasAdded;
			}
			finally{ //添加失败需要释放
				if(wasAdded == false)
					sem.release();
			}
		}
		
		public boolean remove(T t)
		{
			boolean wasRemoved = this.list.remove(t);
			if (wasRemoved) //如果成功remove需要释放信号量
				this.sem.release();
			return wasRemoved;
		}
	}

}
