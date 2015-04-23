package cn.tj.ykt.financialoffice.system.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 功能描述：A3导入系配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class A3Pluginer {

    /** 功能标示 */
    private String funcId;
    /** 插件接口url */
    private String url;

    /** A3凭证vo */
    private List<Gl_Trntm> gl_Trntm = new ArrayList<Gl_Trntm>();

    /** A3调整凭证vo */
    private List<Gl_Trntm> gl_Trntm_Adjust = new ArrayList<Gl_Trntm>();

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

    public List<Gl_Trntm> getGl_Trntm() {
        return gl_Trntm;
    }

    public void setGl_Trntm(List<Gl_Trntm> gl_Trntm) {
        this.gl_Trntm = gl_Trntm;
    }

    public List<Gl_Trntm> getGl_Trntm_Adjust() {
        return gl_Trntm_Adjust;
    }

    public void setGl_Trntm_Adjust(List<Gl_Trntm> gl_Trntm_Adjust) {
        this.gl_Trntm_Adjust = gl_Trntm_Adjust;
    }
}
