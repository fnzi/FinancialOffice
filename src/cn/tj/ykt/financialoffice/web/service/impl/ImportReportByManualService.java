package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.ReportStatus;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.kernel.service.DownDataService;
import cn.tj.ykt.financialoffice.system.cfg.ExportQueryData;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

@Service("importReportByManualService")
public class ImportReportByManualService extends JsonService {

    @Override
    public JsonResult doExecute(Map<String, Object> param) {

        try {
            String reportId = (String) param.get("reportId");
            String rsid = (String) param.get("rsid");

            MessageBroker messageBroker = new MessageBroker();

            ExportQueryData eqd = getExportQueryData(reportId);
            Map<String, String> map = eqd.getQuerys();

            Map<String, String> params = new HashMap<String, String>();

            if (!map.keySet().contains("way")) {
                throw new SystemException("日报导出<way>结点必须配置");
            } else if (!map.keySet().contains("storePath")) {
                throw new SystemException("日报导出<storePath>结点必须配置");
            } else if (!map.keySet().contains("reportId")) {
                throw new SystemException("日报导出<reportId>结点必须配置");
            } else if (!map.keySet().contains("downUrl")) {
                throw new SystemException("日报导出<downUrl>结点必须配置");
            }

            for (Object key : map.keySet()) {
                String p = String.valueOf(key);
                if (p.equals("way")) {
                    messageBroker.setWay(map.get(p));
                } else if (p.equals("storePath")) {
                    messageBroker.setStorePath(map.get(p));
                } else if (p.equals("downUrl")) {
                    messageBroker.setDownUrl(map.get(p));
                } else {
                    params.put(p, map.get(p));
                }
            }

            messageBroker.setExportQueryParams(params);
            messageBroker.setUsername(eqd.getUsername());
            messageBroker.setPassword(eqd.getPassword());

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);

            ReportStatus rs = getDao().get(Long.valueOf(rsid), ReportStatus.class);
            rs.setStatus("成功");
            rs.setReportTime(DateUtil.current("yyyy/MM/dd hh:ss:mm"));
            getDao().update(rs);

            return new JsonResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "操作失败:" + e.getMessage());
        }
    }

    private ExportQueryData getExportQueryData(String reportId) {

        ConfigurationContext context = XmlContext.getContext();
        List<ExportQueryData> exportQueryData = context.getConfigurations().getExportQueryData();
        for (ExportQueryData eqd : exportQueryData) {
            if (eqd.getReportId().equals(reportId)) {
                return eqd;
            }
        }
        throw new SystemException("<querydatas>结点配置错误，没找到reportId：" + reportId);
    }
}
