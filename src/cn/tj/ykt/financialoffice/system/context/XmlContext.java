package cn.tj.ykt.financialoffice.system.context;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.system.cfg.A3Pluginer;
import cn.tj.ykt.financialoffice.system.cfg.CatchDataer;
import cn.tj.ykt.financialoffice.system.cfg.Configuration;
import cn.tj.ykt.financialoffice.system.cfg.Configurations;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.Viewer;
import cn.tj.ykt.financialoffice.system.cfg.mapping.FromXmlMappingDeal;
import cn.tj.ykt.financialoffice.system.cfg.mapping.MappingDeal;

/**
 * <pre>
 * 功能描述：XML配置文件描述类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class XmlContext implements ConfigurationContext {

    public static String CONTEXT_PATH = "/configuration.xml";
    private Configurations configurations;

    private static XmlContext instance = new XmlContext();

    private XmlContext() {
        MappingDeal fxmd = new FromXmlMappingDeal();
        Map<String, Object> param = new HashMap<String, Object>();

        InputStream fileinputstream = getClass().getResourceAsStream(CONTEXT_PATH);

        param.put("fileinputstream", fileinputstream);

        configurations = fxmd.execute(param);
    }

    public static XmlContext getContext() {
        return instance;
    }

    public Configurations getConfigurations() {
        return configurations;
    }

    public Configuration getConfiguration(String id) {
        return configurations.getCfgs().get(id);
    }

    public Viewer getViewer(String id) {
        try {
            return configurations.getCfgs().get(id).getViewer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("配置解析失败，未发现keyid为" + id + "的viewer配置", e);
        }
    }

    public CreateTabler getCreateTabler(String id) {
        try {
            return configurations.getCfgs().get(id).getCreateTabler();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("配置解析失败，未发现keyid为" + id + "的createTabler配置", e);
        }
    }

    public CatchDataer getCatchDataer(String id) {
        try {
            return configurations.getCfgs().get(id).getCatchDataer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("配置解析失败，未发现keyid为" + id + "的catchDataer配置", e);
        }
    }

    public A3Pluginer getA3Pluginer(String id) {
        try {
            return configurations.getCfgs().get(id).getA3Pluginer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("配置解析失败，未发现keyid为" + id + "的A3Pluginer配置", e);
        }
    }

}
