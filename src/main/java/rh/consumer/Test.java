package rh.consumer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time1 = "2019-08-17 00:00:00";

        convertStamp(dateFormat, time1);

    }
    // 日期转换成时间戳
    public static void convertStamp(SimpleDateFormat format, String time)throws Exception {
        Date date = format.parse(time);
        System.out.println(date.getTime());
    }

    // 时间戳转换成日期
    public static void convertDate(SimpleDateFormat sdf, long stamp) {
        String date = sdf.format(new Date(stamp));
        System.out.println(date);
    }
}
