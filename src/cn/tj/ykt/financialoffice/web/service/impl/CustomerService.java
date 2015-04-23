package cn.tj.ykt.financialoffice.web.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.MerchantInfo;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.JsonExtService;
import cn.tj.ykt.financialoffice.web.service.JsonResult;

/**
 * <pre>
 * 功能描述：客户代码管理功能(List,CRUD操作)
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Transactional
@Service("customerService")
public class CustomerService extends JsonExtService {
	
	public Object doExecute(Map<String, Object> param) {
		 Long idl = null;
		 List<MerchantInfo> merlist;
		 BigInteger merCount;
		 int start = Integer.parseInt((String) param.get("start"));
         int limit = Integer.parseInt((String) param.get("limit"));
         GridList<MerchantInfo> merli = new GridList<MerchantInfo>();
          
		int meth = Integer.parseInt((String)getParam(param, "meth"));
		String id = getParam(param, "id");
		String name = getParam(param, "name");
		String code = getParam(param, "code");
		String custom = getParam(param, "custom");
		if (id != null && !"".equals(id)) {
			idl = Long.parseLong(id);
		}
		switch (meth) {
		case 1:
			if ("".equals(name) || name == null) {
				merlist = getDao().findPageByHql(
						"select m from MerchantInfo m ", start, limit);
				merCount = (BigInteger) getDao().findBySQL(
						"select count(*) from sys_custom c ");
			} else {
				merlist = getDao().findPageByHql(
						"select m from MerchantInfo m where m.mername like ?",
						start, limit, "%"+name+"%");
				merCount = (BigInteger) getDao()
						.findBySQL(
								"select count(*) from sys_custom c where c.name like ?",
								"%"+name+"%");
			}
			int merCountInt = merCount.intValue();
			merli.setData(merlist);
			merli.setTotal(merCountInt);
			return merli;
		case 2:
			MerchantInfo mInfo = new MerchantInfo();
			MerchantInfo alMInfo = getDao().findByHQL("select m from MerchantInfo m where m.mername = ?", name);
			if(alMInfo!=null){
					return new JsonResult(false, "商家名称重复！");
			}
			mInfo.setMername(name);
			mInfo.setCode(code);
			mInfo.setCustom(custom);
			getDao().saveOrUpdate(mInfo);
			break;
		case 3:
			MerchantInfo ealMInfo = getDao().findByHQL("select m from MerchantInfo m where m.mername = ?", name);
			if(ealMInfo!=null){
				if(!idl.equals(ealMInfo.getCmid())){
					return new JsonResult(false, "商家名称重复！");
				}
			}
			MerchantInfo lmInfoInDb = getDao().load(idl, MerchantInfo.class);
			lmInfoInDb.setMername(name);
			lmInfoInDb.setCode(code);
			lmInfoInDb.setCustom(custom);
			getDao().update(lmInfoInDb);
			break;
		case 4:
			if (idl != null) {
				MerchantInfo lmInfo = getDao().load(idl, MerchantInfo.class);
				getDao().delete(lmInfo);
			}
			break;
		default:
			return new JsonResult(false, "数据错误！");
		}
		return new JsonResult(true, "保存成功！");
	}

}
