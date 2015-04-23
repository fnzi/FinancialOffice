package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.ReportStatus;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.GridListService;

@Service("getTransmitReportStatusService")
public class GetTransmitReportStatusService extends GridListService<ReportStatus> {

    @Override
    public GridList<ReportStatus> doExecute(Map<String, Object> param) {

        try {
            GridList<ReportStatus> trss = new GridList<ReportStatus>();

            int start = Integer.valueOf((String) param.get("start"));
            int limit = Integer.valueOf((String) param.get("limit"));

            Long count = getDao().countByHql("select count(rs) from ReportStatus rs", new HashMap<String, Object>());
            List<ReportStatus> data = getDao().findPageByHql("from ReportStatus rs", start, limit, new HashMap<String, Object>());

            trss.setData(data);
            trss.setTotal(count.intValue());

            return trss;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
