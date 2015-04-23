package cn.tj.ykt.financialoffice.system.cfg;

/**
 * <pre>
 * 功能描述：A3凭证vo配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class Gl_Trntm {

    private String tr_note = "";
    private String ac_code = "";
    private String tr_cr = "";
    private String tr_de = "";
    private String tr_custom = "";
    private String tr_dept = "";
    private String tr_proj = "";

    private String isLoop = "";

    public String getTr_note() {
        return tr_note;
    }

    public void setTr_note(String tr_note) {
        this.tr_note = tr_note;
    }

    public String getAc_code() {
        return ac_code;
    }

    public void setAc_code(String ac_code) {
        this.ac_code = ac_code;
    }

    public String getTr_cr() {
        return tr_cr;
    }

    public void setTr_cr(String tr_cr) {
        this.tr_cr = tr_cr;
    }

    public String getTr_custom() {
        return tr_custom;
    }

    public void setTr_custom(String tr_custom) {
        this.tr_custom = tr_custom;
    }

    public String getTr_de() {
        return tr_de;
    }

    public void setTr_de(String tr_de) {
        this.tr_de = tr_de;
    }

    public String getTr_dept() {
        return tr_dept;
    }

    public void setTr_dept(String tr_dept) {
        this.tr_dept = tr_dept;
    }

    public String getIsLoop() {
        return isLoop;
    }

    public void setIsLoop(String isLoop) {
        this.isLoop = isLoop;
    }

    public String getTr_proj() {
        return tr_proj;
    }

    public void setTr_proj(String tr_proj) {
        this.tr_proj = tr_proj;
    }

}
