package cn.tj.ykt.financialoffice.fw.exception;

/**
 * <pre>
 * 功能描述：描述数据库级别exception
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class DaoException extends GenericException {

    private static final long serialVersionUID = 1L;

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
