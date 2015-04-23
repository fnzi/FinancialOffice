package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.Menu;
import cn.tj.ykt.financialoffice.fw.entity.Role;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.JsonExtService;
import cn.tj.ykt.financialoffice.web.service.JsonResult;

/**
 * <pre>
 * 功能描述：角色权限管理功能(List,save操作)
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Transactional
@Service("roleAuthorityService")
public class RoleAuthorityService extends JsonExtService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object doExecute(Map<String, Object> param) {
		Long ridl = null;
		Map<String, Object> queryParam = new HashMap<String, Object>();
		GridList<String> menuli = new GridList<String>();
		int meth = Integer.parseInt((String) getParam(param, "meth"));
		String rid = getParam(param, "rid");
		if (rid != null && !"".equals(rid)) {
			ridl = Long.parseLong(rid);
		}
		switch (meth) {
		case 1:
			List<Menu> menuplist = getDao().findListByHQL(
					"select m from Menu m where m.mmodule in (-1,'R')");
			List menuTree = new ArrayList();
			for (int i = 0; i < menuplist.size(); i++) {
				Map<String, Object> folderJson = new HashMap<String, Object>();
				folderJson.put("id", menuplist.get(i).getMid());
				folderJson.put("text", menuplist.get(i).getMname());
				folderJson.put("leaf", false);
				folderJson.put("checked", false);

				queryParam.put("mmodule", menuplist.get(i).getMid());
				List<Menu> menuclist = getDao().findListByHQL(getParentCSql(),
						queryParam);
				List children = new ArrayList();
				for (int j = 0; j < menuclist.size(); j++) {
					Map<String, Object> menuJson = new HashMap<String, Object>();
					menuJson.put("id", menuclist.get(j).getMid());
					menuJson.put("text", menuclist.get(j).getMname());
					menuJson.put("leaf", true);
					menuJson.put("checked", false);
					children.add(menuJson);
				}
				folderJson.put("children", children);
				menuTree.add(folderJson);
			}
			return menuTree;
		case 2:
			List<String> rmdata = (List<String>) getDao().findListBySQL(
					getSql(), rid);
			menuli.setData(rmdata);
			menuli.setTotal(rmdata.size());
			return menuli;
		case 3:
			String emids = getParam(param, "mid");
			Role erole = getDao().load(ridl, Role.class);
			if (erole == null) {
				return new JsonResult(false, "保存失败,该角色已经被删除,请刷新页面重新加载数据");
			}
			getDao().executeSql(getEditSql(), rid);
			// erole.setRid(Long.parseLong(rid));
			if (emids != null && !emids.trim().equals("")) {
				String[] emid = emids.split(",");
				for (int i = 0; i < emid.length; i++) {
					Menu menu = getDao().load(Long.parseLong(emid[i]),
							Menu.class);
					menu.getRoles().add(erole); // 删除节目中对角色的引用
					erole.getMenus().remove(menu);
				}
				getDao().save(erole);
			}
			break;
		default:
			return new JsonResult(false, "数据错误！");
		}
		return new JsonResult(true, "保存成功！");
	}

	private String getParentCSql() {
		return "from Menu where mmodule = :mmodule ";
	}

	private String getSql() {
		return "select sme.mid mid from sys_refrolemenu srm,sys_menu sme where srm.rmid = sme.mid and srm.rrid = ?";
	}

	private String getEditSql() {
		return "delete  from sys_refrolemenu where rrid = ?";
	}
}
