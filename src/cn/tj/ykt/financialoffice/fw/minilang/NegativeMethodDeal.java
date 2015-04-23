package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

public class NegativeMethodDeal implements MethodDeal {

	@Override
	public String deal(List<String> args, Map<String, Object> context) {
		String str = "";
		String amount = args.get(0).toString();
		
		if (!"".equals(amount)) {
			str = "-" + amount;
		}
		
		return str;
	}

}
