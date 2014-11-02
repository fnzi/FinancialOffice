package cn.tj.ykt.financialoffice.fw.exception;

/**
 * <pre>
 * 功能描述：描述底层jar抛出的exception
 * eg：java IO
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class SystemException extends GenericException {

    private static final long serialVersionUID = 1L;

    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
