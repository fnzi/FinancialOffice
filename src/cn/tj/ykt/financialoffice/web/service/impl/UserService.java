package cn.tj.ykt.financialoffice.web.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.Role;
import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.JsonExtService;
import cn.tj.ykt.financialoffice.web.service.JsonResult;

/**
 * <pre>
 * 功能描述：用户管理功能(List,CRUD操作)
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Transactional
@Service("userService")
public class UserService extends JsonExtService {
	
	@Override
	public Object doExecute(Map<String, Object> param) {
		Long uidl = null;
		Long ridl = null;
		List<User> userlist;
		BigInteger userCount;
		 int start = Integer.parseInt((String) param.get("start"));
         int limit = Integer.parseInt((String) param.get("limit"));
         GridList<User> useli = new GridList<User>();
		
		int meth =  Integer.parseInt((String) getParam(param, "meth"));
		String uid = getParam(param, "uid");
		String username = getParam(param, "username");
		String password = getParam(param, "password");
		String realname = getParam(param, "realname");
		String rid = getParam(param, "rid");
		if (uid != null &&!"".equals(uid)) {
			uidl = Long.parseLong(uid);
		}
		if (rid != null &&!"".equals(rid)) {
			ridl = Long.parseLong(rid);
		}
		switch (meth) {
		case 1:
			if("".equals(username)||username==null){
				userlist = getDao().findPageByHql("select u from User u ", start, limit);
				userCount = (BigInteger) getDao().findBySQL("select count(*) from sys_user");
			}else{
				userlist = getDao().findPageByHql("select u from User u where u.username like ?", start, limit, "%"+username+"%");
				userCount = (BigInteger) getDao().findBySQL("select count(*) from sys_user u where u.username like ?","%"+username+"%");
			}
			for (int i = 0; i < userlist.size(); i++) {
				userlist.get(i).setUrid(userlist.get(i).getRole().getRid());
			}
			int userCountInt = userCount.intValue();
			useli.setData(userlist);
			useli.setTotal(userCountInt);
//			JSONArray jsonArray = JSONArray.fromObject(userlist);
//			JSONObject json = new JSONObject();
//			json.put("total", userlist.size());
//			json.put("data", jsonArray.toString());
			return  useli;
		case 2:
			Role rlist = getDao().load(ridl, Role.class);
			if (rlist != null) {
				User usera = new User();
				User aluser = getDao().findByHQL("select u from User u where u.username=?", username);
				if(aluser!=null){
						return new JsonResult(false, "用户名重复！");
				}
				usera.setPassword(password);
				usera.setRealname(realname);
				usera.setRole(rlist);
				usera.setUsername(username);
				getDao().saveOrUpdate(usera);
			}
			break;
		case 3:
			Role edrlist = getDao().load(ridl, Role.class);
			if (edrlist != null) {
				User aluser = getDao().findByHQL("select u from User  u where u.username=?", username);
				if(aluser!=null){
					if(!uidl.equals(aluser.getUid())){
					return new JsonResult(false, "用户名重复！");
				   }
				}
				User usera = getDao().load(uidl, User.class);
				usera.setPassword(password);
				usera.setRealname(realname);
				usera.setRole(edrlist);
				usera.setUsername(username);
				usera.setUid(uidl);
				getDao().update(usera);
			}
			break;
		case 4:
				User user = getDao().load(uidl, User.class);
				getDao().delete(user);
			break;
		default:
			return new JsonResult(false, "数据错误！");
		}
		return new JsonResult(true, "保存成功！");

	}

}
