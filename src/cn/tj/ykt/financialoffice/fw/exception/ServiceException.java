package cn.tj.ykt.financialoffice.fw.exception;

/**
 * <pre>
 * 功能描述：描述业务级别exception
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class ServiceException extends GenericException {

    private static final long serialVersionUID = 1L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
