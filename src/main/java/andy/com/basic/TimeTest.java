package andy.com.basic;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {

    public static void main(String args[]){

        Date d = new Date(1528819200000l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(d));

        Date d1 = new Date(1558367999000l);
        System.out.println("d1 = "+d1);
        System.out.println(sdf.format(d1));

        Date beginOfToday = DateTime.now().withTimeAtStartOfDay().toDate();
        System.out.println("begin of day");
        System.out.println(beginOfToday.getTime());
        System.out.println(getDayBeginTimestamp(new Date().getTime()));
        System.out.println("begin of day end");

        Date d2 = new Date(1557647374000l);
        System.out.println(d2);


        DateTime d3 = new DateTime(2020,5,20,23,59,59);
        long d3l = d3.getMillis();
        System.out.println("\r\nd3l="+d3l);
        System.out.println("d3l time = "+new Date(d3l));

        System.out.println(new Date(1563437166l*1000));


        Date dd = new Date();
        System.out.println(dd);
        System.out.print(dd.getTime());

        DateTime dt =  DateTime.now();
        System.out.println("year = "+ dt.getYear()+ " month = "+dt.getMonthOfYear());
        System.out.println( dt.withDayOfMonth(1).withTimeAtStartOfDay()+"\r\n");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf1.format(new Date()));


        DateTime dt1 = DateTime.now().withTimeAtStartOfDay();
        System.out.println(dt1);

        System.out.println(new Date().getTime()/1000);


        DateTime ddd = new DateTime(2018,8,22,0,0,0);
        System.out.print(ddd.toDate().getTime()/1000);
    }

    public static long getDayBeginTimestamp(long timeStamp){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DATE);

        Calendar x = Calendar.getInstance();
        x.set(y,m,d,0,0,0);
        x.set(Calendar.MILLISECOND,0);
        return x.getTimeInMillis();
    }

}
