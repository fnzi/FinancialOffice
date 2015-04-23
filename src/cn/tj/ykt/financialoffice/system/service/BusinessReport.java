package cn.tj.ykt.financialoffice.system.service;

/**
 * <pre>
 * 功能描述：业务报表实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class BusinessReport {

    /** 业务报表识别id */
    private String reportId;
    /** 业务报表名 */
    private String reportName;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
