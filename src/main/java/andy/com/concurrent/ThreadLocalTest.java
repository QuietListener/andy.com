package andy.com.concurrent;

public class ThreadLocalTest {
	
	public static void main(String [] args)
	{
		final ThreadLocal<String> tl = new ThreadLocal<>();
		
		for(int i = 0; i < 3; i++)
		{
			Thread t1 = new Thread(){
				@Override
				public void run() {
					tl.set("123");
					try {
						this.sleep(3000);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}		
					System.out.println(tl.get());
				}
			};
			
			t1.start();
			
			
		}
	}

}
