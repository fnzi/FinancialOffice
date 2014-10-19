package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

@Service("reportViewService")
public class ReportViewService extends JspService {

    @Override
    public JspResult execute(Map<String, Object> param) {

        return new JspResult("reportView", new HashMap<String, Object>());
    }

}
