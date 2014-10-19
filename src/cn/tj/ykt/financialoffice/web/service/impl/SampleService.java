package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

@Service("sampleService")
public class SampleService extends JspService {

    @Override
    public JspResult execute(Map<String, Object> param) {
        String pwd = getParam(param, "password");

        System.out.println(getParam(param, "username"));
        System.out.println(pwd);

        HttpSession session = getParam(param, SESSION_KEY);
        assert session != null;
        
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("msg", "123");

        return new JspResult("sample", ret);
    }

}
