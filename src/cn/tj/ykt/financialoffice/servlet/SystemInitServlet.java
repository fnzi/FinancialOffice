package cn.tj.ykt.financialoffice.servlet;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cn.tj.ykt.financialoffice.fw.dao.NativeDao;
import cn.tj.ykt.financialoffice.fw.entity.Menu;
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
        NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");

        Session session = dao.getHibernateSession();

        Transaction tx = session.beginTransaction();
        try {
            LogUtil.logInfo("--------系统初始化---------");

            ConfigurationContext context = XmlContext.getContext();
            Configurations configurations = context.getConfigurations();

            Map<String, Configuration> configs = configurations.getCfgs();

            // 查询menu的菜单项list
            List<Menu> menus = (List<Menu>) session.createSQLQuery("select * from sys_menu").addEntity(Menu.class).list();

            Set<String> keyIds = new HashSet<String>();
            // 整理获取keyid
            for (Menu m : menus) {
                keyIds.add(m.getKeyid());
            }

            for (String key : configs.keySet()) {
                // 如果存在更新，不存在插入
                if (keyIds.contains(key)) {
                    Menu m = (Menu) session.createCriteria(Menu.class).add(Restrictions.eq("keyid", key)).uniqueResult();
                    String mname = configs.get(key).getName();
                    m.setMname(mname);
                    String mlink = "/doJsp/reportViewService.action?report=" + configs.get(key).getId();
                    m.setMlink(mlink);
                    String mmodule = "REPORT";
                    m.setMmodule(mmodule);
                    String keyid = configs.get(key).getId();
                    m.setKeyid(keyid);
                    String enable = configs.get(key).getEnable();
                    m.setEnable(enable);
                    String order = configs.get(key).getOrder();
                    m.setOrder(order);

                    session.update(m);
                } else {
                    Menu m = new Menu();
                    String mname = configs.get(key).getName();
                    m.setMname(mname);
                    String mlink = "/doJsp/reportViewService.action?report=" + configs.get(key).getId();
                    m.setMlink(mlink);
                    String mmodule = "REPORT";
                    m.setMmodule(mmodule);
                    String keyid = configs.get(key).getId();
                    m.setKeyid(keyid);
                    String enable = configs.get(key).getEnable();
                    m.setEnable(enable);
                    String order = configs.get(key).getOrder();
                    m.setOrder(order);

                    session.save(m);
                }
            }

            tx.commit();
            LogUtil.logInfo("--------系统初始化完成---------");
        } catch (Exception e) {
            tx.rollback();
            LogUtil.logInfo("--------系统初始化异常---------");
            LogUtil.logError("系统初始化异常：" + e.getMessage(), module, e);
        } finally {
            if (tx != null) {
                tx = null;
            }
            if (session != null) {
                session.close();
            }
        }
    }
}
