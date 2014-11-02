package cn.tj.ykt.financialoffice.kernel.service;

import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.test.BaseTest;

public class DownDataServiceTest extends BaseTest {

    @Test
    public void test001() {

        try {

            MessageBroker messageBroker = new MessageBroker();
            messageBroker.setReportId("MerchantSettleReport");

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
