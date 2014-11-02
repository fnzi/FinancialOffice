package cn.tj.ykt.financialoffice.fw.helper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <pre>
 * 功能描述：获取spring环境
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    public static Object getBean(String name) {
        checkApplicationContext();
        return SpringUtil.applicationContext.getBean(name);
    }

    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null)
            throw new IllegalStateException("applicaitonContext is null");
    }
}