package andy.com.concurrent.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutorPool {

	//线程池
	static Executor pool = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) {
		for(int i = 0; i < 40; i++)
		{
			pool.execute(new Task("task"+i));
			System.out.println("add task "+i);
		}
	}

	static class Task implements  Runnable
	{
		private String name = null;
		public Task(String name) {
			this.name = name;
		}
		
		public void run(){
		
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			System.out.println(this.name +" finished");
			
		}
	}

}