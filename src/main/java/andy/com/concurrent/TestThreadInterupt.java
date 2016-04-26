package andy.com.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 现在一般用interupt来结束进程，如果进程阻塞
 * @author Admin
 *
 */
public class TestThreadInterupt {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread() {
			@Override
			public void run() {
					long i = 0l;
					try {
							
						while (this.isInterrupted() == false) 
						{
							System.out.println(Thread.currentThread().getName() + " sleep 10 ");
							TimeUnit.SECONDS.sleep(10);
						}
					}
					catch (InterruptedException e) //会抛出InterruptException因为Sleep会阻塞 
					{
						e.printStackTrace();
						System.out.println(Thread.currentThread().getName() +":"+e.getMessage());
					}
			}
		};
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
					long i = 0l;
					while (this.isInterrupted() == false) 
					{
						try {
							Thread.currentThread().sleep(1);
							i++;
							while(true)
							{
								i++;
							}
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}		
			}
		};
		
		t1.start();
		t2.start();
		
		System.out.println(t1.getName() + " before interupt:" +t1.isInterrupted() +"   is alive=" + t1.isAlive());
		System.out.println(t2.getName() + " before interupt:" +t2.isInterrupted()+"   is alive=" + t2.isAlive());
		TimeUnit.SECONDS.sleep(1);
		t1.interrupt();
		t2.interrupt();
		TimeUnit.SECONDS.sleep(1);
		System.out.println(t1.getName() +" after interupt:" +t1.isInterrupted()+"   is alive=" + t1.isAlive());
		System.out.println(t2.getName() +" after interupt:" +t2.isInterrupted()+"   is alive=" + t2.isAlive());
	}

}
