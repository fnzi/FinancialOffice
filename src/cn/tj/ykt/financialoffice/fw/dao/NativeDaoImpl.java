package cn.tj.ykt.financialoffice.fw.dao;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

/**
 * <pre>
 * 功能描述：原生dao处理接口
 *             1.获取原生hibernate session
 *             2.获取原生jdbc操作
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class NativeDaoImpl extends GenericDaoImpl implements NativeDao {

    /**
     * <pre>
     * <执行更新系jdbc操作>
     * </pre>
     */
    @Override
    public void getJdbc(Work work) {
        getSession().doWork(work);
    }

    /**
     * <pre>
     * <执行查询系jdbc操作>
     * </pre>
     */
    @Override
    public <T> T getJdbc(ReturningWork<T> returningWork) {
        return getSession().doReturningWork(returningWork);
    }

    /**
     * <pre>
     * <获取hibernate session>
     * </pre>
     */
    @Override
    public Session getHibernateSession() {
        return getSessionFactory().openSession();
    }

}
