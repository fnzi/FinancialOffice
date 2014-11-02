package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.Role;
import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：用户管理功能(List,CRUD操作)
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Service("userService")
public class UserService extends JspService {
	
	public JspResult execute(Map<String, Object> param) {
		Long uidl = null;
		Long ridl = null;

		Map<String, Object> ret = new HashMap<String, Object>();
		String meth = getParam(param, "meth");
		String uid = getParam(param, "uid");

		String username = getParam(param, "username");
		String password = getParam(param, "password");
		String realname = getParam(param, "realname");
		String rid = getParam(param, "rid");

		if (uid != null) {
			uidl = Long.parseLong(uid);
		}
		if (rid != null) {
			ridl = Long.parseLong(rid);
		}

		List<Role> role = getDao().findListByHQL("select r from Role r");
		ret.put("role", role);

		switch (StrInt(meth)) {
		case 1:
			break;
		case 2:
			
			Role rlist = getDao().load(ridl, Role.class);
			String auid = getParam(param, "auid");
			if (rlist != null) {
				User usera = null;
				if (auid != null && !"".equals(auid)) {
					Long auidl = Long.parseLong(auid);
					usera = getDao().load(auidl, User.class);
				} else {
					usera = new User();
				}
				User aluser = getDao().findByHQL("select u from User where u.username=?", username);
				if(aluser!=null){
					ret.put("msg", "用户名重复！");
					return new JspResult("systemManage/userList", ret);
				}
				usera.setPassword(password);
				usera.setRealname(realname);
				usera.setRole(rlist);
				usera.setUsername(username);
				getDao().saveOrUpdate(usera);
				
			}
			break;
		case 3:
			User usere = new User();

			if (uidl != null) {
				User luser = getDao().load(uidl, User.class);
				usere.setUid(luser.getUid());
				usere.setPassword(luser.getPassword());
				usere.setRealname(luser.getRealname());
				usere.setRole(luser.getRole());
				usere.setUsername(luser.getUsername());

				ret.put("usere", usere);
			}
			break;
		case 4:
			if (uidl != null) {
				User luser = getDao().load(uidl, User.class);
				getDao().delete(luser);
			}
			break;
		default:
			ret.put("msg", "数据错误！");
			return new JspResult("main/mainRight", ret);
		}
		List<User> user = getDao().findListByHQL("select su from User su");
		ret.put("user", user);
		return new JspResult("systemManage/userList", ret);

	}

}
