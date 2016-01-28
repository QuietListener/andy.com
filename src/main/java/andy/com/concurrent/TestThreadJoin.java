package andy.com.concurrent;

import java.util.concurrent.TimeUnit;

public class TestThreadJoin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Thread t = new Thread(){
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				super.run();
				for(int i = 0; i < 10; i++)
				{
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					System.out.println("sleep 1 second");
				}
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("over");

	}
	
	
	

}
