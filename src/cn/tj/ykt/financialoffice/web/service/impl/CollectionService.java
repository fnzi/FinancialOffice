package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：收款处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class CollectionService extends JspService {

    @Override
    public JspResult execute(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return new JspResult("collection", new HashMap<String, Object>());
    }

}
