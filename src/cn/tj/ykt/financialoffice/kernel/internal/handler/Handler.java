package cn.tj.ykt.financialoffice.kernel.internal.handler;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：内核处理结点接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface Handler {

    public abstract void init();

    public abstract void destory();

    public abstract String process(MessageBroker messageBroker);
}
