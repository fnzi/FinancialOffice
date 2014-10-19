package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

/**
 * <pre>
 * view系ajax画面显示服务基类
 * </pre>
 * */
public abstract class JsonService extends ViewService<JsonResult> {

    @Override
    public abstract JsonResult execute(Map<String, Object> param);

}