package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.DaoException;
import cn.tj.ykt.financialoffice.fw.exception.GenericException;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;

/**
 * <pre>
 * 功能描述：用于返回ext Grid服务处理
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public abstract class GridListService<T> extends ViewService<GridList<T>> {

    @Override
    public GridList<T> execute(Map<String, Object> param) {
        GridList<T> ret = new GridList<T>();

        try {
            ret = doExecute(param);
        } catch (SystemException e) {
            LogUtil.logError("系统依赖异常：" + e.getMessage(), module);
        } catch (DaoException e) {
            LogUtil.logError("数据操作异常：" + e.getMessage(), module);
        } catch (ServiceException e) {
            LogUtil.logError("业务异常：" + e.getMessage(), module);
        } catch (GenericException e) {
            LogUtil.logError("系统异常：" + e.getMessage(), module);
        }

        return ret;
    }

    public abstract GridList<T> doExecute(Map<String, Object> param);
}
