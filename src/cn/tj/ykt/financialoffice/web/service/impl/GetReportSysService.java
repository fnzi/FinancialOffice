package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.system.cfg.BusinessSystem;
import cn.tj.ykt.financialoffice.system.service.BusinessSystemReportServiceImpl;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.GridListService;

/**
 * 获取系统中所有报表名称/id
 * @author 张娜
 *
 * @2015年1月16日
 */
@Service("getReportSysService")
public class GetReportSysService extends GridListService<BusinessSystem> {

	@Override
	public GridList<BusinessSystem> doExecute(Map<String, Object> param) {
		GridList<BusinessSystem> bs = new GridList<BusinessSystem>();
		
		BusinessSystemReportServiceImpl bsrsi = new BusinessSystemReportServiceImpl();
		List<BusinessSystem> data = bsrsi.getBusinessSystem();
		
		bs.setData(data);
		bs.setTotal(data.size());

        return bs;
	}
}
