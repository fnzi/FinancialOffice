package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_custom")
public class MerchantInfo implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long cmid;
    private String code;
    private String mername;
    private String custom;
    @Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cmid", unique = true, nullable = false)
    public Long getCmid() {
		return cmid;
	}
	public void setCmid(Long cmid) {
		this.cmid = cmid;
	}
	
	@Column(name = "code")
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "name")
	public String getMername() {
		return mername;
	}
	public void setMername(String mername) {
		this.mername = mername;
	}
	
	@Column(name = "custom")
	public String getCustom() {
		return custom;
	}
	
	public void setCustom(String custom) {
		this.custom = custom;
	}
}
