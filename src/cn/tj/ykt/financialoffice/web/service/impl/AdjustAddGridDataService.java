package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.AdjustReport;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：调整添加页面
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("adjustAddGridDataService")
public class AdjustAddGridDataService extends JsonService {

    private String module = AdjustAddGridDataService.class.getName();

    @Override
    public JsonResult doExecute(Map<String, Object> param) {

        String reportId = (String) param.get("reportId");
        if (reportId == null || "".equals(reportId)) {
            LogUtil.logError("reportId必须输入");
            return new JsonResult(false, "reportId必须输入");
        }
        int headSize = ConfigUtil.headerSize(reportId.toString());
		int sort = headSize + 1;
		
        ConfigurationContext context = XmlContext.getContext();
        CreateTabler ct = context.getCreateTabler(reportId);
        String tableName = ct.getTableName();
        Map<String, Column> cols = ct.getColumns();

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String batchNo = getBatchNo();

        for (String col : cols.keySet()) {
            if (col.equals("create_date")) {
                params.put(col, DateUtil.current("yyyyMMdd hh:mm:ss"));
            } else if (col.equals("sort_index")) {
                // 调整数据不录入sort_index
                params.put(col, sort);
            } else if (col.equals("batch_no")) {
                // 调整数据batch_no 每次添加的都新生成batch_no作为识别条件
                params.put(col, batchNo);
            } else if (col.equals("data_type")) {
                // 调整数据data_type -> 30
                params.put(col, "30");
            } else {
                Object p = param.get(col);
                if (p == null) {
                    params.put(col, "");
                } else {
                    params.put(col, p);
                }
            }
        }

      String sql = getSql(tableName, cols);
      Long  adjustid= Long.parseLong((String) param.get("adjustId"));
      String  reportbatchno= (String) params.get("batch_no");
      AdjustReport ar=new AdjustReport();
      ar.setAdjustId(adjustid);
      ar.setReportBatchNo(reportbatchno);
        try {
        	getDao().saveOrUpdate(ar);
            
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), module, e);
            return new JsonResult(false, "调整添加失败");
        }
        try {
            getDao().executeSql(sql, params);
            
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), module, e);
            return new JsonResult(false, "调整添加失败");
        }
        return new JsonResult(true, "调整添加成功");
    }

    /** 获取数据导入批次 */
    private String getBatchNo() {
        return String.valueOf(System.currentTimeMillis());
    }

    private String getSql(String tableName, Map<String, Column> columns) {

        String insertDataSql = "insert into " + tableName;
        String fields = "";
        String values = "";

        for (String key : columns.keySet()) {
            fields = fields + columns.get(key).getMapping() + ",";
            values = values + ":" + key + ",";
        }

        int fieldslen = fields.length();
        fields = fields.substring(0, fieldslen - 1);

        int valueslen = values.length();
        values = values.substring(0, valueslen - 1);

        insertDataSql = insertDataSql + "(" + fields + ") values (" + values + ")";

        return insertDataSql;
    }
}
