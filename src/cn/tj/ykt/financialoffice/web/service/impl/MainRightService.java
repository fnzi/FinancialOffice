package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：登陆首页右侧页面
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Service("mainRightService")
public class MainRightService extends JspService{

	public JspResult doExecute(Map<String, Object> param) {
		return new JspResult("main/mainRight",param);
	}

}
