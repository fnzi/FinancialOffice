package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.service.WebService;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.kernel.service.DownDataService;

/**
 * <pre>
 * 功能描述：报表传输服务 主要包含以下逻辑：
 *          1.抓取excel
 *          2.解析存DB
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("transmitReportService")
public class TransmitReportService implements WebService<Map<String, Object>, Void> {

    @Resource
    DownDataService downDataService;

    @Override
    public Void execute(Map<String, Object> param) {

        String reportId = (String) param.get("reportid");

        MessageBroker message = new MessageBroker();
        message.setReportId(reportId);

        downDataService.execute(message);
        return null;
    }

}
