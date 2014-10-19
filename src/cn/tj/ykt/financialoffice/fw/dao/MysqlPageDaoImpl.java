package cn.tj.ykt.financialoffice.fw.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class MysqlPageDaoImpl extends GenericDaoImpl implements PageDao {

    @Override
    public Page findPageBySql(String sql, Page page, Map<String, Object> params) {

        // 获取总记录数
        String countSql = "select count(0) from (" + sql + ") as tmp_count"; // 记录统计
        BigInteger count = (BigInteger) findBySQL(countSql, params);

        page.setTotalResult(count.intValue());
        // 获取当页数据
        StringBuffer pageSql = new StringBuffer();
        pageSql.append(sql);
        pageSql.append(" limit " + page.getCurrentResult() + "," + page.getShowCount());

        String querySql = pageSql.toString();

        List<?> data = findListBySQL(querySql, params);
        page.setData(data);

        return page;
    }

    @Override
    public Page findPageBySql(String sql, Page page, Object... values) {
        // 获取总记录数
        String countSql = "select count(0) from (" + sql + ") as tmp_count"; // 记录统计
        BigInteger count = (BigInteger) findBySQL(countSql, values);

        page.setTotalResult(count.intValue());
        // 获取当页数据
        StringBuffer pageSql = new StringBuffer();
        pageSql.append(sql);
        pageSql.append(" limit " + page.getCurrentResult() + "," + page.getShowCount());

        String querySql = pageSql.toString();

        List<?> data = findListBySQL(querySql, values);
        page.setData(data);

        return page;
    }
}
