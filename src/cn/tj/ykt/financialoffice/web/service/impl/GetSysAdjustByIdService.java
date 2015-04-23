package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.GridListService;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * 获取指定手工调账的数据
 * @author 张娜
 *
 * @2015年1月20日
 */
@Service("getSysAdjustByIdService")
public class GetSysAdjustByIdService extends GridListService<Adjust> {

	@Override
	public GridList<Adjust> doExecute(Map<String, Object> param) {
		GridList<Adjust> ass = new GridList<Adjust>();
		
		String adjustId = (String) param.get("adjustId");
		if (adjustId == null) {
			throw new ServiceException("没有找到对应的adjustId");
		}
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("adjustId", adjustId);
		
		List<Adjust> data = getDao().findListByHQL(getSql(), queryParam);
		
        ass.setData(data);
        ass.setTotal(data.size());
        
		return ass;
	}
	
    private String getSql() {
        return "from Adjust where adjustId = :adjustId";
    }

}
