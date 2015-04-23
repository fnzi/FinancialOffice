package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：调整删除页面
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("adjustDeleteGridDataService")
public class AdjustDeleteGridDataService extends JsonService {

    private String module = AdjustDeleteGridDataService.class.getName();

    @Override
    public JsonResult doExecute(Map<String, Object> param) {

        String reportId = (String) param.get("reportId");
        if (reportId == null || "".equals(reportId)) {
            LogUtil.logError("reportId必须输入");
            return new JsonResult(false, "reportId必须输入");
        }

        String batchNo = (String) param.get("batchNo");
        if (batchNo == null || "".equals(batchNo)) {
            LogUtil.logError("batchNo必须输入");
            return new JsonResult(false, "batchNo必须输入");
        }

        ConfigurationContext context = XmlContext.getContext();
        CreateTabler ct = context.getCreateTabler(reportId);
        String tableName = ct.getTableName();

        String sql = getSql(tableName);

        try {
            getDao().executeSql(sql, batchNo);
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), module, e);
            return new JsonResult(false, "调整删除失败");
        }

        return new JsonResult(true, "调整删除成功");
    }

    private String getSql(String tableName) {
        String sql = "update " + tableName + " set data_type = '90' where data_type = '30' and batch_no = ?";
        return sql;
    }
}
