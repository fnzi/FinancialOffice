package cn.tj.ykt.financialoffice.fw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 功能描述：StringUtil处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class StringUtil {

    /**
     * <pre>
     * 字符串累加
     * @param str
     * @return
     * </pre>
     */
    public static String append(String... str) {
        StringBuffer sb = new StringBuffer();

        for (String s : str) {
            sb.append(s);
        }

        return sb.toString();
    }

    /**
     * <pre>
     * 清理[%]
     * @param str
     * @return
     * </pre>
     */
    public static String cleanQuery(String query) {
        if (query != null) {
            if (query.startsWith("%")) {
                int len = query.length();
                query = query.substring(1, len);
            }
            if (query.endsWith("%")) {
                int len = query.length();
                query = query.substring(0, len - 1);
            }
        }
        return query;
    }

    /**
     * <pre>
     * 清理[\t\r\n]
     * @param str
     * @return
     * </pre>
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) {
        String q = "qqqq";
        // System.out.println(StringUtil.cleanQuery(q));
        String q1 = "%qqqq";
        // System.out.println(StringUtil.cleanQuery(q1));
        String q2 = "qqqq%";
        // System.out.println(StringUtil.cleanQuery(q2));
        String q3 = "%qqqq%";
        // System.out.println(StringUtil.cleanQuery(q3));

        String str = StringUtil.append(q, q1, q2, q3);
        System.out.println(str);
    }
}
