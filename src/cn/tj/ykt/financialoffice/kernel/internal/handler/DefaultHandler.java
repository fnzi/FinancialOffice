package cn.tj.ykt.financialoffice.kernel.internal.handler;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：内核处理结点默认接口实现类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public abstract class DefaultHandler implements Handler {

    public static final String SUCCESS = "OK";
    public static final String FAILURE = "NG";

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }

    @Override
    public abstract String process(MessageBroker messageBroker);

}
