package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.NativeDao;
import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.fw.entity.GlTrntm;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.service.A3PluginImportDbComponent;
import cn.tj.ykt.financialoffice.fw.service.AbstractA3PluginService;
import cn.tj.ykt.financialoffice.handler.impl.GlTrnVo;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：将[调整凭证]导入到A3系统中服务类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("importAdjustA3Service")
public class ImportAdjustA3Service extends JsonService {

    @Resource
    NativeDao nativeDao;
    @Resource
    A3PluginImportDbComponent a3PluginImportDbComponent;

    @Override
    public JsonResult doExecute(Map<String, Object> param) {
        try {
            String adjustId = null;
            if (param.get("adjustId") != null) {
                adjustId = param.get("adjustId").toString();
            } else {
                throw new ServiceException("adjustId不能为空");
            }

            String sql = "select * from sys_gl_trntm where adjustId = :adjustId and status = '00'";

            List<GlTrntm> glTrntms = nativeDao.getHibernateSession().createSQLQuery(sql).addEntity(GlTrntm.class).setParameter("adjustId", adjustId).list();

            // 生成调整凭证
            List<GlTrnVo> list = new ArrayList<GlTrnVo>();
            for (GlTrntm gt : glTrntms) {
                GlTrnVo glTrnVo = new GlTrnVo();
                glTrnVo = AbstractA3PluginService.getGlTrnVo(glTrnVo);
                // TODO 暂时固定 凭证号 为999999
                glTrnVo.setTr_num("999999");
                // TODO 暂时固定 1
                glTrnVo.setTr_bills(1);
                glTrnVo.setTr_note(gt.getTr_note());
                glTrnVo.setAc_code(gt.getAc_code());
                glTrnVo.setTr_de(gt.getTr_de());
                glTrnVo.setTr_cr(gt.getTr_cr());
                glTrnVo.setTr_custom(gt.getTr_custom());
                list.add(glTrnVo);
            }

            // 导入到A3中
            a3PluginImportDbComponent.importDb(list);

            // 更新凭证的状态
            for (GlTrntm gt : glTrntms) {
                // 凭证已导入
                gt.setStatus("01");
                nativeDao.update(gt);
            }

            // 更新调整表的状态
            Adjust adjust = nativeDao.get(Long.valueOf(adjustId), Adjust.class);
            adjust.setStatus("33");
            nativeDao.update(adjust);

            return new JsonResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "操作失败");
        }
    }

}
