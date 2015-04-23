package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：添加调整数据
 * 创建者：闫世峰
 * 修改者：许芳
 * </pre>
 */
@Service("addAdjustService")
public class AddAdjustService extends JsonService {
	@Override
    public JsonResult doExecute(Map<String, Object> param) {
        try {
            String reportTime = (String) param.get("reportTime");
            String adjustSystem = (String) param.get("adjustSystem");
            String adjustBusiness = (String) param.get("adjustBusiness");
            String reportId = (String) param.get("reportId");
            String reportName = (String) param.get("reportName");
            String adjustReason = (String) param.get("adjustReason");
            int uid = 0;
            String adjuster = (String) param.get("adjuster");
            String adjustTime = (String) param.get("adjustTime");
            String oldValue = (String) param.get("oldValue");
            String newValue = (String) param.get("newValue");
            String flag = (String) param.get("flag");
            String meth = (String) param.get("meth");
            String adjustId=(String)param.get("adjustId");
            GenericDao dao = getDao();

            Adjust adjust1 = new Adjust();
            adjust1.setReportTime(reportTime);// 业务日期
            adjust1.setAdjustSystem(adjustSystem);// 调整业务系统名称
            adjust1.setAdjustBusiness(adjustBusiness);// 调整的业务
            adjust1.setReportId(reportId);
            adjust1.setReportName(reportName);
            adjust1.setAdjustReason(adjustReason);// 调整的原因
            adjust1.setUid(uid);
            adjust1.setAdjuster(adjuster);// 调整人
            adjust1.setAdjustTime(adjustTime);// 调整时间
            adjust1.setOldValue(oldValue);// 调整前金额
            adjust1.setNewValue(newValue);// 调整后金额
            adjust1.setFlag(flag);// 收款/付款
            adjust1.setStatus("31");
            
            if(meth!=null&&"edit".equals(meth)){
            	Long adjustIdl=Long.parseLong(adjustId);
            	 adjust1.setStatus("32");
            	 adjust1.setAdjustId(adjustIdl);
            	 adjust1.setChecker((String) param.get("checker"));
//            	 adjust1.setCheckerId((String) param.get("meth"));
            	 adjust1.setCheckMsg((String) param.get("checkMsg"));
            	 dao.update(adjust1);
            	 /**
            	  * 更新对应报表数据
            	  */
//                 if (reportId == null || "".equals(reportId)) {
//                     LogUtil.logError("reportId必须输入");
//                     return new JsonResult(false, "reportId必须输入");
//                 }

//                 ConfigurationContext context = XmlContext.getContext();
//                 CreateTabler ct = context.getCreateTabler(reportId);
//                 String tableName = ct.getTableName();
//                 
//                 String sql = getSql(tableName);
//                 String selectSql = "select  ar from AdjustReport ar  where ar.adjustId=?";
//                 List<AdjustReport> reportbatchno=getDao().findListByHQL(selectSql, adjustIdl);
//                 for(int i=0;i<reportbatchno.size();i++){
//                 String batchNo = (String) reportbatchno.get(i).getReportBatchNo();
//                 try {
//                     getDao().executeSql(sql, batchNo);
//                     
//                 } catch (Exception e) {
//                     LogUtil.logError(e.getMessage(), module, e);
//                     return new JsonResult(false, "调整添加失败");
//                 }
//                 }
                 return new JsonResult(true, "数据审核成功");
            }else {
            	dao.save(adjust1);
                return new JsonResult(true, "数据添加成功");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "数据添加失败");
        }
    }
//    private String getSql(String tableName) {
//
//    	String updateSql = "update " + tableName + " set data_type = '32' where data_type = '30' and batch_no = ?";
//        return updateSql;
//    }
    
}
