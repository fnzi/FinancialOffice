package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.NativeDao;
import cn.tj.ykt.financialoffice.fw.entity.GlTrntm;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：获取调整凭证服务类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("getAdjustA3Service")
public class GetAdjustA3Service extends JsonService {

    @Override
    public JsonResult doExecute(Map<String, Object> param) {
        try {

            /** 1.获取adjustId **/
            String adjustId = "";
            if (param.get("adjustId") != null) {
                adjustId = param.get("adjustId").toString();
            } else {
                throw new ServiceException("adjustId不能为空");
            }

            String sql = "select gt.* from sys_gl_trntm gt where gt.adjustId = :adjustId and gt.status = '00'";

            NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");
            List<GlTrntm> gltrntms = dao.getHibernateSession().createSQLQuery(sql).addEntity(GlTrntm.class).setParameter("adjustId", adjustId).list();

            return new JsonResult(true, gltrntms);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "操作失败");
        }
    }

}
