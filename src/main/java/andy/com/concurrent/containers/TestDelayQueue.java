package andy.com.concurrent.containers;

import java.util.ArrayList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * delayQueue 需要实现 getDelay 和 compareTo方法。
 * 他会通过 compareTo 来将任务排序，take方法再根据getDelay方法来获取最近需要的执行的任务。
 *
 * @author Admin
 */
public class TestDelayQueue {

    //blockQueue可以指定大小也可以不指定大小
    static DelayQueue<MyTask> q1 = new DelayQueue<MyTask>(new ArrayList<>(2));

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        long now = System.currentTimeMillis();
        System.out.print(now / 1000);
        q1.offer(new MyTask(1, now + 1000 * 10), 10, TimeUnit.MILLISECONDS);
        System.out.println(q1);
        q1.offer(new MyTask(2, now + 1000 * 8), 10, TimeUnit.MILLISECONDS);
        System.out.println(q1);
        q1.offer(new MyTask(3, now + 1000 * 12));
        System.out.println(q1);

        MyTask t = q1.take();
        System.out.println("pool:" + t);
        t = q1.take();
        System.out.println("pool:" + t);
        t = q1.take();
        System.out.println("pool:" + t);
    }
}

class MyTask implements Delayed {

    private long timeMs = 0l;
    private int value = 0;

    public MyTask(int value, long delayMs) {
        this.value = value;
        this.timeMs = delayMs;
    }


    @Override
    public long getDelay(TimeUnit unit) {
        long remain = this.timeMs - System.currentTimeMillis();
        if (unit == TimeUnit.NANOSECONDS) {
            return remain * TimeUnit.MILLISECONDS.toNanos(1);
        } else if (unit == TimeUnit.MICROSECONDS) {
            return remain * TimeUnit.MILLISECONDS.toMicros(1);
        } else if (unit == TimeUnit.MILLISECONDS) {
            return remain;
        } else if (unit == TimeUnit.SECONDS) {
            return remain / 1000l;
        } else if (unit == TimeUnit.MINUTES) {
            return remain / 1000 / 60;
        }

        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.timeMs > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.timeMs < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "timeMs=" + timeMs +
                ", value=" + value +
                '}';
    }
}
