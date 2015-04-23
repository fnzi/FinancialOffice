package cn.tj.ykt.financialoffice.fw.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.handler.impl.GlTrnVo;

/**
 * <pre>
 * 功能描述：A3凭证导入业务-数据插入组件
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Component("a3PluginImportDbComponent")
public class A3PluginImportDbComponent {

    public static final String module = A3PluginImportDbComponent.class.getName();

    public void importDb(List<GlTrnVo> list) {
        GenericDao genericDao = (GenericDao) SpringUtil.getBean("genericDao_a3");
        int id = 0;
        Integer result = (Integer) genericDao.findBySQL(selectMaxId());
        if (result != null) {
            id = Integer.valueOf(result);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId(id + 1);
            id++;
            genericDao.executeSql(insertMsg(list.get(i)), toMap(list.get(i)));
        }

        genericDao.executeSql(deleteRecoder());
    }

    private String selectMaxId() {
        return "select max(id) as id from gl_trntm";
    }

    private String insertMsg(GlTrnVo vo) {

        String f = "";
        String v = "";

        if (vo.getTr_de() != null && !"".equals(vo.getTr_de())) {
            f = f + "Tr_de,";
            v = v + ":Tr_de,";
        }

        if (vo.getTr_cr() != null && !"".equals(vo.getTr_cr())) {
            f = f + "Tr_cr,";
            v = v + ":Tr_cr,";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("insert into gl_trntm (Id,Tf_sign,Tr_date,Tr_type,Tr_num,Tr_Id,Tr_ref,Tr_mode,Open_num,Tr_bills,Tr_note,Tr_note_f,Ac_code,Ac_code_f,");
        sb.append(f);
        sb.append("Bill_date,Bill_num,Sett_type,Assesor,Producer,Tallyer,Asse_sign,Fc_code,Tr_dept,Tr_proj,Tr_proj_ex,Tr_custom,Tr_vendor,Tr_indivi,Tr_unite,Unite_code,ocode,uyear,sett_dept,invtype,orderno,empno,tr_corp,corp_num)values(:id,:Tf_sign, :Tr_date, :Tr_type, :Tr_num, :Tr_Id,'',:Tr_mode, :Open_num,:Tr_bills, :Tr_note,'',:Ac_code, '',");
        sb.append(v);
        sb.append(":Bill_date,:Bill_num, :Sett_type, :Assesor, :Producer, :Tallyer,:Asse_sign, :Fc_code,  :Tr_dept, :Tr_proj, :Tr_proj_ex,:Tr_custom, '','','','',:ocode,:uyear, '','','','','','')");

        return sb.toString();
    }

    private String deleteRecoder() {
        return "delete from gl_trntm where tf_sign = '1'";
    }

    private Map<String, Object> toMap(GlTrnVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", vo.getId());
        LogUtil.logDebug("id:" + vo.getId(), module);
        map.put("Tf_sign", vo.getTf_sign() == null ? "" : vo.getTf_sign());
        map.put("Tr_date", vo.getTr_date() == null ? "" : vo.getTr_date());
        map.put("Tr_type", vo.getTr_type() == null ? "" : vo.getTr_type());
        map.put("Tr_num", vo.getTr_num() == null ? "" : vo.getTr_num());
        LogUtil.logDebug("Tr_num:" + vo.getTr_num(), module);
        map.put("Tr_Id", vo.getTr_Id());
        map.put("Tr_mode", vo.getTr_mode() == null ? "" : vo.getTr_mode());
        map.put("Open_num", vo.getOpen_num() == null ? "" : vo.getOpen_num());
        map.put("Tr_bills", vo.getTr_bills());
        map.put("Tr_note", vo.getTr_note() == null ? "" : vo.getTr_note());
        map.put("Ac_code", vo.getAc_code() == null ? "" : vo.getAc_code());

        if (vo.getTr_de() != null && !"".equals(vo.getTr_de())) {
            map.put("Tr_de", vo.getTr_de());
            LogUtil.logDebug("Tr_de:" + vo.getTr_de(), module);
        }

        if (vo.getTr_cr() != null && !"".equals(vo.getTr_cr())) {
            map.put("Tr_cr", vo.getTr_cr());
            LogUtil.logDebug("Tr_cr:" + vo.getTr_cr(), module);
        }

        map.put("Bill_date", vo.getBill_date() == null ? "" : vo.getBill_date());
        map.put("Bill_num", vo.getBill_num() == null ? "" : vo.getBill_num());
        map.put("Sett_type", vo.getSett_type() == null ? "" : vo.getSett_type());
        map.put("Assesor", vo.getAssesor() == null ? "" : vo.getAssesor());
        map.put("Producer", vo.getProducer() == null ? "" : vo.getProducer());
        map.put("Tallyer", vo.getTallyer() == null ? "" : vo.getTallyer());
        map.put("Asse_sign", vo.getAsse_sign() == null ? "" : vo.getAsse_sign());
        map.put("Fc_code", vo.getFc_code() == null ? "" : vo.getFc_code());
        // map.put("Fc_money", vo.getFc_money() == null ? "" :
        // vo.getFc_money());
        // map.put("Exch_rate", vo.getExch_rate() == null ? "" :
        // vo.getExch_rate());
        // map.put("Quantity", vo.getQuantity() == null ? "" :
        // vo.getQuantity());
        // map.put("Unit_price", vo.getUnit_price() == null ? "" :
        // vo.getUnit_price());
        map.put("Tr_dept", vo.getTr_dept() == null ? "" : vo.getTr_dept());
        LogUtil.logDebug("Tr_dept:" + vo.getTr_dept(), module);
        map.put("Tr_proj", vo.getTr_proj() == null ? "" : vo.getTr_proj());
        map.put("Tr_proj_ex", vo.getTr_proj_ex() == null ? "" : vo.getTr_proj_ex());
        map.put("Tr_custom", vo.getTr_custom() == null ? "" : vo.getTr_custom());
        LogUtil.logDebug("Tr_custom:" + vo.getTr_custom(), module);
        map.put("ocode", vo.getOcode() == null ? "" : vo.getOcode());
        map.put("uyear", vo.getUyear() == null ? "" : vo.getUyear());
        return map;
    }
}
