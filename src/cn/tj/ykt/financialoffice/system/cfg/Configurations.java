package cn.tj.ykt.financialoffice.system.cfg;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能描述：配置集合描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class Configurations {

    private Map<String, Configuration> cfgs = new HashMap<String, Configuration>();

    public Map<String, Configuration> getCfgs() {
        return cfgs;
    }

    public void setCfgs(Map<String, Configuration> cfgs) {
        this.cfgs = cfgs;
    }

}
