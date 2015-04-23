package cn.tj.ykt.financialoffice.fw.minilang;

import cn.tj.ykt.financialoffice.fw.exception.GenericException;

/**
 * <pre>
 * 功能描述：结束循环处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class LoopException extends GenericException {
    private static final long serialVersionUID = 1L;

    public LoopException() {
    }

    public LoopException(String message) {
        super(message);
    }

    public LoopException(String message, Throwable cause) {
        super(message, cause);
    }
}