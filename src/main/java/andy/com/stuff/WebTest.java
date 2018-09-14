package andy.com.stuff;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebTest
{

    //创建一个默认的HttpClient
    static final HttpClient httpclient = HttpClients.createDefault();

    public static String  fetchContent(String url)
    {


       HttpGet httpget = new HttpGet(url);
       //打印请求地址
       System.out.println("executing request " + httpget.getURI());
       //创建响应处理器处理服务器响应内容
       ResponseHandler responseHandler = new BasicResponseHandler();

       try {
           //执行请求并获取结果
           HttpResponse res = httpclient.execute(httpget);
           HttpEntity entity = res.getEntity();

           String responseBody = EntityUtils.toString(entity);
           return responseBody;
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }

       return null;
   }

   public static void main(String [] args)
   {
        int threads = 10, users = 1000;

        ExecutorService pool = Executors.newFixedThreadPool(threads);


        for(int i = 0; i < users; i++)
        {
            final int order = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    long millis = new Random().nextInt(100);

                    try{
                        String threadName = Thread.currentThread().getName();

                        String url = "http://localhost:8082/test?reqOrder="
                                +order+"&name="+threadName
                                +"&sleepMillis="+millis;


                        String ret = fetchContent(url);

                        if(!threadName.trim().equals(ret.trim()))
                        {
                            System.out.println(threadName+"==>"+ ret+"\r\n");
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
   }
}
