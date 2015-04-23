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
import cn.tj.ykt.financialoffice.system.cfg.check.SystemCheck;

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

        // 基础系统字段检查
        SystemCheck.checkSystemColumn(columns);

        String sql = createTableSql(tableName, colStrs);

        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
        dao.executeSql(sql);

        return SUCCESS;
    }

    private String createTableSql(String tableName, List<String> columns) {
        String sql = "create table if not exists " + tableName + "(";

        for (String col : columns) {
            if (col.equals("sort_index")) {
                sql = sql + col + " int(11) NOT NULL,";
            } else {
                sql = sql + col + " varchar(255) default NULL,";
            }
        }

        int len = sql.length();
        sql = sql.substring(0, len - 1);

        sql = sql + ")";

        return sql;
    }

}
