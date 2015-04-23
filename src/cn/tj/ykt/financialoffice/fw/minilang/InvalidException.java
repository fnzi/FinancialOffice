package cn.tj.ykt.financialoffice.fw.minilang;

import cn.tj.ykt.financialoffice.fw.exception.GenericException;

/**
 * <pre>
 * 功能描述：去掉无效处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class InvalidException extends GenericException {
    private static final long serialVersionUID = 1L;

    public InvalidException() {
    }

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}