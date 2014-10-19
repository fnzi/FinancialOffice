package cn.tj.ykt.financialoffice.kernel.service;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.kernel.internal.ProcessImpl;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

@Service("downDateService")
public class DownDateServiceImpl implements DownDateService {

    public MessageBroker execute(MessageBroker messageBroker) {
        try {
            cn.tj.ykt.financialoffice.kernel.internal.Process process = ProcessImpl.getInstance();
            if (process.init().booleanValue()) {
                process.deliver(messageBroker);
            }
            return messageBroker;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
