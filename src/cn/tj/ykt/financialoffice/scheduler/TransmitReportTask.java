package cn.tj.ykt.financialoffice.scheduler;

import org.springframework.stereotype.Service;

/**
 * <pre>
 * 功能描述：日报同步定时任务
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("transmitReportTask")
public class TransmitReportTask implements Task {

    public void doing() {
        System.out.println("fnzi");
    }
}
