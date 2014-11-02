package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sys_role")
public class Role implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long rid;
    private String rname;

    private List<User> users = new ArrayList<User>();
    private List<Menu> menus = new ArrayList<Menu>();
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "rid", unique = true, nullable = false)
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    @Column(name = "rname")
    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    @ManyToMany(fetch = FetchType.LAZY, mappedBy="roles")
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}
