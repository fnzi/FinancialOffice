package cn.tj.ykt.financialoffice.fw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：A3凭证调整导入类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("a3AdjustPluginService")
public class A3AdjustPluginServiceImpl extends AbstractA3PluginService {

    public List<Gl_Trntm> getConfig(String reportId) {
        // A3
        ConfigurationContext context = XmlContext.getContext();
        List<Gl_Trntm> glTrntms = context.getA3Pluginer(reportId).getGl_Trntm_Adjust();
        return glTrntms;
    }

}
