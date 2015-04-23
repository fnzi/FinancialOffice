package cn.tj.ykt.financialoffice.fw.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.handler.impl.GlTrnVo;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：A3凭证导入类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("a3PluginService")
public class A3PluginServiceImpl extends AbstractA3PluginService {

    public List<Gl_Trntm> getConfig(String reportId) {
        // A3
        ConfigurationContext context = XmlContext.getContext();
        List<Gl_Trntm> glTrntms = context.getA3Pluginer(reportId).getGl_Trntm();
        return glTrntms;
    }

    public void importDb(List<GlTrnVo> list) {
        A3PluginImportDbComponent a3PluginImportDbComponent  = (A3PluginImportDbComponent) SpringUtil.getBean("a3PluginImportDbComponent");
        a3PluginImportDbComponent.importDb(list);
    }
}
