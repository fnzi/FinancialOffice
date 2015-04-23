package cn.tj.ykt.financialoffice.fw.minilang;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;

public class NumberFormatMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {
        String str = "";

        if (context != null) {
        	/** 1.解析参数 **/
        	String val = "";
        	List<String> param = new ArrayList<String>();
        	if (args != null) {
    			for (int i = 0; i < args.size(); i++) {
    				String p = args.get(i).toString();
    				if (i == 0) {
    					val = p;
					} else {
						param = this.format(p);
					}
    			}
    		}
        	
        	/** 2.数字格式化 **/
    		try {
    			if (!val.equals("")) {
    				str = MiniLangUtil.digitalConversion(val, param.get(0).toString(), param.get(1).toString());
    			}
    		} catch (ParseException e) {
    			throw new SystemException(e.getMessage());
    		}
        	
        } else {
			throw new ServiceException("参数context不存在");
		}

        return str;
    }

    /**
     * 数字格式化
     * @return
     */
    private List<String[]> numType(){
    	List<String[]> type = new ArrayList<String[]>();
    	
    	String[] remove1k = {"removeThousandCharacter", "#,###.00", "####.00"};
    	type.add(remove1k);
    	
    	String[] add1k = {"addThousandCharacter", "####.00", "#,###.00"};
    	type.add(add1k);
    	
    	return type;
    }
    
    /**
     * 根据type查找对应的转换前格式、转换后格式
     * @param word
     * @return
     */
    private List<String> format(String word) {
    	List<String[]> numType = this.numType();
    	List<String> format = new ArrayList<String>();
    	
    	for (String[] str : numType) {
    		for(String s : str) {
    			if(word.equals(s)) {
    				format.add(str[1].toString());
    				format.add(str[2].toString());
					break;
				}    			
    		}
		}
    	
    	return format;
	}
}
