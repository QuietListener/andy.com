package andy.com.stuff;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    static public void main(String [] args){
        boolean ret = "can't stand".matches(".*[^a-zA-Z.'\\s_\\-].*");
        System.out.println(ret);

        Date d = new Date(1560762692963l);
        System.out.println(d);

        Map a = new HashMap<Integer,Integer>();
        a.put(1,1);
        a.put(2,1);
        System.out.println(a.toString());
    }
}
