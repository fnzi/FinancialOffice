package cn.tj.ykt.financialoffice.kernel.service;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

public interface DownDateService {

    public MessageBroker execute(MessageBroker messageBroker);
}
