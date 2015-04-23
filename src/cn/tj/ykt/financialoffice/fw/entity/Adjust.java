package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 功能描述：调整信息实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Entity
@Table(name = "sys_adjust")
public class Adjust implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "adjustId", unique = true, nullable = false)
    private Long adjustId;// 调整id

    @Column(name = "reportTime")
    private String reportTime;// 业务日期

    @Column(name = "adjustSystem")
    private String adjustSystem;// 调整业务系统名称

    @Column(name = "adjustBusiness")
    private String adjustBusiness;// 调整的业务

    @Column(name = "reportId")
    private String reportId;

    @Column(name = "reportName")
    private String reportName;

	@Column(name = "adjustReason")
    private String adjustReason;// 调整的原因

    @Column(name = "uid")
    private int uid;// 调整人

    @Column(name = "adjuster")
    private String adjuster;// 调整人

    @Column(name = "adjustTime")
    private String adjustTime;// 调整时间

    @Column(name = "oldValue")
    private String oldValue;// 调整前金额

    @Column(name = "newValue")
    private String newValue;// 调整后金额

    @Column(name = "flag")
    private String flag;// 收款/付款

    /**
     * <pre>
     * 调整状态
     * ------31：(初始插入)
     * ------32：(审核之后)
     * ------33：(生成凭证之后)
     * ------39：(初始化删除)
     * </pre>
     */
    @Column(name = "status")
    private String status;

    // 审核人
    @Column(name = "checkerId")
    private String checkerId;

    // 审核人
    @Column(name = "checker")
    private String checker;

    // 审核信息
    @Column(name = "checkMsg")
    private String checkMsg;

    public Long getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(Long adjustId) {
        this.adjustId = adjustId;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getAdjustSystem() {
        return adjustSystem;
    }

    public void setAdjustSystem(String adjustSystem) {
        this.adjustSystem = adjustSystem;
    }

    public String getAdjustBusiness() {
        return adjustBusiness;
    }

    public void setAdjustBusiness(String adjustBusiness) {
        this.adjustBusiness = adjustBusiness;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    public String getAdjuster() {
        return adjuster;
    }

    public void setAdjuster(String adjuster) {
        this.adjuster = adjuster;
    }

    public String getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(String adjustTime) {
        this.adjustTime = adjustTime;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getCheckMsg() {
        return checkMsg;
    }

    public void setCheckMsg(String checkMsg) {
        this.checkMsg = checkMsg;
    }

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
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }
}
