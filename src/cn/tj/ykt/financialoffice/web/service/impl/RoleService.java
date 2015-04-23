package cn.tj.ykt.financialoffice.web.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.Role;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.JsonExtService;
import cn.tj.ykt.financialoffice.web.service.JsonResult;

/**
 * <pre>
 * 功能描述：角色管理功能(List,CRUD操作)
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Transactional
@Service("roleService")
public class RoleService extends JsonExtService {
	
	public Object doExecute(Map<String, Object> param) {
		 Long ridl = null;
		 List<Role> rolelist;
		 BigInteger roleCount;
		 int start = Integer.parseInt((String) param.get("start"));
         int limit = Integer.parseInt((String) param.get("limit"));
         GridList<Role> rseli = new GridList<Role>();
		
		int meth = Integer.parseInt((String)getParam(param, "meth"));
		String rid = getParam(param, "rid");
		String rname = getParam(param, "rname");
		if (rid != null && !"".equals(rid)) {
			ridl = Long.parseLong(rid);
		}
		
		switch (meth) {
		case 1:
			if("".equals(rname)||rname==null){
//			 rolelist = getDao().findPageByHql("select r from Role r where r.rname=(CASE WHEN ? IS NULL THEN r.rname ELSE ? END)", start, limit);
			 rolelist = getDao().findPageByHql("select r from Role r ", start, limit);
			 roleCount = (BigInteger) getDao().findBySQL("select count(*) from sys_role");
			}else {
			 rolelist = getDao().findPageByHql("select r from Role r where r.rname like ?", start, limit, "%"+rname+"%");
			 roleCount = (BigInteger) getDao().findBySQL("select count(*) from sys_role r where r.rname like ?","%"+rname+"%");
			}
			int roleCountInt = roleCount.intValue();
			rseli.setData(rolelist);
			rseli.setTotal(roleCountInt);
			return  rseli;
		case 2:
			Role role = new Role();
			Role alrole = getDao().findByHQL("select r from Role r where r.rname=?", rname);
			if(alrole!=null){
					return new JsonResult(false, "角色名称重复！");
			}
			role.setRname(rname);
			getDao().saveOrUpdate(role);
			break;
		case 3:
			
			Role ealrole = getDao().findByHQL("select r from Role r where r.rname=?", rname);
			if(ealrole!=null){
				if(!ridl.equals(ealrole.getRid())){
					return new JsonResult(false, "角色名称重复！");
				}
			}
			Role erole = getDao().load(ridl, Role.class);
			erole.setRname(rname);
			erole.setRid(ridl);
			getDao().update(erole);
			break;
		case 4:
			if (ridl != null) {
				Role lrole = getDao().load(ridl, Role.class);
				getDao().delete(lrole);
			}
			break;
		default:
			return new JsonResult(false, "数据错误！");
		}
		return new JsonResult(true, "保存成功！");
	}

}
