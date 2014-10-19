package cn.tj.ykt.financialoffice.kernel.internal.handler;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

public interface Handler {

    public abstract void init();

    public abstract void destory();

    public abstract String process(MessageBroker messageBroker);
}
