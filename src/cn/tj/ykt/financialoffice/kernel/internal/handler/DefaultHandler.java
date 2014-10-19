package cn.tj.ykt.financialoffice.kernel.internal.handler;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

public abstract class DefaultHandler implements Handler {

    protected static final String SUCCESS = "OK";
    protected static final String FAILURE = "NG";

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }

    @Override
    public abstract String process(MessageBroker messageBroker);

}
