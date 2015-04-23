package cn.tj.ykt.financialoffice.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;

/**
 * <pre>
 * 功能描述：系统初始化时验证xml配置文件
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class CheckXmlConfigServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected static final String module = "SYSTEM_INIT_CHECK";

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            LogUtil.logInfo("--------系统初始化:验证xml配置文件---------");
            // TODO
            LogUtil.logInfo("--------系统初始化:验证xml配置文件完成---------");
        } catch (Exception e) {
            LogUtil.logInfo("--------系统初始化异常---------");
            LogUtil.logError("系统初始化异常：" + e.getMessage(), module, e);
        }
    }
}
