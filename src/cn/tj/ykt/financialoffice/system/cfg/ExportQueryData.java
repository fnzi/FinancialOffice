package cn.tj.ykt.financialoffice.system.cfg;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能描述：导出日报查询条件配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class ExportQueryData {

    private Map<String, String> querys = new HashMap<String, String>();
    private String reportId;
    private String cron;
    private String username = "";
    private String password = "";

    public Map<String, String> getQuerys() {
        return querys;
    }

    public void setQuerys(Map<String, String> querys) {
        this.querys = querys;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
