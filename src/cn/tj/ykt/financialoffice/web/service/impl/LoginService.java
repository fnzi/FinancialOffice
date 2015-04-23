package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.web.service.JsonExtService;
import cn.tj.ykt.financialoffice.web.service.JsonResult;

/**
 * <pre>
 * 功能描述：登陆权限验证处理
 * 创建者：许芳
 * 修改者：闫世峰
 * </pre>
 */
@Service("loginService")
public class LoginService extends JsonExtService {
	
    @Transactional
    public Object doExecute(Map<String, Object> param) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String uname = getParam(param, "username");
		String pwd = getParam(param, "pwd");
		User user;
		if (!"".equals(uname) && uname != null) {
			user = getDao().findByHQL(
					"select su from User su where username=?", uname);
			if (user == null) {
				return new JsonResult(false, "没有该用户！");
			} else {
				String loaduna = user.getUsername();
				String loadpwd = user.getPassword();
				if (pwd.equals(loadpwd) && uname.equals(loaduna)) {
//					 HttpSession session = getParam(param, SESSION_KEY);
					setSessionUser(user);
					ret.put("user", user.getRole().getRid());
				} else {
					return new JsonResult(false, "用户名或密码错误！");
				}
			}
		} else {
			return new JsonResult(false, "用户名不能为空！");
		}

        return new JsonResult(true, ret);
    }
}
