package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;

public class DateFormatMethodDeal implements MethodDeal {
	
    @Override
    public String deal(List<String> args, Map<String, Object> context) {
    	String date = "";
    	
    	if (context != null) {
        	/** 1.解析参数 **/
        	String val = "";
        	List<String> dateParam = new ArrayList<String>();
        	if (args != null) {
    			for (int i = 0; i < args.size(); i++) {
    				String p = args.get(i).toString();
    				if (i == 0) {
    					val = p;
					} else {
						dateParam.add(p);
					}
    			}
    		}

        	/** 2.日期格式化 **/
        	try {
        		if (!val.equals("")) {
        			date = DateUtil.formatConversion(val, dateParam.get(0).toString(), dateParam.get(1).toString());
    			}
    		} catch (Exception e) {
                throw new SystemException(e.getMessage());
    		}
        	
    	} else {
			throw new ServiceException("参数context不存在");
		}
    	    	
        return date;
    }

}