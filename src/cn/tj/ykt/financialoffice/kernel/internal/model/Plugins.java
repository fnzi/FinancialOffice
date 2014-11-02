package cn.tj.ykt.financialoffice.kernel.internal.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 功能描述：内核结点信息描述集合类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@XmlRootElement(name = "plugins")
public class Plugins {

    private List<Plugin> plugins;

    @XmlElement(name = "plugin")
    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }
}
