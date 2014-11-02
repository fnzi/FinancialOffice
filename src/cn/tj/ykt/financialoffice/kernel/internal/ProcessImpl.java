package cn.tj.ykt.financialoffice.kernel.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import cn.tj.ykt.financialoffice.kernel.internal.handler.Handler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.kernel.internal.model.Plugin;
import cn.tj.ykt.financialoffice.kernel.internal.model.Plugins;

/**
 * <pre>
 * 功能描述：内核处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class ProcessImpl implements Process {

    public static String PLUGIN_PATH = "/process-plugin.xml";

    private static Process process = new ProcessImpl();

    public static Boolean hasInit = Boolean.FALSE;

    private Plugins plugins;

    private Map<String, Handler> handlers;

    public void setPlugins(Plugins plugins) {
        this.plugins = plugins;
    }

    private ProcessImpl() {
    }

    public static Process getInstance() {
        return process;
    }

    @Override
    public Boolean init() {
        if (!hasInit.booleanValue()) {
            synchronized (this) {
                if (!hasInit.booleanValue()) {
                    try {
                        Plugins plugins = new Plugins();
                        JAXBContext context = JAXBContext.newInstance(new Class[] { plugins.getClass() });
                        Unmarshaller um = context.createUnmarshaller();
                        plugins = (Plugins) um.unmarshal(getClass().getResourceAsStream(PLUGIN_PATH));
                        setPlugins(plugins);
                        hasInit = Boolean.TRUE;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Boolean.FALSE;
                    }
                }
            }
        }

        return Boolean.valueOf(true);
    }

    @Override
    public void destory() {
        Iterator<String> iter = getHandlers().keySet().iterator();
        while (iter.hasNext()) {
            ((Handler) this.handlers.get(iter.next())).destory();
        }
        hasInit = Boolean.FALSE;
        this.plugins = null;
        this.handlers = null;
    }

    @Override
    public void deliver(MessageBroker messageBroker) {
        List<Plugin> plugin = getPlugins().getPlugins();
        try {
            for (int i = 0; i < plugin.size(); i++) {
                Plugin p = (Plugin) plugin.get(i);

                Handler handler = (Handler) getHandlers().get(p.getClassName());
                if (handler == null) {
                    handler = (Handler) Class.forName(p.getClassName()).newInstance();
                    handler.init();
                    getHandlers().put(p.getClassName(), handler);
                }

                String returnValue = handler.process(messageBroker);
                messageBroker.setReturnValue(returnValue);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Plugins getPlugins() {
        return this.plugins;
    }

    public Map<String, Handler> getHandlers() {
        if (this.handlers == null) {
            this.handlers = new HashMap<String, Handler>();
        }
        return this.handlers;
    }

    public void setHandlers(Map<String, Handler> handlers) {
        this.handlers = handlers;
    }
}
