package cn.tj.ykt.financialoffice.handler.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;

/**
 * <pre>
 * 功能描述：持久化data到DB
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class PersistentDataHandler extends DefaultHandler {

    @Override
    public String process(MessageBroker messageBroker) {

        try {
            LogUtil.logInfo("持久化data到DB");

            List<List<String>> data = messageBroker.getData();

            if (data == null) {
                throw new SystemException("data 解析失败");
            }

            String tableName = messageBroker.getTableName();
            Map<String, Column> columns = messageBroker.getColumns();

            if (tableName == null || "".equals(tableName)) {
                throw new SystemException("tableName 必须配置");
            }
            if (columns == null || columns.size() == 0) {
                throw new SystemException("columns 必须配置");
            }

            if (!columns.containsKey("create_date")) {
                throw new SystemException("配置文件配置错误，没有发现数据导入时间[create_date]字段");
            }
            if (!columns.containsKey("check_date")) {
                throw new SystemException("配置文件配置错误，没有发现审核时间[check_date]字段");
            }

            GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

            for (List<String> insertData : data) {

                if (columns.size() != insertData.size() + 2) {
                    throw new SystemException("columns配置错误或数据解析错误");
                }

                String insertDataSql = "insert into " + tableName;
                String fields = "";
                String values = "";
                Map<String, Object> params = new LinkedHashMap<String, Object>();
                int i = 0;

                for (String key : columns.keySet()) {
                    fields = fields + columns.get(key).getMapping() + ",";
                    values = values + ":" + key + ",";

                    if (key.equals("create_date")) {
                        params.put(key, DateUtil.current("yyyyMMdd hh:mm:ss"));
                    } else if (key.equals("check_date")) {
                        params.put(key, "");
                    } else {
                        params.put(key, insertData.get(i));
                        i++;
                    }
                }

                int fieldslen = fields.length();
                fields = fields.substring(0, fieldslen - 1);

                int valueslen = values.length();
                values = values.substring(0, valueslen - 1);

                insertDataSql = insertDataSql + "(" + fields + ") values (" + values + ")";

                dao.executeSql(insertDataSql, params);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e.getMessage(), e);
        }
        return SUCCESS;
    }
}
