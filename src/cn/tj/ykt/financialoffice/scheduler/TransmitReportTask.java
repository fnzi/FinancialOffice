package cn.tj.ykt.financialoffice.scheduler;

import org.springframework.stereotype.Service;

@Service("transmitReportTask")
public class TransmitReportTask implements Task {

    public void doing() {
        System.out.println("fnzi");
    }
}
