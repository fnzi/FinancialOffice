package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.Role;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：角色管理功能(List,CRUD操作)
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Service("roleService")
public class RoleService extends JspService {
	
	public JspResult execute(Map<String, Object> param) {
		Long ridl = null;
		Map<String, Object> ret = new HashMap<String, Object>();
		String meth = getParam(param, "meth");
		String rid = getParam(param, "rid");
		String arid = getParam(param, "arid");
		String rname = getParam(param, "rname");
		if (rid != null) {
			ridl = Long.parseLong(rid);
		}
		switch (StrInt(meth)) {
		case 1:
			break;
		case 2:
			Role rolea = null;
			if (arid != null && !"".equals(arid)) {
				Long aridl = Long.parseLong(arid);
				rolea = getDao().load(aridl, Role.class);
			} else {
				rolea = new Role();
			}
			rolea.setRname(rname);
			getDao().saveOrUpdate(rolea);
			break;
		case 3:
			Role rolee = new Role();

			if (ridl != null) {
				Role lrole = getDao().load(ridl, Role.class);
				rolee.setRid(lrole.getRid());
				rolee.setRname(lrole.getRname());

				ret.put("rolee", rolee);
			}
			break;
		case 4:
			if (ridl != null) {
//				try{
				Role lrole = getDao().load(ridl, Role.class);
				getDao().delete(lrole);
//				} catch (RuntimeException e) { 
//					String msg = e.getMessage();
//					ret.put("msg", "数据错误！");
//				}catch (Exception e) {
//		            String msg = e.getMessage();
//		            ret.put("msg", "数据错误！");
//		        }
			}
			break;
		default:
			ret.put("msg", "数据错误！");
			return new JspResult("main/mainRight", ret);
		}
		List<Role> role = getDao().findListByHQL("select r from Role r");
		ret.put("role", role);
		return new JspResult("systemManage/roleList", ret);
	}

}
