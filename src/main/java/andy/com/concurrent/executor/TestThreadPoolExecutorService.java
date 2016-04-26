package andy.com.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService 继承了 Executor 为Executor提供了生命周期管理
 *  shutdown()
	shutdownNow()
	isShutdown()
	isTerminated()
	awaitTermination(long, TimeUnit)
	submit(Callable<T>)
	submit(Runnable, T)
	submit(Runnable)
	invokeAll(Collection<? extends Callable<T>>)
	invokeAll(Collection<? extends Callable<T>>, long, TimeUnit)
	invokeAny(Collection<? extends Callable<T>>)
	invokeAny(Collection<? extends Callable<T>>, long, TimeUnit)
 * 
 * @author Admin
 *
 */

public class TestThreadPoolExecutorService {

	//线程池
	static ExecutorService pool = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) throws InterruptedException 
	{	
		for(int i = 0; i < 40; i++)
		{
			pool.submit(new Task("task"+i));
			System.out.println("add task "+i);
		}
		
		//调用shutdown之后不能有新的任务再加入了
		pool.shutdown();
		
		//在shutdown收调用会抛出 java.util.concurrent.RejectedExecutionException:
		//pool.execute(new Task("task"+44));
		
		//当调用shutdown之后, 阻塞直到所有任务结束, 或者阻塞指定的时间
		pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		
		System.out.println("all thread complete");
	}
	
	static  class Task implements  Runnable
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

