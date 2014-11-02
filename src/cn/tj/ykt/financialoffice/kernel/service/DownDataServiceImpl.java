package cn.tj.ykt.financialoffice.kernel.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.kernel.internal.ProcessImpl;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;
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

        String tableName = context.getCreateTabler(reportId).getTableName();
        Map<String, Column> columns = context.getCreateTabler(reportId).getColumns();
        messageBroker.setTableName(tableName);
        messageBroker.setColumns(columns);

        String col = context.getCatchDataer(reportId).getDatacol();
        String row = context.getCatchDataer(reportId).getDatarow();
        messageBroker.setCol(Integer.parseInt(col));
        messageBroker.setRow(Integer.parseInt(row));

        // TODO 临时
        String filePath = "D:\\test\\3.10商户结算日报（日结）.xls";
        messageBroker.setTempFilePath(filePath);

        return messageBroker;
    }

    public MessageBroker execute(MessageBroker messageBroker) {
        try {
            // 获取配置信息
            messageBroker = init(messageBroker);

            cn.tj.ykt.financialoffice.kernel.internal.Process process = ProcessImpl.getInstance();
            if (process.init().booleanValue()) {
                process.deliver(messageBroker);
            }
            return messageBroker;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
