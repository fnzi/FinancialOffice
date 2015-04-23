package cn.tj.ykt.financialoffice.system.cfg.util;

import java.util.Map;

import org.junit.Test;

import cn.tj.ykt.financialoffice.system.cfg.Column;

public class ConfigUtilTest {

	@Test
    public void test001() {
        String reportName = ConfigUtil.reportName("RealVIPSaleCheckDaily");
        System.out.println(reportName);
	}
	
	@Test
	public void test002() {
		System.out.println(ConfigUtil.indexColumn("MerchantSettleReport", "sort_index"));
	}
	
	@Test
    public void test004() {
        Map<String, Column> col = ConfigUtil.showCols("MerchantSettleReport");
        for(String key : col.keySet()) {
            System.out.println(key);
        }
    }
}
