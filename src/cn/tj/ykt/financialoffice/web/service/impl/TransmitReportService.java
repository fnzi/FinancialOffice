package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.service.WebService;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.kernel.service.DownDateService;

/**
 * <pre>
 * 报表传输服务 主要包含以下逻辑：
 * 1.抓取excel
 * 2.解析存DB
 * </pre>
 */
@Service("transmitReportService")
public class TransmitReportService implements WebService<Map<String, Object>, Void> {

    @Resource
    DownDateService downDateService;

    @Override
    public Void execute(Map<String, Object> param) {
        MessageBroker message = new MessageBroker();
        
        downDateService.execute(message);
        return null;
    }

}
