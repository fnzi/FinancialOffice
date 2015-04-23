package cn.tj.ykt.financialoffice.web.service;

import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.DaoException;
import cn.tj.ykt.financialoffice.fw.exception.GenericException;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;

/**
 * <pre>
 * 功能描述：view系jsp画面显示服务基类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public abstract class JspService extends ViewService<JspResult> {

    @Override
    public JspResult execute(Map<String, Object> param) {
        JspResult ret = new JspResult();

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

    public abstract JspResult doExecute(Map<String, Object> param);

}
