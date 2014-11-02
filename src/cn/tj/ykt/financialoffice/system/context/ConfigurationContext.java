package cn.tj.ykt.financialoffice.system.context;

import cn.tj.ykt.financialoffice.system.cfg.A3Pluginer;
import cn.tj.ykt.financialoffice.system.cfg.CatchDataer;
import cn.tj.ykt.financialoffice.system.cfg.Configuration;
import cn.tj.ykt.financialoffice.system.cfg.Configurations;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.Viewer;

/**
 * <pre>
 * 功能描述：配置文件描述接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface ConfigurationContext {

    public Configurations getConfigurations();

    public Configuration getConfiguration(String id);

    public Viewer getViewer(String id);

    public CreateTabler getCreateTabler(String id);

    public CatchDataer getCatchDataer(String id);

    public A3Pluginer getA3Pluginer(String id);
}
