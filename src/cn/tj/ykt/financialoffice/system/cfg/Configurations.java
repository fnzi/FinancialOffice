package cn.tj.ykt.financialoffice.system.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<ExportQueryData> exportQueryData = new ArrayList<ExportQueryData>();

    public Map<String, Configuration> getCfgs() {
        return cfgs;
    }

    public void setCfgs(Map<String, Configuration> cfgs) {
        this.cfgs = cfgs;
    }

    public List<ExportQueryData> getExportQueryData() {
        return exportQueryData;
    }

    public void setExportQueryData(List<ExportQueryData> exportQueryData) {
        this.exportQueryData = exportQueryData;
    }

}
