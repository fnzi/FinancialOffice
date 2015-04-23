package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

@Deprecated
@Service("checkSessionService")
public class CheckSessionService extends JspService {

    @Override
    public JspResult doExecute(Map<String, Object> param) {
        Map<String, Object> ret = new HashMap<String, Object>();

        // ret.put("username", getSessionUser().getUsername());

        String username = getParam(param, "username");

        /** 赋值到session中 */
        putSession("username", username);

        /** 获取session中的值 */
        String usernameInsession = (String) getSession("username");

        /** 设置session登录用户 */
        setSessionUser(new User());

        /** 获取session登陆用户 */
        User userInSession = getSessionUser();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String threadname = getParam(param, "threadname");

        System.out.println(Thread.currentThread().getName() + ": " + threadname + "__" + getSession("username"));

        return new JspResult("test/checkSession", ret);
    }

}
