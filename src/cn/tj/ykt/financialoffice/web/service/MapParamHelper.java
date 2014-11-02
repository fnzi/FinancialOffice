package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

/**
 * <pre>
 * 功能描述：view系画面服务辅助类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class MapParamHelper {

    protected static final String CURRENTPAGE = "page.currentPage";

    /**
     * <pre>
     * 获取请求参数
     * @param param
     * @param key
     * @return
     * </pre>
     */
    @SuppressWarnings("unchecked")
    protected <P> P getParam(Map<String, Object> param, String key) {
        return (P) param.get(key);
    }

    /**
     * <pre>
     * 获取当前请求页号
     * @param param
     * @param key
     * @return
     * </pre>
     */
    protected Integer getPageNum(Map<String, Object> param) {
        String currentPage = getParam(param, CURRENTPAGE);
        if (currentPage == null) {
            currentPage = "1";
        }
        return Integer.parseInt(currentPage);
    }
}
