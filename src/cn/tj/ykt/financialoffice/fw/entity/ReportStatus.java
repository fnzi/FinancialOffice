package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 功能描述：报表导入状态实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Entity
@Table(name = "sys_report_status")
public class ReportStatus implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "rsid", unique = true, nullable = false)
    private Long rsid;
    @Column(name = "reportId")
    private String reportId;
    @Column(name = "reportName")
    private String reportName;
    @Column(name = "reportTime")
    private String reportTime;
    @Column(name = "status")
    private String status;

    public ReportStatus() {

    }

    public ReportStatus(String reportId, String reportName, String reportTime, String status) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportTime = reportTime;
        this.status = status;
    }

    public Long getRsid() {
        return rsid;
    }

    public void setRsid(Long rsid) {
        this.rsid = rsid;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
