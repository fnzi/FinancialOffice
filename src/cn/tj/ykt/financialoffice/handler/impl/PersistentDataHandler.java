package cn.tj.ykt.financialoffice.handler.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.minilang.MiniLang;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.fw.util.NumberUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.check.SystemCheck;

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

        // 基础系统字段检查
        SystemCheck.checkSystemColumn(columns);

        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

        int k = messageBroker.getDataLine() + 1;

        // 获取本次数据录入批次
        String batchNo = messageBroker.getBatchNo();

        /** 插入汇总数据 */
        for (List<String> insertData : data) {

            // if (columns.size() != insertData.size() +
            // SystemCheck.lengthSystemColumn()) {
            // throw new SystemException("columns配置错误或数据解析错误");
            // }

            // 验证有效
            if (!isEffective(insertData)) {
                continue;
            }

            // 验证总计
            boolean hasTotal = false;
            for (String test : insertData) {
                if ("总计".equals(test.trim()) || "合计".equals(test.trim())) {
                    hasTotal = true;
                    break;
                }
            }

            String insertDataSql = "insert into " + tableName;
            String fields = "";
            String values = "";
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            int i = 0;

            int len = insertData.size();

            for (String key : columns.keySet()) {
                fields = fields + columns.get(key).getMapping() + ",";
                values = values + ":" + key + ",";

                String defaultValue = columns.get(key).getDefaultValue();

                if (key.equals("create_date")) {
                    params.put(key, DateUtil.current("yyyyMMdd HH:mm:ss"));
                } else if (key.equals("sort_index")) {
                    params.put(key, k);
                } else if (key.equals("batch_no")) {
                    params.put(key, batchNo);
                } else if (key.equals("data_type")) {
                    if (hasTotal) {
                        params.put(key, "20");
                    } else {
                        params.put(key, "10");
                    }
                } else if (defaultValue != null && !"".equals(defaultValue)) {
                    Map<String, Object> context = new HashMap<String, Object>();
                    context.put("excelInData", messageBroker.getExcelInData());

                    MiniLang lang = MiniLang.getInstance();
                    String value = lang.exec(defaultValue, context);

                    params.put(key, value);
                    i++;
                } else {
                    if (i >= len) {
                        params.put(key, "");
                    } else {
                        String p = insertData.get(i);

                        // ***科学记数法处理***
                        if (NumberUtil.isENum(p)) {
                            p = NumberUtil.enumToString(p);
                        }

                        params.put(key, p);
                    }
                    i++;
                }

            }
            // sort_index
            k = k + 1;

            int fieldslen = fields.length();
            fields = fields.substring(0, fieldslen - 1);

            int valueslen = values.length();
            values = values.substring(0, valueslen - 1);

            insertDataSql = insertDataSql + "(" + fields + ") values (" + values + ")";
            dao.executeSql(insertDataSql, params);
        }
        return SUCCESS;
    }

    /** 验证数据是否有效 */
    private boolean isEffective(List<String> datas) {

        int i = 0;
        for (String s : datas) {
            System.out.println(s);
            if (s == null || "".equals(s.trim())) {
                i = i + 1;
            }
        }

        if (datas.size() == i) {
            return false;
        } else {
            return true;
        }
    }

}
