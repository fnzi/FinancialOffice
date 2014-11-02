package cn.tj.ykt.financialoffice.kernel.service;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：内核暴露业务接口（同步日报数据）
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface DownDataService {

    public MessageBroker execute(MessageBroker messageBroker);
}
