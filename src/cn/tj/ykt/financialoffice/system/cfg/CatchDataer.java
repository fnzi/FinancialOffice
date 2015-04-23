package cn.tj.ykt.financialoffice.system.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 功能描述：抓取数据系配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class CatchDataer {

    /** 权限url */
    private String authUrl;
    /** 权限用户名 */
    private String authUsername;
    /** 权限密码 */
    private String authPassword;

    /** 文件下载路径 */
    private String downFileUrl;

    /** excel模板header */
    private List<Header> headers = new ArrayList<Header>();

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getAuthUsername() {
        return authUsername;
    }

    public void setAuthUsername(String authUsername) {
        this.authUsername = authUsername;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public String getDownFileUrl() {
        return downFileUrl;
    }

    public void setDownFileUrl(String downFileUrl) {
        this.downFileUrl = downFileUrl;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }
}
