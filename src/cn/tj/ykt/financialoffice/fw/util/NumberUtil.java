package cn.tj.ykt.financialoffice.fw.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class NumberUtil {

    static String regx = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";
    static Pattern pattern = Pattern.compile(regx);

    /**
     * <pre>
     * 判断是不是科学记数法
     * </pre>
     */
    public static boolean isENum(String input) {
        return pattern.matcher(input).matches();
    }

    /**
     * <pre>
     * 科学记数法转换为字符串
     * </pre>
     */
    public static String enumToString(String input) {
        BigDecimal db = new BigDecimal(input);
        return db.toPlainString();
    }
}
