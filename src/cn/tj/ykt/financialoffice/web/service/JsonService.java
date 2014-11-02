package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

/**
 * <pre>
 * 功能描述：view系ajax(Json)画面显示服务基类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public abstract class JsonService extends ViewService<JsonResult> {

    @Override
    public abstract JsonResult execute(Map<String, Object> param);

}