package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 功能描述：网点信息实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Entity
@Table(name = "sys_networkinfo")
public class NetWorkInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "nwid", unique = true, nullable = false)
    private Long nwid;

    @Column(name = "networkname")
    private String networkName;

    @Column(name = "networkcode")
    private String networkCode;

    @Column(name = "address")
    private String address;

    @Column(name = "flag")
    private String flag;

    public Long getNwid() {
        return nwid;
    }

    public void setNwid(Long nwid) {
        this.nwid = nwid;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkCode() {
        return networkCode;
    }

    public void setNetworkCode(String networkCode) {
        this.networkCode = networkCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
