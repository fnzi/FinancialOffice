package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AdjustGridServiceTest {

    @Test
    public void test001() {
        String ret = "1234567,\n";

        int len = ret.length();

        ret = ret.substring(0, len - 2);
        System.out.println(ret);
    }
    
    @Test
    public void test002() {
        String[] ret = new String[2];

        ret[0] = "2";
        ret[1] = "3";
        System.out.println(ret.toString());
        
        List<String> ret1 = new ArrayList<String>();

        ret1.add("a");
        ret1.add("d");
        System.out.println(ret1.toString());
    }
}
