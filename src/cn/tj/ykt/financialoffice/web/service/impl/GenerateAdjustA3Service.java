package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.dao.NativeDao;
import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.service.A3CreateServiceImpl;
import cn.tj.ykt.financialoffice.fw.service.A3PluginAnalyseComponent;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.handler.impl.GlTrnVo;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：生成调整凭证服务类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("generateAdjustA3Service")
public class GenerateAdjustA3Service extends JsonService {
    @Resource
    A3PluginAnalyseComponent analyse;
    
    @SuppressWarnings("rawtypes")
	@Override
    public JsonResult doExecute(Map<String, Object> param) {
        try {
            /** 1.获取adjustId **/
            String adjustId = "";
            if (param.get("adjustId") != null) {
                adjustId = param.get("adjustId").toString();
            } else {
                throw new ServiceException("adjustId不能为空");
            }

            NativeDao nativeDao = (NativeDao) SpringUtil.getBean("nativeDao");
            Adjust adjust = nativeDao.get(Long.parseLong(adjustId), Adjust.class);

            // 获取报表id
            String reportId = adjust.getReportId();

            ConfigurationContext context = XmlContext.getContext();
            CreateTabler ct = context.getCreateTabler(reportId);
            String tableName = ct.getTableName();
            Map<String, Column> cols = ct.getColumns();
            List<String> colsList = getColumns(cols);

            String sql = getDataSql(tableName, colsList);

            // 获取调整的数据
            List adjusts = getDao().findListBySQL(sql,adjustId);

            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            for (Object oInDb : adjusts) {
                Object[] os = (Object[]) oInDb;

                if (os.length != colsList.size()) {
                    throw new SystemException("调整数据检索异常");
                }

                Map<String, Object> item = new HashMap<String, Object>();
                for (int i = 0; i < colsList.size(); i++) {
                    item.put(colsList.get(i), os[i]);
                }
                datas.add(item);
            }

            String insertSql = "insert into sys_gl_trntm (tr_note, ac_code, tr_cr, tr_de, tr_custom, adjustId, status ) VALUES (:tr_note,:ac_code,:tr_cr,:tr_de,:tr_custom,:adjustId,:status)";
            Map<String, Object> report=new HashMap<String, Object>();
            report.put("reportId", reportId);
            GenericDao dao = getDao();
            // 插入A3凭证临时表
            for (Map<String, Object> d : datas) {
            	report.put("batchNo", d.get("batch_no"));
            	A3CreateServiceImpl ab =  (A3CreateServiceImpl) SpringUtil.getBean("A3CreateService");
            	List<GlTrnVo> lg= ab.execute(report); 
            	Map<String, Object> paramglt=new HashMap<String, Object>();
            	if(lg.isEmpty()){
            		return new JsonResult(false, "没有可生成凭证数据！");
            	}
            	for (int i=0;i<lg.size();i++){
            		paramglt.put("tr_note", lg.get(i).getTr_note());
            		paramglt.put("ac_code", lg.get(i).getAc_code());
            		paramglt.put("tr_cr", lg.get(i).getTr_cr());
            		paramglt.put("tr_de", lg.get(i).getTr_de());
            		paramglt.put("tr_custom", lg.get(i).getTr_custom());
            		paramglt.put("status", "00");
            		paramglt.put("adjustId", adjustId);
            		getDao().executeSql(insertSql, paramglt);
            	}
            	Long adjustIdl=Long.parseLong(adjustId);
            	adjust.setStatus("33");
            	adjust.setAdjustId(adjustIdl);
            	dao.update(adjust);
            	
//                String tr_note = lang.exec(glTrntms.get(0).getTr_note(), map);
//                String tr_cr = lang.exec(glTrntms.get(0).getTr_cr(), map);
                
               
            }

            return new JsonResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "操作失败");
        }
    }

    private String getDataSql(String tableName, List<String> cols) {
        String column = StringUtil.list2String(cols);
      
        String sql = "select " + column + " from " + tableName
                + ", ref_adjust_report rar, sys_adjust a where batch_no = rar.reportbatchno and a.adjustId = ? and a.adjustId = rar.adjustId  and data_type in ('30', '40')";
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
