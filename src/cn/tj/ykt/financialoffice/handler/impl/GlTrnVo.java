package cn.tj.ykt.financialoffice.handler.impl;

import java.util.Date;

/**
 * <pre>
 * 功能描述：A3导入vo映射对象
 * 创建者：李铁 闫世峰
 * 修改者：
 * </pre>
 */
public class GlTrnVo {

    private int id;// 主键
    private String Tf_sign;// 过账
    private Date Tr_date;// 凭证日期
    private String Tr_type;// 凭证类型-中方
    private String Tr_num;// 凭证号-中方
    private int Tr_Id;// 凭证流水号
    private String Tr_ref;// 凭证类型-外方
    private String Tr_mode;// 凭证交易类型
    private String Open_num;// 业务号
    private int Tr_bills;// 凭证附件数
    private String Tr_note;// 摘要-中法
    private String Tr_note_f;// 摘要-外放
    private String Ac_code;// 科目代码-中方
    private String Ac_code_f;// 科目代码-外方
    private String Tr_de;// 凭证借方发生额
    private String Tr_cr;// 凭证贷方
    private Date Bill_date;// 票据日期
    private String Bill_num;// 票据号码
    private String Sett_type;// 结算方式
    private String Assesor;// 审核员
    private String Producer;// 制单员
    private String Tallyer;// 记账员
    private String Asse_sign;// 审核标志
    private String Fc_code;// 货币代码
    private String Fc_money;// 外币金额
    private String Exch_rate;// 汇率
    private String Quantity;// 数量
    private String Unit_price;// 单价
    private String Tr_dept;// 部门代码
    private String Tr_proj;// 辅助项代码
    private String Tr_proj_ex;// 附加辅助项代码
    private String Tr_custom;// 客户代码
    private String Tr_vendor;// 供应商代码
    private String Tr_indivi;// 个人代码
    private String Tr_unite;// 合并方单位代码
    private String Unite_code;// 转入方单位代码
    private String ocode;// 组织号
    private String uyear;// 年度标示

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }

    public String getUyear() {
        return uyear;
    }

    public void setUyear(String uyear) {
        this.uyear = uyear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTf_sign() {
        return Tf_sign;
    }

    public void setTf_sign(String tf_sign) {
        Tf_sign = tf_sign;
    }

    public Date getTr_date() {
        return Tr_date;
    }

    public void setTr_date(Date tr_date) {
        Tr_date = tr_date;
    }

    public String getTr_type() {
        return Tr_type;
    }

    public void setTr_type(String tr_type) {
        Tr_type = tr_type;
    }

    public String getTr_num() {
        return Tr_num;
    }

    public void setTr_num(String tr_num) {
        Tr_num = tr_num;
    }

    public int getTr_Id() {
        return Tr_Id;
    }

    public void setTr_Id(int tr_Id) {
        Tr_Id = tr_Id;
    }

    public String getTr_ref() {
        return Tr_ref;
    }

    public void setTr_ref(String tr_ref) {
        Tr_ref = tr_ref;
    }

    public String getTr_mode() {
        return Tr_mode;
    }

    public void setTr_mode(String tr_mode) {
        Tr_mode = tr_mode;
    }

    public String getOpen_num() {
        return Open_num;
    }

    public void setOpen_num(String open_num) {
        Open_num = open_num;
    }

    public int getTr_bills() {
        return Tr_bills;
    }

    public void setTr_bills(int tr_bills) {
        Tr_bills = tr_bills;
    }

    public String getTr_note() {
        return Tr_note;
    }

    public void setTr_note(String tr_note) {
        Tr_note = tr_note;
    }

    public String getTr_note_f() {
        return Tr_note_f;
    }

    public void setTr_note_f(String tr_note_f) {
        Tr_note_f = tr_note_f;
    }

    public String getAc_code() {
        return Ac_code;
    }

    public void setAc_code(String ac_code) {
        Ac_code = ac_code;
    }

    public String getAc_code_f() {
        return Ac_code_f;
    }

    public void setAc_code_f(String ac_code_f) {
        Ac_code_f = ac_code_f;
    }

    public String getTr_de() {
        return Tr_de;
    }

    public void setTr_de(String tr_de) {
        Tr_de = tr_de;
    }

    public String getTr_cr() {
        return Tr_cr;
    }

    public void setTr_cr(String tr_cr) {
        Tr_cr = tr_cr;
    }

    public Date getBill_date() {
        return Bill_date;
    }

    public void setBill_date(Date bill_date) {
        Bill_date = bill_date;
    }

    public String getBill_num() {
        return Bill_num;
    }

    public void setBill_num(String bill_num) {
        Bill_num = bill_num;
    }

    public String getSett_type() {
        return Sett_type;
    }

    public void setSett_type(String sett_type) {
        Sett_type = sett_type;
    }

    public String getAssesor() {
        return Assesor;
    }

    public void setAssesor(String assesor) {
        Assesor = assesor;
    }

    public String getProducer() {
        return Producer;
    }

    public void setProducer(String producer) {
        Producer = producer;
    }

    public String getTallyer() {
        return Tallyer;
    }

    public void setTallyer(String tallyer) {
        Tallyer = tallyer;
    }

    public String getAsse_sign() {
        return Asse_sign;
    }

    public void setAsse_sign(String asse_sign) {
        Asse_sign = asse_sign;
    }

    public String getFc_code() {
        return Fc_code;
    }

    public void setFc_code(String fc_code) {
        Fc_code = fc_code;
    }

    public String getFc_money() {
        return Fc_money;
    }

    public void setFc_money(String fc_money) {
        Fc_money = fc_money;
    }

    public String getExch_rate() {
        return Exch_rate;
    }

    public void setExch_rate(String exch_rate) {
        Exch_rate = exch_rate;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUnit_price() {
        return Unit_price;
    }

    public void setUnit_price(String unit_price) {
        Unit_price = unit_price;
    }

    public String getTr_dept() {
        return Tr_dept;
    }

    public void setTr_dept(String tr_dept) {
        Tr_dept = tr_dept;
    }

    public String getTr_proj() {
        return Tr_proj;
    }

    public void setTr_proj(String tr_proj) {
        Tr_proj = tr_proj;
    }

    public String getTr_proj_ex() {
        return Tr_proj_ex;
    }

    public void setTr_proj_ex(String tr_proj_ex) {
        Tr_proj_ex = tr_proj_ex;
    }

    public String getTr_custom() {
        return Tr_custom;
    }

    public void setTr_custom(String tr_custom) {
        Tr_custom = tr_custom;
    }

    public String getTr_vendor() {
        return Tr_vendor;
    }

    public void setTr_vendor(String tr_vendor) {
        Tr_vendor = tr_vendor;
    }

    public String getTr_indivi() {
        return Tr_indivi;
    }

    public void setTr_indivi(String tr_indivi) {
        Tr_indivi = tr_indivi;
    }

    public String getTr_unite() {
        return Tr_unite;
    }

    public void setTr_unite(String tr_unite) {
        Tr_unite = tr_unite;
    }

    public String getUnite_code() {
        return Unite_code;
    }

    public void setUnite_code(String unite_code) {
        Unite_code = unite_code;
    }
}
