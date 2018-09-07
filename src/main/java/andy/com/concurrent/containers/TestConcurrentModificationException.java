package andy.com.concurrent.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 当一个容器在迭代过程中被其他线程修改了会抛出一个 ConcurrentModificationException
 * @author Admin
 *
 */
public class TestConcurrentModificationException {

	static List<Integer> list =   Collections.synchronizedList(new ArrayList<Integer>());
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		for(int i = 0; i < 10; i++) list.add(i);
	
		Thread t = new Thread(){
			@Override
			public void run() {
				for(int i = 0; i< 3; i++)
				{			
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					list.remove(1);
				}
			}
		};
		
		t.start();
// 不会抛出异常	ConcurrentModificationException	
//		for(int i = 0; i < list.size() ;i++)
//		{
//			System.out.println(list.get(i));
//			TimeUnit.SECONDS.sleep(1);
//		}
		
		for(int i:list)
		{
			System.out.println(list.get(i));
			TimeUnit.SECONDS.sleep(1);
		}
	}

}
