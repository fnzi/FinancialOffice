package cn.tj.ykt.financialoffice.kernel.internal.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
