package cn.tj.ykt.financialoffice.kernel.internal.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 功能描述：内核结点信息描述类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@XmlRootElement(name = "plugin")
public class Plugin {

    private String id;
    private String className;
    private String description;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "class")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
