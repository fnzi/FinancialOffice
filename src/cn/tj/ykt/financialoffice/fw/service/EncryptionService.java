package cn.tj.ykt.financialoffice.fw.service;

/**
 * <pre>
 * 功能描述：系统加密服务接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface EncryptionService {

    /**
     * <pre>
     * 解密
     * </pre>
     * */
    public String dec(String str);

    /**
     * <pre>
     * 加密
     * </pre>
     * */
    public String enc(String str);
}
