package cn.tj.ykt.financialoffice.fw.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_sparegoldcode")
public class SpareGoldCode implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String name;
    private String spareGoldCode;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "sparegoldcode")
    public String getSpareGoldCode() {
        return spareGoldCode;
    }

    public void setSpareGoldCode(String spareGoldCode) {
        this.spareGoldCode = spareGoldCode;
    }

}
