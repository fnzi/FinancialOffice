package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sys_menu")
public class Menu implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long mid;
    private String mname;
    private String mlink;
    private String mmodule;
    private String keyid;
    private String enable;
    private String order;
    private List<Role> roles = new ArrayList<Role>();
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mid", unique = true, nullable = false)
    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    @Column(name = "mname")
    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    @Column(name = "mlink")
    public String getMlink() {
        return mlink;
    }

    public void setMlink(String mlink) {
        this.mlink = mlink;
    }

    @Column(name = "mmodule")
    public String getMmodule() {
        return mmodule;
    }

    public void setMmodule(String mmodule) {
        this.mmodule = mmodule;
    }

    @Column(name = "keyid")
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    @Column(name = "enable")
    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
    @ManyToMany
    @JoinTable(name="sys_refrolemenu",joinColumns={@JoinColumn(name="rmid")},
	inverseJoinColumns={@JoinColumn(name="rrid")})
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Column(name = "order")
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
   

}
