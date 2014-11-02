package cn.tj.ykt.financialoffice.fw.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import cn.tj.ykt.financialoffice.fw.util.Base64Util;

/**
 * <pre>
 * 功能描述：属性文件处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    /** 需要解密处理的属性 */
    private String keyWord = ".enc";

    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {

        Map<String, String> deal = new HashMap<String, String>();

        for (Object key : props.keySet()) {
            String property = key.toString();
            if (property.endsWith(keyWord)) {
                String var = props.getProperty(property);
                if (var != null) {
                    String strDes = Base64Util.dec(var);
                    props.setProperty(property, strDes);

                    deal.put(replaceEnc(property), strDes);
                }
            }
        }

        for (String key : deal.keySet()) {
            props.put(key, deal.get(key));
        }

        super.processProperties(beanFactory, props);
    }

    protected String resolvePlaceholder(String placeholder, Properties props) {
        String prop = null;
        prop = props.getProperty(placeholder);
        return prop;
    }

    private String replaceEnc(String property) {
        property = property.replace(keyWord, "");
        return property;
    }
}
