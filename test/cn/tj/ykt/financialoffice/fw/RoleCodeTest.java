package cn.tj.ykt.financialoffice.fw;

import org.junit.Assert;
import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.code.RoleCode;

public class RoleCodeTest {

    @Test
    public void test001() {

        // 取得code的key
        System.out.println(RoleCode.REPORT.getKey());
        Assert.assertEquals("report", RoleCode.REPORT.getKey());
        // 取得code的描述
        System.out.println(RoleCode.REPORT.getDescription());
        Assert.assertEquals("报表用户", RoleCode.REPORT.getDescription());
    }
}
