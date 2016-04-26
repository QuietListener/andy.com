package andy.com.concurrent.containers;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * java5使用并发容器替代了同步容器 ConcurrentHashMap 替代了Collections.synchronizedMap 以获取更好的性能
 * @author Admin
 *
 */
public class TestConcurrentHashMap {

	//ConcurrentHashMap 使用分离锁，有限数量的鞋线程可以同时修改map 任意数量的读线程访问，但是他提供的是弱一致性，
	//不会抛出ConcurrentModificationException 
	static Map<Integer,Integer> map =  new ConcurrentHashMap<>();
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		for(int i = 0; i < 10; i++) map.put(i,i);
	
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
					
					map.remove(i);
					System.out.println(Thread.currentThread().getName() +"remove:"+i);
				}
			}
		};
		
		t.start();

		// 不会抛出异常	ConcurrentModificationException	
		for(Map.Entry<Integer,Integer> entry : map.entrySet())
		{
			System.out.println(entry.getKey());
			TimeUnit.SECONDS.sleep(1);
		}
	}

}
