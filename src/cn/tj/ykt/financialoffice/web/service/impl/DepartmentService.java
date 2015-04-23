package cn.tj.ykt.financialoffice.web.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.DepartmentInfo;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.JsonExtService;
import cn.tj.ykt.financialoffice.web.service.JsonResult;

/**
 * <pre>
 * 功能描述：部门代码管理功能(List,CRUD操作)
 * 创建者：许芳
 * 修改者：
 * </pre>
 */
@Transactional
@Service("departmentService")
public class DepartmentService extends JsonExtService {
	
	public Object doExecute(Map<String, Object> param) {
		 Long idl = null;
		 List<DepartmentInfo> delist;
		 BigInteger deCount;
		 int start = Integer.parseInt((String) param.get("start"));
         int limit = Integer.parseInt((String) param.get("limit"));
         GridList<DepartmentInfo> deli = new GridList<DepartmentInfo>();
          
		int meth = Integer.parseInt((String)getParam(param, "meth"));
		String id = getParam(param, "id");
		String name = getParam(param, "name");
		String code = getParam(param, "code");
		String department = getParam(param, "department");
		if (id != null && !"".equals(id)) {
			idl = Long.parseLong(id);
		}
		switch (meth) {
		case 1:
			if("".equals(name)||name==null){
				delist = getDao().findPageByHql("select d from DepartmentInfo d", start, limit);
				deCount = (BigInteger) getDao().findBySQL("select count(*) from sys_dept d ");
				
			}else{
				delist = getDao().findPageByHql("select d from DepartmentInfo d where d.name like ?", start, limit, "%"+name+"%");
				deCount = (BigInteger) getDao().findBySQL("select count(*) from sys_dept d where d.name like ?", "%"+name+"%");
			}
			int deCountInt = deCount.intValue();
			deli.setData(delist);
			deli.setTotal(deCountInt);
			return  deli;
		case 2:
			DepartmentInfo dInfo = new DepartmentInfo();
			DepartmentInfo alDInfo = getDao().findByHQL("select d from DepartmentInfo d where d.name = ?", name);
			if(alDInfo!=null){
					return new JsonResult(false, "部门名称重复！");
			}
			dInfo.setName(name);
			dInfo.setCode(code);
			dInfo.setDepartment(department);
			getDao().saveOrUpdate(dInfo);
			break;
		case 3:
			DepartmentInfo ealDInfo = getDao().findByHQL("select d from DepartmentInfo d where d.name = ?", name);
			if(ealDInfo!=null){
				if(!idl.equals(ealDInfo.getId())){
					return new JsonResult(false, "部门名称重复！");
				}
			}
			DepartmentInfo dInfoInDb = getDao().load(idl, DepartmentInfo.class);
			dInfoInDb.setName(name);
			dInfoInDb.setCode(code);
			dInfoInDb.setDepartment(department);
			getDao().update(dInfoInDb);
			break;
		case 4:
			if (idl != null) {
				DepartmentInfo ddInfo = getDao().load(idl, DepartmentInfo.class);
				getDao().delete(ddInfo);
			}
			break;
		default:
			return new JsonResult(false, "数据错误！");
		}
		return new JsonResult(true, "保存成功！");
	}

}
