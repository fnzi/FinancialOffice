package cn.tj.ykt.financialoffice.kernel.internal;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.kernel.internal.model.Plugins;

public interface Process {
    public abstract Boolean init();

    public abstract void destory();

    public abstract void deliver(MessageBroker messageBroker);

    public abstract Plugins getPlugins();
}
