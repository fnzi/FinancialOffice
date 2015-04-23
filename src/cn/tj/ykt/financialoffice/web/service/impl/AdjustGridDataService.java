package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.Page;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.GridListService;

/**
 * <pre>
 * 功能描述：调整数据获取页面
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("adjustGridDataService")
public class AdjustGridDataService extends GridListService<Map<String, Object>> {

    @Override
    public GridList<Map<String, Object>> doExecute(Map<String, Object> param) {
        GridList<Map<String, Object>> trss = new GridList<Map<String, Object>>();
        String reportId = (String) param.get("reportId");

        if (reportId == null || "".equals(reportId)) {
            LogUtil.logError("reportId必须输入");
            return trss;
        }

        String data_type = (String) param.get("data_type");

        if (data_type == null || "".equals(data_type)) {
            LogUtil.logError("data_type必须输入");
            return trss;
        }

        String status = (String) param.get("status");

        if (status == null || "".equals(status)) {
            LogUtil.logError("status必须输入");
            return trss;
        }

        String adjustId = (String) param.get("adjustId");

        if (adjustId == null || "".equals(adjustId)) {
            LogUtil.logError("adjustId必须输入");
            return trss;
        }

        String start = (String) param.get("start");
        String limit = (String) param.get("limit");

        ConfigurationContext context = XmlContext.getContext();
        CreateTabler ct = context.getCreateTabler(reportId);
        String tableName = ct.getTableName();
        Map<String, Column> cols = ct.getColumns();
        List<String> colsList = getColumns(cols);

        String sql = getGridDataSql(tableName, colsList);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data_type", data_type);
        params.put("status", status);
        params.put("adjustId", adjustId);

        Page page = new Page(Integer.parseInt(start));
        page.setShowCount(Integer.parseInt(limit));
        page = getDao().findPageBySql(sql, page, params);

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        for (Object oInDb : page.getData()) {
            Object[] os = (Object[]) oInDb;

            if (os.length != colsList.size()) {
                throw new SystemException("调整数据检索异常");
            }

            Map<String, Object> item = new HashMap<String, Object>();
            for (int i = 0; i < colsList.size(); i++) {
                item.put(colsList.get(i), os[i]);
            }
            data.add(item);
        }

        trss.setData(data);
        trss.setTotal(data.size());
        return trss;
    }

    private String getGridDataSql(String tableName, List<String> cols) {
        String column = StringUtil.list2String(cols);

        String sql = "select "
                + column
                + " from "
                + tableName
                + " report, sys_adjust sa, ref_adjust_report rar WHERE rar.adjustid = sa.adjustId AND rar.reportbatchno = report.batch_no AND report.data_type = :data_type and sa.status = :status and sa.adjustId = :adjustId";
        return sql;
    }

    private List<String> getColumns(Map<String, Column> cols) {
        List<String> colsList = new ArrayList<String>();
        for (String s : cols.keySet()) {
            colsList.add(s);
        }
        return colsList;
    }

}
