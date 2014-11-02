package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.Menu;
import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：登陆首页左侧页面
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Service("mainLeftService")
public class MainLeftService extends JspService {

	public JspResult execute(Map<String, Object> param) {
		Map<String, Object> ret = new HashMap<String, Object>();
//		HttpSession session = getParam(param, SESSION_KEY);
//		User user = (User) session.getAttribute(SESSION_USER);
		User user = getSessionUser();
		Long loadrid = user.getRole().getRid();
		System.out.println("session"+user.getUsername());
		List<Menu> parentmenus = new ArrayList<Menu>();
		List<Menu> childmenus = new ArrayList<Menu>();

		List<Menu> menus = getDao()
				.findListByHQL(
						"select m from Menu m where m.mid in (select distinct me.mid from Menu me inner join me.roles r where r.rid=?)",
						loadrid);
		for (Menu menu : menus) {
			if ("REPORT".equals(menu.getMmodule())) {
				parentmenus.add(menu);
				List<Menu> reportMenus = getDao()
						.findListByHQL(
								"select distinct s2 from Menu s1,Menu s2 where  s2.mmodule = s1.mid and s1.mmodule = ? ",
								"REPORT");
				for (Menu reportMenu : reportMenus) {
					childmenus.add(reportMenu);
				}
			}
			if ("-1".equals(menu.getMmodule())) {
				parentmenus.add(menu);

			}
			if (!"REPORT".equals(menu.getMmodule())
					&& !"-1".equals(menu.getMmodule())) {
				childmenus.add(menu);
			}
		}
		ret.put("childmenus", childmenus);
		ret.put("parentmenus", parentmenus);
		return new JspResult("main/mainLeft", ret);

	}

}
