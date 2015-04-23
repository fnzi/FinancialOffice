package cn.tj.ykt.financialoffice.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.schedule.QuartzStart;

/**
 * <pre>
 * 功能描述：定时任务初始化
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class ScheduleInitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected static final String module = "SYSTEM_SCHEDULE_INIT";

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            LogUtil.logInfo("--------系统初始化:定时任务初始化---------");
            QuartzStart quartzStart = (QuartzStart) SpringUtil.getBean("quartzStart");
            quartzStart.start();
            LogUtil.logInfo("--------系统初始化:定时任务初始化完成---------");
        } catch (Exception e) {
            LogUtil.logInfo("--------系统初始化异常---------");
            LogUtil.logError("系统初始化异常：" + e.getMessage(), module, e);
        }
    }
}
