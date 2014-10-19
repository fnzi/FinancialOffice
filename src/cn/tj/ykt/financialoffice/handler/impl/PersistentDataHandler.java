package cn.tj.ykt.financialoffice.handler.impl;

import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：持久化data到DB
 * </pre>
 */
public class PersistentDataHandler extends DefaultHandler {

    @Override
    public String process(MessageBroker messageBroker) {
        System.out.println("PersistentDataHandler");
        return null;
    }

}
