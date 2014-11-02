package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.Page;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：分页演示服务类(临时)
 * 增加session演示例子
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("pageTestService")
public class PageTestService extends JspService {

    @Override
    public JspResult execute(Map<String, Object> param) {

        Integer currentPage = getPageNum(param);

        Page page = new Page(currentPage);

        Page pager = getDao().findPageBySql("select * from sss", page);

        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("pager", pager);

        HttpSession session = getParam(param, SESSION_KEY);
        session.setAttribute("USER", "user fnzi 123");

        return new JspResult("pageTest", ret);
    }

}
