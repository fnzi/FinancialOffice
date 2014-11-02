package cn.tj.ykt.financialoffice.fw.service;

/**
 * <pre>
 * 功能描述：web服务基类
 * @param <P>
 * @param <R>
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface WebService<P, R> {

    public abstract R execute(P param);
}
