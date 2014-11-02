package cn.tj.ykt.financialoffice.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.system.cfg.Configuration;
import cn.tj.ykt.financialoffice.system.cfg.Configurations;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：系统初始化
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class SystemInitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected static final String module = "SYSTEM_INIT";

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {	
             LogUtil.logInfo("--------系统初始化---------");
            
             ConfigurationContext context = XmlContext.getContext();
             Configurations configurations = context.getConfigurations();
            
             Map<String, Configuration> configs = configurations.getCfgs();
            
             // TODO 临时解决方案（生成sql插入数据）
             GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
             
             // 查询menu的菜单项list
             List<Object[]> menuDate = (List<Object[]>)dao.findListBySQL("select * from sys_menu");
             
             //判断数据库报表菜单和configuration.xml配置报表差异，进行更新数据库
             if(menuDate.size() != 0){ // 数据库menu项非空时，将新增配置文件新增项目插入数据库
                 if(menuDate.size() < configs.size()){
                	 for (String key : configs.keySet()) {
                		 for(Object[] menu : menuDate){ 
                    		if(!configs.get(key).getName().equals(menu[1])){
                              String insertFunctionSql =
                              "insert into sys_menu (mname,mlink,mmodule,keyid,enable) values (?, ?, ?, ?, ?)";
                             
                              String mname = configs.get(key).getName();
                              String mlink = "/doJsp/reportViewService.action?report=" +
                              configs.get(key).getId();
                              String mmodule = "1";
                              String keyid = configs.get(key).getId();
                              String enable = configs.get(key).getEnable();
                             
                              // 插入menu数据
                              dao.executeSql(insertFunctionSql, mname, mlink, mmodule, keyid,
                              enable);
                    		}
                		 }
                	 }
                 }else{
                	 LogUtil.logInfo("--------配置文件无新添加项目，不需更新---------");
                 }; 
             }else{ // 数据库menu项为空时，将全部配置文件新增项目插入数据库
            	 for (String key : configs.keySet()) {
                      String insertFunctionSql =
                      "insert into sys_menu (mname,mlink,mmodule,keyid,enable) values (?, ?, ?, ?, ?)";
                     
                      String mname = configs.get(key).getName();
                      String mlink = "/doJsp/reportViewService.action?report=" +
                      configs.get(key).getId();
                      String mmodule = "1";
                      String keyid = configs.get(key).getId();
                      String enable = configs.get(key).getEnable();
                     
                      // 插入menu数据
                      dao.executeSql(insertFunctionSql, mname, mlink, mmodule, keyid,
                      enable);
            	 }
             }
            
             LogUtil.logInfo("--------系统初始化完成---------");
        } catch (Exception e) {
            LogUtil.logInfo("--------系统初始化异常---------");
            LogUtil.logError("系统初始化异常：" + e.getMessage(), module, e);
        }
    }
}
