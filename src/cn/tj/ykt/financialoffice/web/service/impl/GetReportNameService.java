package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.system.cfg.BusinessSystem;
import cn.tj.ykt.financialoffice.system.service.BusinessReport;
import cn.tj.ykt.financialoffice.system.service.BusinessSystemReportServiceImpl;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.GridListService;

/**
 * 获取系统中所有报表名称/id
 * @author 张娜
 *
 * @2015年1月16日
 */
@Service("getReportNameService")
public class GetReportNameService extends GridListService<BusinessReport> {

	@Override
	public GridList<BusinessReport> doExecute(Map<String, Object> param) {
		String sysId = (String) param.get("sysId");
		
		GridList<BusinessReport> br = new GridList<BusinessReport>();
		
		BusinessSystemReportServiceImpl bsrsi = new BusinessSystemReportServiceImpl();
		List<BusinessReport> data = bsrsi.getReportsBySystem(sysId);
		
		br.setData(data);
		br.setTotal(data.size());

        return br;
	}

}
