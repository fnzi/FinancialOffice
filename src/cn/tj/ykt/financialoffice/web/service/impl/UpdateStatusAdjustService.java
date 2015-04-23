package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.web.service.JsonResult;
import cn.tj.ykt.financialoffice.web.service.JsonService;

/**
 * <pre>
 * 功能描述：删除、审核通过、审核不通过调整数据
 * ------31：调整数据(初始插入)调整表中的状态
 * ------32：调整数据(审核之后)调整表中的状态
 * ------33：调整数据(生成凭证之后)调整表中的状态
 * ------39：调整数据(初始化删除)调整表中的状态
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("updateStatusAdjustService")
public class UpdateStatusAdjustService extends JsonService {

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