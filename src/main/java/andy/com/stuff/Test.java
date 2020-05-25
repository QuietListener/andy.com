package andy.com.stuff;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    static public void main(String[] args) {
        boolean ret = "can't stand".matches(".*[^a-zA-Z.'\\s_\\-].*");
        System.out.println(ret);

        Date d = new Date(1560762692963l);
        System.out.println(d);

        Map a = new HashMap<Integer, Integer>();
        a.put(1, 1);
        a.put(2, 1);
        System.out.println(a.toString());


        Calendar c = Calendar.getInstance();
        System.out.println("-------");
        System.out.println(c.getTime());
        c.set(2020, 1, 21);
        Date t = c.getTime();
        System.out.println(t);

        Date dd = new Date(1582214400l*1000);
        System.out.println("***");
        System.out.println(dd);
        DateTime dateTime = new DateTime(dd).withTimeAtStartOfDay();

        DateTime dateTime1 = new DateTime(t).withTimeAtStartOfDay();
        System.out.println("---33---");
        System.out.println(dateTime);
        System.out.println(dateTime1);
        System.out.println(dateTime1.equals(dateTime));

        DateTime time = dateTime1.plusDays(1);
        System.out.println(time.toDate().getTime());

        System.out.println(dd.getTime());
        System.out.println(System.currentTimeMillis());


        System.out.println(new Date(1582905601000l));
    }
}
