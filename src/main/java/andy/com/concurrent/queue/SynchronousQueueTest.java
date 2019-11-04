package andy.com.concurrent.queue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {

    static public void main(String[] args) throws Exception {
        SynchronousQueue queue = new SynchronousQueue();

        Thread producer = new Thread() {
            public void run() {
                while (true) {
                    try {
                        int rand = new Random().nextInt(1000);
                        put("生产了：" + rand);
                        put("等待2秒后运送出去..." + " queue.isEmpty()=" + queue.isEmpty());
                        TimeUnit.SECONDS.sleep(1);
                        queue.put(rand);
                        put("送出了：" + rand + " queue.isEmpty()=" + queue.isEmpty());
                        System.out.println("-------------------\r\n\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        };

        producer.setName("producer");

        Thread consumer = new Thread() {

            public void run() {

                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        put(" queue.isEmpty()=" + queue.isEmpty());
                        put("消费了一个产品:" + queue.take());
                        System.out.println("-------------------\r\n\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };

        consumer.setName("consumer");

        consumer.start();
        producer.start();

        TimeUnit.SECONDS.sleep(100);

    }

    public static void put(String str){
        System.out.println(Thread.currentThread().getName()+":  "+ str);
    }

}
