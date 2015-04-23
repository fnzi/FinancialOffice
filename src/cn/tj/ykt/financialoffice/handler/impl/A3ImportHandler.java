package cn.tj.ykt.financialoffice.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.service.A3PluginService;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;

/**
 * <pre>
 * 功能描述：A3导入处理
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class A3ImportHandler extends DefaultHandler {

    @Override
    public String process(MessageBroker messageBroker) {

        List<Gl_Trntm> glTrntms = messageBroker.getGlTrntms();
        if (glTrntms == null || glTrntms.size() == 0) {
            throw new SystemException("凭证配置信息错误,请检查<gl_trntm/>节点");
        }

        // context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("reportId", messageBroker.getReportId());
        // context.put("batchNo", new
        // ArrayList<String>().add(messageBroker.getBatchNo()));
        context.put("batchNo", messageBroker.getBatchNo());
        context.put("trNum", messageBroker.getTrNum());

        A3PluginService a3PluginService = (A3PluginService) SpringUtil.getBean("a3PluginService");

        /** A3 */
        a3PluginService.execute(context);

        return SUCCESS;
    }

}
