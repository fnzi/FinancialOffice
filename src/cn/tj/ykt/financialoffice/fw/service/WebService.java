package cn.tj.ykt.financialoffice.fw.service;

/**
 * web服务基类
 *
 * @param <P>
 * @param <R>
 */
public interface WebService<P, R> {

    public abstract R execute(P param);
}
