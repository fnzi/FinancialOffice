package cn.tj.ykt.financialoffice.fw.dao;

import java.util.Map;

public interface PageDao extends GenericDao {

    /**
     * <pre>
     * <分页处理>
     * @param sql 查询sql
     * @param page 分页描述信息
     * @param params sql查询参数
     * @return 返回的分页信息
     * </pre>
     */
    public Page findPageBySql(String sql, Page page, Map<String, Object> params);

    /**
     * <pre>
     * <分页处理>
     * @param sql 查询sql
     * @param page 分页描述信息
     * @param params sql查询参数
     * @return 返回的分页信息
     * </pre>
     */
    public Page findPageBySql(String sql, Page page, Object... values);
}
