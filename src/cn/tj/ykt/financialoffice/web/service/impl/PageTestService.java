package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.Page;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

@Service("pageTestService")
public class PageTestService extends JspService {

    @Override
    public JspResult execute(Map<String, Object> param) {

        String currentPage = getParam(param, "page.currentPage");
        if (currentPage == null) {
            currentPage = "1";
        }

        Page page = new Page(Integer.parseInt(currentPage));

        Page pager = getDao().findPageBySql("select * from sss", page);

        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("pager", pager);

        return new JspResult("pageTest", ret);
    }

}
