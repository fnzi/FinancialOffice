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
            String filePath = "D:\\test\\3.10商户结算日报（日结）.xls";
            messageBroker.setTempFilePath(filePath);

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test003() {
        try {
            MessageBroker messageBroker = new MessageBroker();
            messageBroker.setReportId("MerchantSettleReport");
            String filePath = "f:\\wzhTest\\3.10商户结算日报（日结）.xls";
            messageBroker.setTempFilePath(filePath);

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test005() {
        try {
            MessageBroker messageBroker = new MessageBroker();
            messageBroker.setReportId("MerchantSettleWeekReport");
            String filePath = "f:\\wzhTest\\3.11商户结算日报（周结）.xls";
            messageBroker.setTempFilePath(filePath);

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test007() {
        try {
            MessageBroker messageBroker = new MessageBroker();
            messageBroker.setReportId("MerchantSettleMonthReport");
            String filePath = "f:\\wzhTest\\3.12商户结算日报（月结）.xls";
            messageBroker.setTempFilePath(filePath);

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test009() {
        try {
            MessageBroker messageBroker = new MessageBroker();
            messageBroker.setReportId("JCTSaleSettleReport");
            String filePath = "f:\\wzhTest\\津城通售卡统计结算报表.xls";
            messageBroker.setTempFilePath(filePath);

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test011() {
        try {
            MessageBroker messageBroker = new MessageBroker();
            messageBroker.setReportId("RealVIPRechargeDaily");
            String filePath = "f:\\wzhTest\\福利卡充值日报.xls";
            messageBroker.setTempFilePath(filePath);

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
