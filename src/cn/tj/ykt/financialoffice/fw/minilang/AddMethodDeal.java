package cn.tj.ykt.financialoffice.fw.minilang;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AddMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {
    	String sum = "";
    	
    	if (context != null) {
			/** 验证参数 */
			for (String c : args) {
				if ("".equals(c)) {
					return "";
				}
			}
	
        	/** 1.解析参数，并相加 **/
        	BigDecimal c = new BigDecimal(0.00);
        	if (args != null) {
        		for (int i = 0; i < args.size(); i++) {
        			String p = args.get(i).toString();
        			p = p.replaceAll(",", "");
        			c = c.add(new BigDecimal(p));
        		}
			}
        	    		
        	sum = c.toString();
    	}
    	
    	return sum;
    }

}
