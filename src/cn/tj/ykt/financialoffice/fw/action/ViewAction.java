package cn.tj.ykt.financialoffice.fw.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.JsonUtil;
import cn.tj.ykt.financialoffice.web.service.JsonService;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;
import cn.tj.ykt.financialoffice.web.service.impl.TransmitReportService;

/**
 * <pre>
 * 功能描述：通用viewaction处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Controller
public class ViewAction extends ActionSupport {

    private String module = ViewAction.class.getName();

    /**
     * 处理ajax请求
     * */
    @RequestMapping("/doJson/{serviceName}")
    public void doJsonService(@PathVariable String serviceName, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            Map<String, Object> param = getRequstMap();

            JsonService service = (JsonService) SpringUtil.getBean(serviceName);
            service.setSession(getSession());

            Object ret = service.execute(param);
            String retJson = JsonUtil.beanToJson(ret);

            out.print(retJson);
            out.flush();
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), module, e);
        }
    }

    /**
     * 处理jsp请求
     * */
    @RequestMapping("/doJsp/{serviceName}")
    public String doJspService(@PathVariable String serviceName, ModelMap model) {
        try {
            Map<String, Object> param = getRequstMap();

            JspService service = (JspService) SpringUtil.getBean(serviceName);

            service.setSession(getSession());
            JspResult ret = service.execute(param);

            setModelMap(ret.getData(), model);
            return ret.getPath();
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), module, e);
            model.addAttribute("message", "出错了：" + e.getMessage());
            return "404";
        }
    }

    /**
     * 处理报表请求
     * */
    @RequestMapping("/doReport/{reportid}")
    public String transmitReportService(@PathVariable String reportid, ModelMap model) {
        try {

            TransmitReportService service = (TransmitReportService) SpringUtil.getBean("transmitReportService");

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("reportid", reportid);

            service.execute(param);

            model.addAttribute("status", "成功");
            return "transmitReportStatus";
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), module, e);
            model.addAttribute("message", "出错了：" + e.getMessage());
            return "404";
        }
    }
}
