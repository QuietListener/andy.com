package andy.com.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestRateLimiter {

    private RateLimiter rateLimiter = RateLimiter.create(1,200,TimeUnit.MILLISECONDS);

    static private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss:SSS");

    @Test
    public void test1() throws Exception {

        Date start = new Date();
        int count = 0;
        System.out.println("");

        for(int i = 0;i < 10;i++){
            boolean ret = rateLimiter.tryAcquire();
            String date = sdf.format(new Date());
            if(ret){
                System.out.println(date+"-"+i+":"+ret);
                count+=1;
            }else{
                System.out.println(date+"-"+i+":"+ret);
            }


            if(i>0&&i%3 == 0){
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }

        Date end = new Date();
        System.out.println("timeused = "+(end.getTime()-start.getTime())+"  count = " +count);
    }

    @Test
    public void test2() throws Exception{
        RateLimiter rateLimiter = RateLimiter.create(2.0,0,TimeUnit.MILLISECONDS);  //创建一个QPS=2的限流器

        System.out.println(rateLimiter.acquire(1));  //不会等待
        System.out.println(rateLimiter.acquire(1));  //等待0.5秒，债务转移
        TimeUnit.SECONDS.sleep(1);
        //因为闲置了一段时间，桶中已经存在令牌，能够应对突发流量
        System.out.println(rateLimiter.acquire(1));  //不会等待
        System.out.println(rateLimiter.acquire(1));  //不会等待
        System.out.println(rateLimiter.acquire(1));  //不会等待
        System.out.println(rateLimiter.acquire(1));  //等待0.5秒，债务转移
        System.out.println(rateLimiter.acquire(1));  //等待0.5秒，债务转移
        //流量饱和，请求会以2个/秒的速度执行
    }

    @Test
    public void test3() throws Exception{

    }


}
