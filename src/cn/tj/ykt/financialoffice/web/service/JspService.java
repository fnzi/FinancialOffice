package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

/**
 * <pre>
 * view系jsp画面显示服务基类
 * </pre>
 * */
public abstract class JspService extends ViewService<JspResult> {

    @Override
    public abstract JspResult execute(Map<String, Object> param);

}
