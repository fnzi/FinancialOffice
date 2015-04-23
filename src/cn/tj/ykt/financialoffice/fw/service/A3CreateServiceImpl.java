package cn.tj.ykt.financialoffice.fw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：A3凭证生成类
 * 创建者：
 * 修改者：许芳
 * </pre>
 */
@Service("A3CreateService")
public class A3CreateServiceImpl extends AbstractA3CreateService {

    public List<Gl_Trntm> getConfig(String reportId) {
        ConfigurationContext context = XmlContext.getContext();
        List<Gl_Trntm> glTrntms = context.getA3Pluginer(reportId).getGl_Trntm_Adjust();
        return glTrntms;
    }

}
