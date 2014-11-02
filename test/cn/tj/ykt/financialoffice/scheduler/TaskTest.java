package cn.tj.ykt.financialoffice.scheduler;

import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.scheduler.Task;
import cn.tj.ykt.financialoffice.test.BaseTest;

public class TaskTest extends BaseTest {

    @Test
    public void test001() throws Exception {
        Task t = (Task) SpringUtil.getBean("transmitReportTask");

        assertNotNull(t);

        Thread.sleep(1000 * 10L);
    }
}
