package andy.com.stuff;

import java.util.Date;

public class Test {
    static public void main(String [] args){
        boolean ret = "can't stand".matches(".*[^a-zA-Z.'\\s_\\-].*");
        System.out.println(ret);

        Date d = new Date(1560762692963l);
        System.out.println(d);

    }
}
