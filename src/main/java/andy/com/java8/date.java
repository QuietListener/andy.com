package andy.com.java8;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class date {

    @Test
    public void test(){
        DateTime today = DateTime.now();
        DateTime sameDayLastWeek = today.minusWeeks(1);
        DateTime mondayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.MONDAY);
        DateTime fridayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.SUNDAY);

        System.out.println(mondayLastWeek);
        System.out.println(fridayLastWeek);

    }

    /**
     * 处理时区
     */
    private static long time = 1563590334743l;
    @Test
    public void test1(){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(time);
        System.out.println(dateFormat1.getTimeZone().getRawOffset()/1000/60/60+"  "+dateFormat1.format(d));

        TimeZone tz = TimeZone.getDefault();
        tz.setRawOffset(-60*60*1000);
        dateFormat2.setTimeZone(tz);
        System.out.println(dateFormat2.getTimeZone().getRawOffset()/1000/60/60+"  "+dateFormat2.format(d));

    }
}
