package cn.tj.ykt.financialoffice.fw.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * <pre>
     * 日期格式转换
     * @param str
     * @param formatBefore
     * @param formatAfter
     * @return
     * </pre
     * 
     * @throws Exception
     */
    public static String formatConversion(String str, String formatBefore, String formatAfter) throws Exception {
        SimpleDateFormat sdf_before = new SimpleDateFormat(formatBefore);
        SimpleDateFormat sdf_after = new SimpleDateFormat(formatAfter);

        Date date;
        try {
            date = sdf_before.parse(str);
            return sdf_after.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * <pre>
     * 获取当前格式的时间
     * </pre
     */
    public static String current(String format) {
        Date dt = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(dt);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * <pre>
     * 获取当月第i天是几号
     * i>0:从月初往后数
     * i=0:月末最后一天
     * i<0:从月末往前数
     * </pre
     */
    public static String getThisDayOfMonth(int i, String dayFormat) {

        SimpleDateFormat format = new SimpleDateFormat(dayFormat);

        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.set(Calendar.DAY_OF_MONTH, i);
        String day = format.format(cal_1.getTime());

        return day;
    }

    /**
     * <pre>
     * 获取昨天__String
     * </pre
     */
    public static String getYesterday(String dayFormat) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = new SimpleDateFormat(dayFormat).format(cal.getTime());
        return yesterday;
    }

    /**
     * <pre>
     * 获取昨天__Date
     * </pre
     */
    public static Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * <pre>
     * 获取今天
     * 如果今天是每月的1号，那么取T-1
     * 如果非每月1号，那么取今天
     * </pre
     */
    public static Date getThisMonthToday() {
        Calendar cal = Calendar.getInstance();

        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        return cal.getTime();
    }
}
