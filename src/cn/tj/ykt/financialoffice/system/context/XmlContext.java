package cn.tj.ykt.financialoffice.system.context;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.tj.ykt.financialoffice.system.cfg.A3Pluginer;
import cn.tj.ykt.financialoffice.system.cfg.CatchDataer;
import cn.tj.ykt.financialoffice.system.cfg.Configuration;
import cn.tj.ykt.financialoffice.system.cfg.Configurations;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.Viewer;
import cn.tj.ykt.financialoffice.system.cfg.mapping.FromXmlMappingDeal;
import cn.tj.ykt.financialoffice.system.cfg.mapping.MappingDeal;

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
        return configurations.getCfgs().get(id).getViewer();
    }

    public CreateTabler getCreateTabler(String id) {
        return configurations.getCfgs().get(id).getCreateTabler();
    }

    public CatchDataer getCatchDataer(String id) {
        return configurations.getCfgs().get(id).getCatchDataer();
    }

    public A3Pluginer getA3Pluginer(String id) {
        return configurations.getCfgs().get(id).getA3Pluginer();
    }

    public static void main(String[] args) {
        ConfigurationContext context = XmlContext.getContext();
        Viewer viewer = context.getViewer("MerchantSettleReport");

        System.out.println(context.getConfigurations());
        System.out.println(viewer);
        System.out.println(context.getCreateTabler("MerchantSettleReport"));
    }
}
