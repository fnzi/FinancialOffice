package cn.tj.ykt.financialoffice.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;

/**
 * <pre>
 * 功能描述：创建/检查table
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class CreateTableHandler extends DefaultHandler {

    @Override
    public String process(MessageBroker messageBroker) {

        LogUtil.logInfo("创建/检查table");

        String tableName = messageBroker.getTableName();
        Map<String, Column> columns = messageBroker.getColumns();

        if (tableName == null || "".equals(tableName)) {
            throw new SystemException("tableName 必须配置");
        }
        if (columns == null || columns.size() == 0) {
            throw new SystemException("columns 必须配置");
        }

        List<String> colStrs = new ArrayList<String>();
        for (String col : columns.keySet()) {
            colStrs.add(col);
        }

        if (!columns.containsKey("create_date")) {
            throw new RuntimeException("配置文件配置错误，没有发现数据导入时间[create_date]字段");
        }
        if (!columns.containsKey("check_date")) {
            throw new RuntimeException("配置文件配置错误，没有发现审核时间[check_date]字段");
        }

        String sql = createTableSql(tableName, colStrs);

        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
        dao.executeSql(sql);

        return SUCCESS;
    }

    private String createTableSql(String tableName, List<String> columns) {
        String sql = "create table if not exists " + tableName + "(";

        for (String col : columns) {
            sql = sql + col + " varchar(255) default NULL,";
        }

        int len = sql.length();
        sql = sql.substring(0, len - 1);

        sql = sql + ")";

        return sql;
    }

}
