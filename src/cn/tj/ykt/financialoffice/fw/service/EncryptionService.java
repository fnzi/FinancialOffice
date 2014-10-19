package cn.tj.ykt.financialoffice.fw.service;

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
