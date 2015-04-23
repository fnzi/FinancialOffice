package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;

public class RowColMethodDeal implements MethodDeal {
	
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
        	
        	/** 2.转换数据列（字母转换数字） **/
        	String strEn = "";
        	String strNum = "";
        	if (args != null) {
    			for (int i = 0; i < args.size(); i++) {
    				String p = args.get(i).toString();
    				Boolean isEn = MiniLangUtil.strIsEnglish(p);
    				if (isEn) {
    					strEn = p;
    				} else {
    					strNum = p;
    				}
    			}
    		}
        	int num = 0;
        	if (!strEn.equals("")) {
        		num = MiniLangUtil.letter2Num(strEn);
    		} else {
    			throw new ServiceException("没有找到对应的数据列（英文字母）");
    		}
        
        	/** 3.获取当前报表的数据列 **/
        	String dataCol = "";
        	Map<String, Column> column = ConfigUtil.tableColumns(report);
        	List<String> list = new ArrayList<String>();
        	if (column != null) {
        		for (String key : column.keySet()) {
        			list.add(key);
    			}
        		if (list.get(num).toString() != null) {
        			dataCol = list.get(num).toString();
    			} else {
    				throw new ServiceException("对应的数据列不存在");
    			}
    		}
        	
        	/** 4.查询数据表**/
    		String tName = ConfigUtil.tableName(report);
    		String sql = "select " + dataCol + " from " + tName + " where (batch_no = '" + batchNo + "' or data_type = '40') and sort_index = " + strNum + " order by sort_index ";
    		GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
    		Object result = dao.findBySQL(sql);
    		if (result != null) {
    			str = result.toString();
    		} else {
    			throw new LoopException("数据不存在__");
    		}
    		
    	} else {
			throw new ServiceException("参数context不存在");
		}
    	    	
        return str;
    }
    
    

}