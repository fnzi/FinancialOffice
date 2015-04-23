package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 功能描述：调整信息与日报关联实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Entity
@Table(name = "ref_adjust_report")
public class AdjustReport implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "rarid", unique = true, nullable = false)
    private Long rarId;

    /** 调整id */
    @Column(name = "adjustid")
    private Long adjustId;

    /**
     * <pre>
     * 日报中data_type为30状态
     * batch_no为唯一关联条件
     * </pre>
     */
    @Column(name = "reportbatchno")
    private String reportBatchNo;

    public Long getRarId() {
        return rarId;
    }

    public void setRarId(Long rarId) {
        this.rarId = rarId;
    }

    public Long getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(Long adjustId) {
        this.adjustId = adjustId;
    }

    public String getReportBatchNo() {
        return reportBatchNo;
    }

    public void setReportBatchNo(String reportBatchNo) {
        this.reportBatchNo = reportBatchNo;
    }

}
