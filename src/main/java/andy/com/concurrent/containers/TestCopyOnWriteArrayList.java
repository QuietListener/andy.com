package andy.com.concurrent.containers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * java5使用并发容器替代了同步容器 CopyOnWriteArrayList 替代了Collections.synchronizedList  以获取更好的性能
 * @author Admin
 *
 */
public class TestCopyOnWriteArrayList {

	//ConcurrentHashMap 使用分离锁，有限数量的鞋线程可以同时修改map 任意数量的读线程访问，但是他提供的是弱一致性，
	//不会抛出ConcurrentModificationException 
	static List<Integer> list =  new CopyOnWriteArrayList<>();
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		for(int i = 0; i < 10; i++) list.add(i);
	
		Thread t = new Thread(){
			@Override
			public void run() {
				
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					list.remove(9); //删除了9但是会打印9
					System.out.println(Thread.currentThread().getName() +"remove:"+9);
					System.out.println("删除了9之后 也会打印9");
				}
			
		};
		
		t.start();
		
		// 不会抛出异常	ConcurrentModificationException	会打印9
		for(int i : list)
		{
			System.out.println(i);
			TimeUnit.SECONDS.sleep(1);
		}
		
		System.out.println("删除了9之后 不会打印9");
		
		// 不会抛出异常	ConcurrentModificationException	不会打印9
		for(int i : list)
		{
			System.out.println(i);
			TimeUnit.SECONDS.sleep(1);
		}
	}

}
