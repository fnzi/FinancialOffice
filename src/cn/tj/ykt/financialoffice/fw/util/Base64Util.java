package cn.tj.ykt.financialoffice.fw.util;

import it.sauronsoftware.base64.Base64;

/**
 * <pre>
 * 功能描述：base64加密解密处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class Base64Util {

    public static String charset = "utf-8";

    /**
     * base64解密
     */
    public static String dec(String target) {
        return Base64.decode(target, charset);
    }

    /**
     * base64加密
     */
    public static String enc(String target) {
        return Base64.encode(target, charset);
    }
}
