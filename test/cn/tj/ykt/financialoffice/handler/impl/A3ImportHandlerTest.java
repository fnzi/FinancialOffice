package cn.tj.ykt.financialoffice.handler.impl;

import java.util.List;

import org.junit.Test;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;
import cn.tj.ykt.financialoffice.test.BaseTest;

public class A3ImportHandlerTest extends BaseTest {

    @Test
    public void test001() {
        MessageBroker messageBroker = new MessageBroker();
        String reportId = "MerchantSettleReport";

        ConfigurationContext context = XmlContext.getContext();

        List<Gl_Trntm> glTrntms = context.getA3Pluginer(reportId).getGl_Trntm();
        messageBroker.setGlTrntms(glTrntms);

        A3ImportHandler handler = new A3ImportHandler();

        handler.process(messageBroker);
    }
}
