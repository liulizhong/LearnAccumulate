package alltool.dateortime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName GetDateaddnum
 * @create 2020-06-12 13:39
 * @Des TODO
 */
public class GetDateAddNum {
    public static void main(String[] args) {
        System.out.println(getLastsDay("2020-06-10",3));
    }

    /**
     * 取n(daysAgo)天前日期:输入格式String(2020-06-10)输出格式String(2020-06-07)
     *
     * @param time
     * @return
     */
    public static String getLastsDay(String time, int daysAgo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        //                      此处修改为+1则是获取后一天
        calendar.set(Calendar.DATE, day - daysAgo);
        String lastDay = sdf.format(calendar.getTime());
        return lastDay;
    }
}
