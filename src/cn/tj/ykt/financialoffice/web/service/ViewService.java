package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

import javax.annotation.Resource;

import cn.tj.ykt.financialoffice.fw.dao.PageDao;
import cn.tj.ykt.financialoffice.fw.service.WebService;

/**
 * <pre>
 * view系画面显示服务基类
 * </pre>
 * */
public abstract class ViewService<R> extends MapParamHelper implements WebService<Map<String, Object>, R> {

    protected String module = ViewService.class.getName();

    @Resource(name = "pageDao")
    private PageDao dao;

    protected PageDao getDao() {
        return dao;
    }

    @Override
    public abstract R execute(Map<String, Object> param);

}
