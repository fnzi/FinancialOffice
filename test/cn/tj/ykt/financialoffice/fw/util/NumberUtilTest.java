package cn.tj.ykt.financialoffice.fw.util;

import java.math.BigDecimal;

import org.junit.Test;

public class NumberUtilTest {

    @Test
    public void test002() {
        String d = "1.2e3";
        BigDecimal bd = new BigDecimal(d);
        System.out.println(bd.toPlainString());
    }
    
    @Test
    public void test004() {
        String d = "CBD";
        System.out.println(NumberUtil.isENum(d));
    }
}
