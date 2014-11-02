package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import cn.tj.ykt.financialoffice.fw.action.ActionSupport;
import cn.tj.ykt.financialoffice.fw.dao.PageDao;
import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.service.WebService;

/**
 * <pre>
 * 功能描述：view系画面显示服务基类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public abstract class ViewService<R> extends MapParamHelper implements WebService<Map<String, Object>, R> {

    protected String module = ViewService.class.getName();

    protected static final String SESSION_KEY = ActionSupport.SESSION_KEY;
    /** session中的user */
    protected static final String SESSION_USER = ActionSupport.SESSION_USER;

    private ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();

    /** 设置session */
    public void setSession(HttpSession currentSession) {
        session.set(currentSession);
    }

    /** 获取session */
    private HttpSession getSession() {
        HttpSession currentSession = session.get();
        if (currentSession == null) {
            throw new SystemException("session is null");
        }
        return currentSession;
    }

    @Resource(name = "pageDao")
    private PageDao dao;

    protected PageDao getDao() {
        return dao;
    }

    @Override
    public abstract R execute(Map<String, Object> param);

    /**
     * 获取session user
     */
    protected User getSessionUser() {
        User sessionUser = (User) getSession().getAttribute(SESSION_USER);
        return sessionUser;
    }

    /**
     * 设置session user
     */
    protected void setSessionUser(User sessionUser) {
        getSession().setAttribute(SESSION_USER, sessionUser);
    }

    /**
     * 获取session value
     */
    protected Object getSession(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 设置session value
     */
    protected void putSession(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * CRUD关键字String转int
     */
    public int StrInt(String str) {
        int meth = 0;

        if ("list".equals(str))
            meth = 1;
        if ("add".equals(str))
            meth = 2;
        if ("edit".equals(str))
            meth = 3;
        if ("delete".equals(str))
            meth = 4;

        return meth;
    }
}
