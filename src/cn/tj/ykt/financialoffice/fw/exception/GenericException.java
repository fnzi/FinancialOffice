package cn.tj.ykt.financialoffice.fw.exception;

/**
 * <pre>
 * 功能描述：exception基类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class GenericException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GenericException() {
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
