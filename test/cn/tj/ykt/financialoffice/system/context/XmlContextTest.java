package cn.tj.ykt.financialoffice.system.context;

import org.junit.Test;

import cn.tj.ykt.financialoffice.system.cfg.Viewer;

public class XmlContextTest {

    @Test
    public void test001() {
        ConfigurationContext context = XmlContext.getContext();
        Viewer viewer = context.getViewer("MerchantSettleReport");

        System.out.println(context.getConfigurations());
        System.out.println(viewer);
        System.out.println(context.getCreateTabler("MerchantSettleReport"));
    }
}
