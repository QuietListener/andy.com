package andy.com.concurrent.executor;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorCompletionService 实现了Executor和CompletionService
 * 使用Future和Callable可以提供异步执行的功能
 * 他将Future放入一个BlockingQueue当结果不可用时会阻塞
 * 
 * @author Admin
 *
 */

public class TestThreadPoolExecutorServiceFuture {

	//线程池
	static ExecutorService pool = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) throws InterruptedException
	{	
		//启动
		ExecutorCompletionService<String> cs1 = new ExecutorCompletionService<>(pool);
		
		int count = 5; 
		
		for(int i = 0; i < count; i++)
		{
			cs1.submit(new Task(i+""));
		}
		
		
		for(int i = 0; i < count; i++)
		{
			try {
				Future<String> f = cs1.take();
				String result = f.get();
				System.out.println(result);
				
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}

		
		//调用shutdown之后不能有新的任务再加入了
		pool.shutdown();
		
		//在shutdown收调用会抛出 java.util.concurrent.RejectedExecutionException:
		//pool.execute(new Task("task"+44));
		
		//当调用shutdown之后, 阻塞直到所有任务结束, 或者阻塞指定的时间
		pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		
		System.out.println("all thread complete");
	}
	
	static  class Task implements Callable<String>
	{
		private String name = null;
		
		public Task(String name) {
			this.name = name;
		}

		@Override
		public String call() throws Exception {

			int i = new Random().nextInt(10);
			try {
				
				TimeUnit.SECONDS.sleep(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			System.out.println(Thread.currentThread().getName() +" finished");
			return Thread.currentThread().getName() + ": doing job take " + i ;
		}
	}
}

