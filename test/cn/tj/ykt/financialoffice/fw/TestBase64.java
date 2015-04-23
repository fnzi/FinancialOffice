package cn.tj.ykt.financialoffice.fw;

import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.util.Base64Util;

public class TestBase64 {

    @Test
    public void test001() {
        System.out.println(Base64Util.dec("MTIzNDU2"));
        System.out.println(Base64Util.enc("123456"));
    }
}
