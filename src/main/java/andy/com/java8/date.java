package andy.com.java8;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
    public void testDateSdf(){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(time);
        System.out.println(dateFormat1.getTimeZone().getRawOffset()/1000/60/60+"  "+dateFormat1.format(d));

        TimeZone tz = TimeZone.getDefault();
        tz.setRawOffset(-60*60*1000);
        dateFormat2.setTimeZone(tz);
        System.out.println(dateFormat2.getTimeZone().getRawOffset()/1000/60/60+"  "+dateFormat2.format(d));

    }

    @Test
    public void testddd(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));
    }


    @Test
    public void testTimeZone(){

        Calendar c = Calendar.getInstance();
        Date d = new Date(1564211553*1000l);
        c.setTime(d);
        TimeZone tzLocal = TimeZone.getDefault();
        TimeZone tz0 = TimeZone.getDefault();
        tz0.setRawOffset(-160);
        c.setTimeZone(tzLocal);
        System.out.println(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE)+" "+c.get(Calendar.HOUR_OF_DAY));
        c.setTimeZone(tz0);
        System.out.println(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE)+" "+c.get(Calendar.HOUR_OF_DAY));
        System.out.println(c.getTime());

        Date d1 = changeTimeZone(d,tzLocal,tz0);
        System.out.println(d1);

    }

    public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
        Date dateTmp = null;
        if (date != null) {
            int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
            dateTmp = new Date(date.getTime() - timeOffset);
        }
        return dateTmp;
    }
}
