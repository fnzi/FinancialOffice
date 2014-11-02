package cn.tj.ykt.financialoffice.kernel.internal;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.kernel.internal.model.Plugins;

/**
 * <pre>
 * 功能描述：内核处理接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface Process {
    public abstract Boolean init();

    public abstract void destory();

    public abstract void deliver(MessageBroker messageBroker);

    public abstract Plugins getPlugins();
}
