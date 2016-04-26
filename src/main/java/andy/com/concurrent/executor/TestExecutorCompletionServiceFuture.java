package andy.com.concurrent.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ExecutorService 继承了 Executor 为Executor提供了生命周期管理
 * 使用Future和Callable可以提供异步执行的功能
 * 
 * @author Admin
 *
 */

public class TestExecutorCompletionServiceFuture {

	//线程池
	static ExecutorService pool = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) throws InterruptedException
	{	
		
		Future<String> f1 = pool.submit(new Task("task"+10,10));
		Future<String> f2 = pool.submit(new Task("task"+11,11));
		
		int count = 5; 
		while(count-- > 0)
		{
			System.out.println("doing my other job...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//get可以重复调用 直到返回为止
			String result = null;
			try {
				result = (String) f1.get(1, TimeUnit.SECONDS);
			} catch (ExecutionException | TimeoutException e) {
				e.printStackTrace();
			}
			if (result != null)
				System.out.println(result);
		}
		

		String result = null;
		try {
			result = (String) f2.get();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println(result);
		
		//调用shutdown之后不能有新的任务再加入了
		pool.shutdown();
		
		//在shutdown收调用会抛出 java.util.concurrent.RejectedExecutionException:
		//pool.execute(new Task("task"+44));
		
		//当调用shutdown之后, 阻塞直到所有任务结束, 或者阻塞指定的时间
		pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		
		System.out.println("all thread complete");
	}
	
	static  class Task implements  Callable<String>
	{
		private String name = null;
		private int i  = 0;
		
		public Task(String name,int i) {
			this.name = name;
			this.i = i;
		}
		
		public void run(){
		
			
		}

		@Override
		public String call() throws Exception {

			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			System.out.println(this.name +" finished");
			return Thread.currentThread().getName() + ": " + i*i;
		}
	}
}

