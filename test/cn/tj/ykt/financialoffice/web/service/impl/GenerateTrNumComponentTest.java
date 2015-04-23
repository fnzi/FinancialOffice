package cn.tj.ykt.financialoffice.web.service.impl;

import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.service.GenerateTrNumComponent;

public class GenerateTrNumComponentTest {

    @Test
    public void test001() {
        GenerateTrNumComponent gtc = new GenerateTrNumComponent();
        
        System.out.println(gtc.doGenerate("121"));
        System.out.println(gtc.doGenerateNum());
    }
}
