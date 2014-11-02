package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：登陆首页上部页面
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Service("mainTopService")
public class MainTopService extends JspService{

	public JspResult execute(Map<String, Object> param) {
		return new JspResult("main/mainTop",param);
	}

}
