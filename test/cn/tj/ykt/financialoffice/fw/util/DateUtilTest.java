package cn.tj.ykt.financialoffice.fw.util;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void test001() throws Exception {
        System.out.println(DateUtil.formatConversion("'20141212'", "'yyyyMMdd'", "MM.dd"));
    }

    @Test
    public void test002() {
        System.out.println(DateUtil.getThisDayOfMonth(1, "yyyy-MM-dd"));

        System.out.println(DateUtil.getYesterday("yyyy-MM-dd"));
    }

    @Test
    public void test004() {
        System.out.println(DateUtil.current("yyyy/MM/dd hh:mm:ss"));
        System.out.println(DateUtil.current("yyyy/MM/dd HH:mm:ss"));
    }

    @Test
    public void test006() {
        System.out.println(DateUtil.getYesterday());
    }

    @Test
    public void test008() {
        System.out.println(DateUtil.getThisMonthToday());
    }
}
