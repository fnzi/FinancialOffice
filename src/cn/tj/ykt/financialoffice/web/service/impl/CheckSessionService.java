package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

@Service("checkSessionService")
public class CheckSessionService extends JspService {

    @Override
    public JspResult execute(Map<String, Object> param) {
        Map<String, Object> ret = new HashMap<String, Object>();

        // ret.put("username", getSessionUser().getUsername());

        String username = getParam(param, "username");

        putSession("username", username);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String threadname = getParam(param, "threadname");

        System.out.println(Thread.currentThread().getName() + ": " + threadname + "__" + getSession("username"));

        return new JspResult("checkSession", ret);
    }

}
