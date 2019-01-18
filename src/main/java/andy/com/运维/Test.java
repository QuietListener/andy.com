package andy.com.运维;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 会导致cpu过高
 */
public class Test {
    static private Map<String,MyObj> hashMap = new HashMap<>();

    static class MyObj{
        String str ;
        public MyObj(String str){
            this.str = str;
        }
    }
    public static void main(String [] args) throws Exception{
        startThread(2);
        TimeUnit.SECONDS.sleep(600);
    }

    public static void startThread(int n){

        for(int i =0; i < n; i++){
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        long a = 1+1+ new Random().nextInt(999999999)+new Random().nextInt(999999999);
                        String b = a+"";
                        hashMap.put(b,new MyObj(b));
                    }
                }
            });

            t1.setName("runner-"+ i);
            t1.setDaemon(true);
            t1.start();
        }

    }

}
