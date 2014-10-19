package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

import cn.tj.ykt.financialoffice.fw.action.ActionSupport;

public class MapParamHelper {

    protected static final String SESSION_KEY = ActionSupport.SESSION_KEY;

    @SuppressWarnings("unchecked")
    protected <P> P getParam(Map<String, Object> param, String key) {
        return (P) param.get(key);
    }
}
