package cn.tj.ykt.financialoffice.fw.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * <pre>
 * 功能描述：Action辅助处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class ActionSupport {
    public static final String SESSION_KEY = "__session_key__";
    public static final String SESSION_USER = "__session_key_user__";

    public void setModelMap(Map<String, Object> result, ModelMap model) {
        for (String key : result.keySet()) {
            model.addAttribute(key, result.get(key));
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getRequstMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> _enum = this.getRequest().getParameterNames();
        while (_enum.hasMoreElements()) {
            String paramName = (String) _enum.nextElement();
            String paramValue = this.getRequest().getParameter(paramName);

            // 形成键值对应的map
            map.put(paramName, paramValue);
        }
        map.put(SESSION_KEY, getSession());
        return map;
    }

    /**
     * <pre>
     * 获取session
     * @return 返回session
     * </pre>
     */
    public HttpSession getSession() {

        return getRequest().getSession();
    }

    /**
     * <pre>
     * 向session中添加值
     * @param key 主键
     * @param value 值
     * </pre>
     */
    public void setSession(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }

    /**
     * <pre>
     * 在session中获取值
     * @param key 主键
     * @return 返回值
     * </pre>
     */
    public Object getSessionValue(String key) {
        return getRequest().getSession().getAttribute(key);
    }

    /**
     * <pre>
     * 获取request
     * @return 返回request
     * </pre>
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
    
	/**
	 * <pre>
	 * 获取HttpServletResponse
	 * @return 返回response
	 * </pre>
	 */
	public static HttpServletResponse getResponse() {
		ServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		HttpServletResponse response1 = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		return response1;
	}

    /**
     * <pre>
     * 获取根目录
     * @return 返回根目录
     * </pre>
     */
    public String getRealPath() {
        return getSession().getServletContext().getRealPath("/");
    }

}