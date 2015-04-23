package cn.tj.ykt.financialoffice.fw.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.handler.impl.GlTrnVo;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;

/**
 * <pre>
 * 功能描述：A3凭证导入业务模板类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public abstract class AbstractA3PluginService implements A3PluginService {

    @Resource
    A3PluginAnalyseComponent analyse;

    @Resource
    GenerateTrNumComponent generateTrNumComponent;

    /**
     * <pre>
     * 参数context说明：
     * 1.reportId->日报id
     * 2.batchNo ->List<String>
     * </pre>
     */
    @Override
    public List<GlTrnVo> execute(Map<String, Object> context) {

        /** 1.获取report **/
        String report = "";
        if (context.get("reportId") != null) {
            report = context.get("reportId").toString();
        } else {
            throw new ServiceException("没有找到对应的reportId");
        }

        /** 2.获取凭证编号 */
        String trNum = "";
        if (context.get("trNum") != null) {
            trNum = context.get("trNum").toString();
        } else {
            throw new ServiceException("没有找到对应的trNum");
        }

        // /** 1.1 检查批次号 **/
        // if (!(context.get("batchNo") instanceof List)) {
        // throw new ServiceException("batchNo参数不正确");
        // }

        List<Gl_Trntm> glTrntms = getConfig(report);

        // 凭证编号
        trNum = generateTrNumComponent.doGenerate(trNum);

        /** A3 vo 解析 */
        List<Map<String, String>> vos = analyse(glTrntms, context);

        List<GlTrnVo> list = new ArrayList<GlTrnVo>();
        for (Map<String, String> vo : vos) {
            GlTrnVo glTrnVo = new GlTrnVo();
            glTrnVo = getGlTrnVo(glTrnVo);
            glTrnVo.setTr_num(trNum);
            // TODO 暂时固定 1
            glTrnVo.setTr_bills(1);
            glTrnVo.setTr_note(vo.get("tr_note") == null ? "" : vo.get("tr_note"));
            glTrnVo.setAc_code(vo.get("ac_code") == null ? "" : vo.get("ac_code"));
            glTrnVo.setTr_de(vo.get("tr_de") == null ? "" : vo.get("tr_de"));
            glTrnVo.setTr_cr(vo.get("tr_cr") == null ? "" : vo.get("tr_cr"));
            glTrnVo.setTr_custom(vo.get("tr_custom") == null ? "" : vo.get("tr_custom"));
            glTrnVo.setTr_dept(vo.get("tr_dept") == null ? "" : vo.get("tr_dept"));
            glTrnVo.setTr_proj(vo.get("tr_proj") == null ? "" : vo.get("tr_proj"));
            list.add(glTrnVo);
        }

        /** A3数据导入 */
        importDb(list);

        return list;
    }

    public abstract List<Gl_Trntm> getConfig(String reportId);

    List<Map<String, String>> analyse(List<Gl_Trntm> glTrntms, Map<String, Object> context) {
        return analyse.analyse(glTrntms, context);
    }

    public void importDb(List<GlTrnVo> list) {
        // do nothing
    }

    public static GlTrnVo getGlTrnVo(GlTrnVo vo) {
        Calendar cal = Calendar.getInstance();

        vo.setTf_sign("0");// 过账标志
        vo.setTr_date(DateUtil.getThisMonthToday());
        vo.setTr_type("05");// 凭证类型-中方
        // vo.setTr_mode("SA");//凭证交易类型
        vo.setOpen_num("0");// 业务号
        vo.setTr_bills(0);// 凭证附件数
        vo.setBill_date(DateUtil.getThisMonthToday());
        vo.setBill_num("");// 票据号码
        vo.setAsse_sign("0");// 审核标志
        vo.setFc_code("CNY");// 货币代码
        vo.setOcode("0004");
        vo.setUyear(String.valueOf(cal.get(Calendar.YEAR)));

        return vo;
    }
}
