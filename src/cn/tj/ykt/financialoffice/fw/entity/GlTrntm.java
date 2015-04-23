package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 功能描述：调整凭证实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Entity
@Table(name = "sys_gl_trntm")
public class GlTrntm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "gtid", unique = true, nullable = false)
    private Long gtid;
    /** 摘要 */
    @Column(name = "tr_note")
    private String tr_note;
    /** 科目代码 */
    @Column(name = "ac_code")
    private String ac_code;
    /** 凭证贷方 */
    @Column(name = "tr_cr")
    private String tr_cr;
    /** 凭证借方 */
    @Column(name = "tr_de")
    private String tr_de;
    /** 客户代码 */
    @Column(name = "tr_custom")
    private String tr_custom;
    /** 调整id */
    @Column(name = "adjustId")
    private Long adjustId;
    /** 状态 */
    @Column(name = "status")
    private String status;

    public Long getGtid() {
        return gtid;
    }

    public void setGtid(Long gtid) {
        this.gtid = gtid;
    }

    public String getTr_note() {
        return tr_note;
    }

    public void setTr_note(String tr_note) {
        this.tr_note = tr_note;
    }

    public String getAc_code() {
        return ac_code;
    }

    public void setAc_code(String ac_code) {
        this.ac_code = ac_code;
    }

    public String getTr_cr() {
        return tr_cr;
    }

    public void setTr_cr(String tr_cr) {
        this.tr_cr = tr_cr;
    }

    public String getTr_de() {
        return tr_de;
    }

    public void setTr_de(String tr_de) {
        this.tr_de = tr_de;
    }

    public String getTr_custom() {
        return tr_custom;
    }

    public void setTr_custom(String tr_custom) {
        this.tr_custom = tr_custom;
    }

    public Long getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(Long adjustId) {
        this.adjustId = adjustId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
