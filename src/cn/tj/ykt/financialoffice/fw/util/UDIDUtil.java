package cn.tj.ykt.financialoffice.fw.util;

import java.util.UUID;

/**
 * <pre>
 * 功能描述：UUID处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class UDIDUtil {

    public static String generator() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}