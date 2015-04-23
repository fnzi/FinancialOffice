package cn.tj.ykt.financialoffice.handler.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.fw.util.ExcelUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.Header;
import cn.tj.ykt.financialoffice.system.cfg.check.SystemCheck;

/**
 * <pre>
 * 功能描述：解析获取Excel中data
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class AnalyticalDataHandler extends DefaultHandler {

    public static final String module = AnalyticalDataHandler.class.getName();

    @Override
    public String process(MessageBroker messageBroker) {

        LogUtil.logInfo("解析获取Excel中data");

        String filePath = messageBroker.getTempFilePath();
        if (filePath == null || "".equals(filePath)) {
            throw new SystemException("file Path 必须赋值");
        }

        try {
            List<List<String>> excelInData = ExcelUtil.analyseExcel(filePath);
            // 有效data
            List<List<String>> data = new ArrayList<List<String>>();

            if (excelInData.isEmpty()) {
                throw new SystemException("excel行解析失败");
            }

            messageBroker.setExcelInData(excelInData);

            String tableName = messageBroker.getTableName();
            String insertDataSql = "insert into " + tableName;

            Map<String, Column> columns = messageBroker.getColumns();
            List<String> columnsList = getColumnsList(columns);

            // 获取本次数据录入批次
            String batchNo = messageBroker.getBatchNo();

            GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

            // 判断是否需要插入header
            boolean hasHeader = hasHeader(tableName, dao);

            /** 插入header数据 */
            List<Header> headers = messageBroker.getHeaders();
            for (int i = 0; i < headers.size(); i++) {

                List<String> line = excelInData.get(i);

                String fields = "";
                String values = "";
                Map<String, Object> params = new LinkedHashMap<String, Object>();

                if (headers.get(i).getReadonly().equals("true")) {
                    if (hasHeader) {
                        continue;
                    }

                    // batch_no
                    fields = fields + "batch_no,";
                    values = values + ":batch_no,";
                    params.put("batch_no", "");

                    // data_type
                    fields = fields + "data_type,";
                    values = values + ":data_type,";
                    params.put("data_type", "40");
                } else {

                    // batch_no
                    fields = fields + "batch_no,";
                    values = values + ":batch_no,";
                    params.put("batch_no", batchNo);

                    // data_type
                    fields = fields + "data_type,";
                    values = values + ":data_type,";
                    params.put("data_type", "41");
                }

                for (int j = 0; j < line.size(); j++) {
                    String col = columnsList.get(j);
                    if (SystemCheck.getSystemColumn().contains(col)) {
                        break;
                    }

                    fields = fields + col + ",";
                    values = values + ":" + col + ",";
                    params.put(col, line.get(j));
                }
                // create_date
                fields = fields + "create_date,";
                values = values + ":create_date,";
                params.put("create_date", DateUtil.current("yyyyMMdd hh:mm:ss"));
                // sort_index
                fields = fields + "sort_index,";
                values = values + ":sort_index,";
                params.put("sort_index", headers.get(i).getSort_index());

                int fieldslen = fields.length();
                fields = fields.substring(0, fieldslen - 1);

                int valueslen = values.length();
                values = values.substring(0, valueslen - 1);

                String insertSql = insertDataSql + "(" + fields + ") values (" + values + ")";
                LogUtil.logDebug(insertSql, module);
                dao.executeSql(insertSql, params);

            }

            // 数据开始行
            int start = headers.size();
            messageBroker.setDataLine(start);

            for (int i = start; i < excelInData.size(); i++) {

                List<String> line = excelInData.get(i);
                if (line.isEmpty()) {
                    continue;
                    // throw new SystemException("excel列解析失败");
                }

                List<String> record = new ArrayList<String>();

                for (int j = 0; j < line.size(); j++) {
                    record.add(line.get(j));
                }
                data.add(record);
            }
            messageBroker.setData(data);

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e.getMessage(), e);
        }
    }

    /** 获取list列集合 */
    private List<String> getColumnsList(Map<String, Column> columns) {

        List<String> list = new ArrayList<String>();
        for (String col : columns.keySet()) {
            list.add(col);
        }

        return list;
    }

    /** 验证db中是否已存在header */
    private boolean hasHeader(String tableName, GenericDao dao) {
        String selectSql = "select count(*) from " + tableName;
        BigInteger len = (BigInteger) dao.findBySQL(selectSql);
        return len.compareTo(BigInteger.ZERO) > 0;
    }
}
