package cn.tj.ykt.financialoffice.system.cfg;

/**
 * <pre>
 * 功能描述：A3导入系配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class A3Pluginer {

    // 功能标示
    private String funcId;
    // 插件接口url
    private String url;

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
