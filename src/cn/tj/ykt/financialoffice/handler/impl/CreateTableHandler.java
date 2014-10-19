package cn.tj.ykt.financialoffice.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：创建/检查table
 * </pre>
 */
public class CreateTableHandler extends DefaultHandler {

    @Override
    public String process(MessageBroker messageBroker) {
        ConfigurationContext contxt = XmlContext.getContext();

        String reportId = messageBroker.getReportId();

        if (reportId == null || "".equals(reportId)) {
            throw new SystemException("report id 必须赋值");
        }

        CreateTabler createTabler = contxt.getCreateTabler(reportId);

        String tableName = createTabler.getTableName();
        Map<String, Column> columns = createTabler.getColumns();

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

        String sql = createTableSql(tableName, colStrs);

        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
        dao.executeSql(sql);

        return SUCCESS;
    }

    private String createTableSql(String tableName, List<String> columns) {
        String sql = "create table if not exists " + tableName + "(";

        for (String col : columns) {
            sql = sql + col + " varchar(255) default NULL, ";
        }

        int len = sql.length();
        sql = sql.substring(0, len - 1);

        sql = sql + ")";

        return sql;
    }

}
