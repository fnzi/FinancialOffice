package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

public class JspResult {

    /** 返回的jsp路径 */
    private String path;
    /** 返回到jsp的数据 */
    private Map<String, Object> data;

    public JspResult() {
    }

    public JspResult(String path, Map<String, Object> data) {
        this.path = path;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
