package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;

public class ColMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {
        String str = "";
        
        if (context != null) {
        	/** 1.获取report **/
        	String report = "";
        	if (context.get("reportId") != null) {
        		report = context.get("reportId").toString();
        	} else {
        		throw new ServiceException("没有找到对应的reportId");
    		}

        	/** 1.1 获取批次号 **/
        	String batchNo = "";
        	if (context.get("batchNo") != null) {
        		batchNo = context.get("batchNo").toString();
        	}else {
        		throw new ServiceException("没有找到对应的batchNo");
    		}

    		/** 2.解析参数 **/
    		String strVal = args.get(0).toString();
    		List<String> param = new ArrayList<String>();
    		if (args != null) {
    			for (int i = 1; i < args.size(); i++) {
    				String p = args.get(i).toString();
    				Boolean isNum = MiniLangUtil.isNumeric(p);
    				if (isNum) {
    					param.add(p);
    				} else {
    					throw new ServiceException("col函数：非行数据");
    				}
    			}
    		}

    		/** 3.查询数据表**/
    		String inParam = StringUtil.list2String(param);
    		String tName = ConfigUtil.tableName(report);
    		String sql = "select * from " + tName + " where (batch_no = '" + batchNo + "' or data_type = '40') and sort_index in (" + inParam + ") order by sort_index ";
    		GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
    		List<Object> result = (List<Object>) dao.findListBySQL(sql);
    		
    		int indexSortCol = ConfigUtil.indexColumn(report, "sort_index");
    		int index = -1;
    		
     		if (result != null) {
     			// 寻找目标列
     			for (Object o : result) {
     				Object[] col = (Object[]) o;
     				if(param.get(0).equals(String.valueOf(col[indexSortCol]))) {
     					for(Object c : col) {
     						index += 1;
     						if(strVal.equals((String) c)) {
     							break;
     						}
     					}
     				}
    			}
     			
     			// 得到目标列
     			for (Object o : result) {
     				Object[] col = (Object[]) o;
     				if(param.get(1).equals(String.valueOf(col[indexSortCol]))) {
     					str = (String) col[index];
     					break;
     				}
     			}
    		} else {
    			throw new LoopException("数据不存在__");
    		}

		} else {
			throw new ServiceException("参数context不存在");
		}

        return str;
    }

}
