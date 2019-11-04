package andy.com.concurrent.executor;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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

public class TestScheduledThreadPoolExecutorService {

	//线程池
	static ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(2,new ScheduledThreadPoolExecutor.DiscardPolicy());



	@Test
	public void test1(){

		for(int i = 0; i < 40; i++)
		{
			long now = System.currentTimeMillis();
			int delay = new Random().nextInt(1000)+1000;
			Task t = new Task("task:"+now+":"+delay);
			pool.schedule(t,delay,TimeUnit.MILLISECONDS);
			System.out.println("add task:"+t.name);
		}

		close();
	}

	static void close(){
		try {
			//调用shutdown之后不能有新的任务再加入了
			pool.shutdown();

			//在shutdown收调用会抛出 java.util.concurrent.RejectedExecutionException:
			//pool.execute(new Task("task"+44));

			//当调用shutdown之后, 阻塞直到所有任务结束, 或者阻塞指定的时间
			pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

			System.out.println("all thread complete");
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	static  class Task implements  Runnable
	{
		private String name = null;
		public Task(String name) {
			this.name = name;
		}

		public void run(){

			try {
				TimeUnit.MILLISECONDS.sleep(50);
				long interval = System.currentTimeMillis() - Long.parseLong(this.name.split(":")[1]);
				System.out.println(this.name +" finished interval:"+interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}



		}
	}
}

