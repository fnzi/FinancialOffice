package cn.tj.ykt.financialoffice.fw.minilang;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SubtractMethodDeal implements MethodDeal {

	@Override
	public String deal(List<String> args, Map<String, Object> context) {
		String sum = "";

		if (context != null) {

			/** 1.解析参数，并相减 **/
			String first = args.get(0).toString().replaceAll(",", "");

			/** 验证参数 */
			for (String c : args) {
				if ("".equals(c)) {
					return "";
				}
			}

			BigDecimal c = new BigDecimal(first);
			if (args != null) {
				for (int i = 1; i < args.size(); i++) {
					String p = args.get(i).toString();
					p = p.replaceAll(",", "");
					c = c.subtract(new BigDecimal(p));
				}
			}

			sum = c.toString();
		}

		return sum;
	}

}
