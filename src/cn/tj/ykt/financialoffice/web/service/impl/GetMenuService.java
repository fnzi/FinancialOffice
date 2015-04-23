package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.entity.Menu;
import cn.tj.ykt.financialoffice.web.service.ListService;
import cn.tj.ykt.financialoffice.web.service.vo.MenuTree;

/**
 * <pre>
 * 功能描述：登陆首页左侧页面
 * 创建者：许芳
 * 修改者：闫世峰
 * </pre>
 */
@Service("getMenuService")
public class GetMenuService extends ListService<MenuTree> {

    @SuppressWarnings("rawtypes")
	public List<MenuTree> doExecute(Map<String, Object> param) {
		String mid = (String) param.get("node");
		int midnum=Integer.parseInt(mid);
		String userrid = (String) param.get("userrid");
		GenericDao dao = getDao();
		List<MenuTree> menuTrees = new ArrayList<MenuTree>();
		List rmdata =  dao.findListBySQL(getSql(),
				userrid);
		List<Map<String, Object>> rmdatas = new ArrayList<Map<String, Object>>();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		int num = 0;
        for (Object oInDb : rmdata) {
            Object[] os = (Object[]) oInDb;
            Map<String, Object> item = new HashMap<String, Object>(); 
            item.put("rmid", os[0]);
            item.put("mmodule", os[1]);
            rmdatas.add(item);
        }
        
//		if (mid.equals("-100")) {
//			MenuTree mt1 = new MenuTree(1, "日报同步", false, "");
//			menuTrees.add(mt1);
//		} else if (mid.equals("1")) {
//			List<Menu> data = dao.findListByHQL(getReportSql());
//			int rr = 11;
//			for (int i = 0; i < data.size(); i++) {
//				MenuTree mt11 = new MenuTree(rr++, data.get(i).getMname(),
//						true, data.get(i).getMlink());
//				menuTrees.add(mt11);
//			}
//		}
		for (int rn = 0; rn < rmdata.size(); rn++) {
			
			queryParam.put("mid", rmdatas.get(rn).get("rmid"));
			queryParam.put("mmodule", -1);
			if (mid.equals("-100")) {
				
				if("R".equals(rmdatas.get(rn).get("mmodule"))){
					MenuTree mt1 = new MenuTree(1, "日报同步", false, "");
					menuTrees.add(mt1);
				}
				// 获得权限下可显示主菜单
				List<Menu> menuParent = dao.findListByHQL(getParentCSql(),
						queryParam);
				if (menuParent.size() <= 0) {
					continue;
				}
				for (int i = 0; i < menuParent.size(); i++) {
					num = (Integer.parseInt(menuParent.get(i).getMid()
							.toString()));
					MenuTree mt2 = new MenuTree(num, menuParent.get(i)
							.getMname(), false, "");
					menuTrees.add(mt2);
				}
        }
        else if(!mid.equals("-100")){
        	if("R".equals(rmdatas.get(rn).get("mmodule"))&&"1".equals(mid)){
        		List<Menu> data = dao.findListByHQL(getReportSql(),rmdatas.get(rn).get("rmid").toString());
    			for (int i = 0; i < data.size(); i++) {
    				MenuTree mt11 = new MenuTree(Integer.parseInt(String.valueOf(data.get(i).getMid())), data.get(i).getMname(),
    						true, data.get(i).getMlink());
    				menuTrees.add(mt11);
    			}
			}
    		queryParam.put("mid", rmdatas.get(rn).get("rmid"));
    		queryParam.put("mmodule",mid);
        	List<Menu> data =  dao.findListByHQL(getParentCSql(),queryParam);
			for (int i = 0; i < data.size(); i++) {
				MenuTree mt21 = new MenuTree(Integer.parseInt(String.valueOf(data.get(i).getMid())), data.get(i).getMname(),
						true, data.get(i).getMlink());
				menuTrees.add(mt21);
			}
        	 
        }
    }
        return menuTrees;
    }
    private String getSql() {
        return "select srm.rmid rmid,sme.mmodule mmodule from sys_refrolemenu srm,sys_menu sme where srm.rmid = sme.mid and srm.rrid = ? order by sme.mid";
    }
    private String getParentCSql() {
        return "from Menu where mmodule = :mmodule and mid = :mid order by mid";
    }
    private String getReportSql() {
        return "from Menu where mmodule in ('REPORT',?) order by mid";
    }
}
