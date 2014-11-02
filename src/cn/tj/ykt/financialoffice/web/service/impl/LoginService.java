package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：登陆权限验证处理
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Service("loginService")
public class LoginService extends JspService {

	@Transactional
	public JspResult execute(Map<String, Object> param) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String uname = getParam(param, "username");
		String pwd = getParam(param, "password");

		if (!"".equals(uname) && uname != null) {
			User user = getDao().findByHQL(
					"select su from User su where username=?", uname);
			String loaduna = user.getUsername();
			String loadpwd = user.getPassword();
			if (pwd.equals(loadpwd) && uname.equals(loaduna)) {
				// HttpSession session = getParam(param, SESSION_KEY);
				setSessionUser(user);
				ret.put("user", user);
				return new JspResult("main", ret);
			} else {
				ret.put("msg", "用户名或密码错误！");
				return new JspResult("login", ret);
			}
		} else {
			ret.put("msg", "用户名不能为空！");
			return new JspResult("login", ret);
		}
	}
}
