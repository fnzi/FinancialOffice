package cn.tj.ykt.financialoffice.kernel.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.kernel.internal.ProcessImpl;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.cfg.Header;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：内核暴露业务实现类（同步日报数据）
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("downDataService")
public class DownDataServiceImpl implements DownDataService {

    protected MessageBroker init(MessageBroker messageBroker) {

        String reportId = messageBroker.getReportId();
        if (reportId == null || "".equals(reportId)) {
            throw new RuntimeException("reportId 不存在");
        }

        ConfigurationContext context = XmlContext.getContext();

        String reportName = context.getConfiguration(reportId).getName();
        messageBroker.setReportName(reportName);

        String tableName = context.getCreateTabler(reportId).getTableName();
        Map<String, Column> columns = context.getCreateTabler(reportId).getColumns();
        messageBroker.setTableName(tableName);
        messageBroker.setColumns(columns);

        List<Header> headers = context.getCatchDataer(reportId).getHeaders();
        messageBroker.setHeaders(headers);

        // A3
        List<Gl_Trntm> glTrntms = context.getA3Pluginer(reportId).getGl_Trntm();
        messageBroker.setGlTrntms(glTrntms);

        return messageBroker;
    }

    public MessageBroker execute(MessageBroker messageBroker) {
        // 获取配置信息
        messageBroker = init(messageBroker);

        cn.tj.ykt.financialoffice.kernel.internal.Process process = ProcessImpl.getInstance();
        if (process.init().booleanValue()) {
            process.deliver(messageBroker);
        }
        return messageBroker;
    }
}
