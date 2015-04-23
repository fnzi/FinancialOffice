package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：更新调整数据
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("updateAdjustService")
public class UpdateAdjustService extends JsonService {

    @Override
    public JsonResult doExecute(Map<String, Object> param) {
        try {
            String adjustId = (String) param.get("adjustId");
            String status = (String) param.get("status");

            GenericDao dao = getDao();

            Adjust adjust1 = dao.load(Long.parseLong(adjustId), Adjust.class);
            adjust1.setStatus(status);

            dao.update(adjust1);
            return new JsonResult(true, "处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "处理失败");
        }
    }

}