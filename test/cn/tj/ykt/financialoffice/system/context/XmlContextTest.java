package cn.tj.ykt.financialoffice.system.context;

import org.junit.Test;

import cn.tj.ykt.financialoffice.system.cfg.A3Pluginer;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.Viewer;

public class XmlContextTest {

    @Test
    public void test001() {
        ConfigurationContext context = XmlContext.getContext();

        CreateTabler createTabler = context.getCreateTabler("RechargeDailyQuery");

        Viewer viewer = context.getViewer("MerchantSettleReport");

        A3Pluginer a3Pluginer = context.getA3Pluginer("CompositeCardDepositIncomeReport");

        System.out.println(a3Pluginer);
        System.out.println(context.getConfigurations());
        System.out.println(viewer);
        System.out.println(createTabler);

        System.out.println(context.getConfiguration("MerchantSettleReport").getSystem().getId());
        System.out.println(context.getConfiguration("MerchantSettleReport").getSystem().getName());
    }
}
