package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.GlTrntm;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：财务调整[调整凭证]服务类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("editAdjustA3Service")
public class EditAdjustA3Service extends JsonService {

    @Override
    public JsonResult doExecute(Map<String, Object> param) {
        try {

            String gtid = (String) param.get("gtid");
            String tr_note = (String) param.get("tr_note");
            String ac_code = (String) param.get("ac_code");
            String tr_de = (String) param.get("tr_de");
            String tr_cr = (String) param.get("tr_cr");
            String adjustId = (String) param.get("adjustId");
            String status = (String) param.get("status");

            GlTrntm glTrntm = getDao().get(Long.valueOf(gtid), GlTrntm.class);

            glTrntm.setTr_note(tr_note);
            glTrntm.setAc_code(ac_code);
            glTrntm.setTr_de(tr_de);
            glTrntm.setTr_cr(tr_cr);
            glTrntm.setAdjustId(Long.valueOf(adjustId));
            glTrntm.setStatus(status);

            getDao().update(glTrntm);

            return new JsonResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "操作失败");
        }
    }

}
