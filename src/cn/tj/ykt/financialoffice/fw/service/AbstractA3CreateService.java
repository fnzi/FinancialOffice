package cn.tj.ykt.financialoffice.fw.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.handler.impl.GlTrnVo;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;

/**
 * <pre>
 * 功能描述：A3凭证创建业务模板类
 * 创建者：
 * 修改者：许芳
 * </pre>
 */
public abstract class AbstractA3CreateService implements A3PluginService {

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

        A3CreateServiceImpl ab = (A3CreateServiceImpl) SpringUtil.getBean("A3CreateService");
        List<Gl_Trntm> glTrntms = ab.getConfig(report);

        /** A3 vo 解析 */
        List<Map<String, String>> vos = analyse(glTrntms, context);

        String trNum = "9999";
        // 凭证编号
        trNum = generateTrNumComponent.doGenerate(trNum);

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
            list.add(glTrnVo);
        }
        return list;
    }

    private List<Gl_Trntm> getConfig(String report) {
        // TODO Auto-generated method stub
        return null;
    }

    List<Map<String, String>> analyse(List<Gl_Trntm> glTrntms, Map<String, Object> context) {
        return analyse.analyse(glTrntms, context);
    }

    public static GlTrnVo getGlTrnVo(GlTrnVo vo) {
        Calendar cal = Calendar.getInstance();

        vo.setTf_sign("0");// 过账标志
        vo.setTr_date(new Date());// 凭证日期
        vo.setTr_type("05");// 凭证类型-中方
        // vo.setTr_mode("SA");//凭证交易类型
        vo.setOpen_num("0");// 业务号
        vo.setTr_bills(0);// 凭证附件数
        vo.setBill_date(new Date());// 票据日期
        vo.setBill_num("");// 票据号码
        vo.setAsse_sign("0");// 审核标志
        vo.setFc_code("CNY");// 货币代码
        vo.setOcode("0004");
        vo.setUyear(String.valueOf(cal.get(Calendar.YEAR)));

        return vo;
    }
}
