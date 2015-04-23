package cn.tj.ykt.financialoffice.fw.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.minilang.InvalidException;
import cn.tj.ykt.financialoffice.fw.minilang.LoopException;
import cn.tj.ykt.financialoffice.fw.minilang.MiniLang;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;

/**
 * <pre>
 * 功能描述：A3凭证导入业务-分析组件
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Component("a3PluginAnalyseComponent")
public class A3PluginAnalyseComponent {

    public static final String module = A3PluginAnalyseComponent.class.getName();

    private Set<String> methods = null;
    private MiniLang miniLang = null;

    public List<Map<String, String>> analyse(List<Gl_Trntm> glTrntms, Map<String, Object> context) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        miniLang = MiniLang.getInstance();
        methods = miniLang.getMiniLangMethods();

        for (Gl_Trntm gl_Trntm : glTrntms) {

            Map<String, String> gltrn = new HashMap<String, String>();

            boolean isLoop = Boolean.valueOf(gl_Trntm.getIsLoop());
            if (isLoop) {
                int i = 0;

                while (true) {
                    context.put("index", i);
                    try {
                        i = i + 1;
                        gltrn = cal(gl_Trntm, context);
                        list.add(gltrn);
                    } catch (InvalidException se) {
                        continue;
                    } catch (LoopException se) {
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new SystemException(e.getMessage());
                    }
                }

            } else {
                // 没有loop的流程
                try {
                    gltrn = cal(gl_Trntm, context);
                    list.add(gltrn);
                } catch (InvalidException se) {
                    continue;
                } catch (LoopException se) {
                    continue;
                } catch (Exception e) {
                    throw new SystemException(e.getMessage());
                }
            }
        }

        return list;
    }

    public String callMiniLang(String target, Map<String, Object> context) {

        /** mock */
        miniLang = MiniLang.getInstance();
        methods = miniLang.getMiniLangMethods();

        String ret = "";

        if (target != null && !target.equals("")) {

            // 验证是否存在minilang功能函数
            boolean f = false;
            for (String m : methods) {
                if (target.indexOf(m) >= 0) {
                    f = true;
                    break;
                }
            }
            if (f) {
                ret = miniLang.exec(target, context);
            } else {
                ret = target;
            }
        }

        return ret;
    }

    private Map<String, String> cal(Gl_Trntm gl_Trntm, Map<String, Object> context) {
        /**
         * <pre>
         * 1.解析摘要
         * 2.解析借贷金额
         * 3.如果借贷金额都是""或者null或者0，则该条凭证作废
         * </pre>
         */
        String tr_cr = "";
        String tr_de = "";
        String tr_note = "";
        String tr_custom = "";
        String tr_dept = "";
        String tr_proj = "";
        String ac_code = "";

        // 借
        if (gl_Trntm.getTr_cr() != null && !gl_Trntm.getTr_cr().equals("")) {
            String gl_trntm_tr_cr = gl_Trntm.getTr_cr();
            // 验证是否存在minilang功能函数
            boolean f = false;
            for (String m : methods) {
                if (gl_trntm_tr_cr.indexOf(m) >= 0) {
                    f = true;
                    break;
                }
            }
            if (f) {
                tr_cr = miniLang.exec(gl_Trntm.getTr_cr(), context);
            } else {
                tr_cr = gl_trntm_tr_cr;
            }
        }

        // 贷
        if (gl_Trntm.getTr_de() != null && !gl_Trntm.getTr_de().equals("")) {
            String gl_trntm_tr_de = gl_Trntm.getTr_de();
            // 验证是否存在minilang功能函数
            boolean f = false;
            for (String m : methods) {
                if (gl_trntm_tr_de.indexOf(m) >= 0) {
                    f = true;
                    break;
                }
            }
            if (f) {
                tr_de = miniLang.exec(gl_Trntm.getTr_de(), context);
            } else {
                tr_de = gl_trntm_tr_de;
            }
        }

        // 结束loop
        if ((tr_cr == null || tr_cr.equals("")) && (tr_de == null || tr_de.equals(""))) {
            throw new LoopException();
        }

        LogUtil.logDebug("tr_cr: " + tr_cr);
        // 去掉无效凭证
        if (tr_cr != null && !tr_cr.equals("") && new BigDecimal(tr_cr).compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidException();
        }

        LogUtil.logDebug("tr_de: " + tr_de);
        // 去掉无效凭证
        if (tr_de != null && !tr_de.equals("") && new BigDecimal(tr_de).compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidException();
        }

        // 摘要
        if (gl_Trntm.getTr_note() != null && !gl_Trntm.getTr_note().equals("")) {

            String note = gl_Trntm.getTr_note();
            // 验证是否存在minilang功能函数
            boolean f = false;
            for (String m : methods) {
                if (note.indexOf(m) >= 0) {
                    f = true;
                    break;
                }
            }
            if (f) {
                tr_note = miniLang.exec(note, context);
            } else {
                tr_note = note;
            }
        }

        // 科目代码
        if (gl_Trntm.getAc_code() != null && !gl_Trntm.getAc_code().equals("")) {

            String accode = gl_Trntm.getAc_code();
            // 验证是否存在minilang功能函数
            boolean f = false;
            for (String m : methods) {
                if (accode.indexOf(m) >= 0) {
                    f = true;
                    break;
                }
            }
            if (f) {
                ac_code = miniLang.exec(gl_Trntm.getAc_code(), context);
            } else {
                ac_code = accode;
            }
        }

        // 客户代码
        if (gl_Trntm.getTr_custom() != null && !gl_Trntm.getTr_custom().equals("")) {

            String trCustom = gl_Trntm.getTr_custom();
            // 验证是否存在minilang功能函数
            boolean f = false;
            for (String m : methods) {
                if (trCustom.indexOf(m) >= 0) {
                    f = true;
                    break;
                }
            }
            if (f) {
                tr_custom = miniLang.exec(gl_Trntm.getTr_custom(), context);
            } else {
                tr_custom = trCustom;
            }
        }

        // 部门代码
        if (gl_Trntm.getTr_dept() != null && !gl_Trntm.getTr_dept().equals("")) {

            String trDept = gl_Trntm.getTr_dept();
            // 验证是否存在minilang功能函数
            boolean f = false;
            for (String m : methods) {
                if (trDept.indexOf(m) >= 0) {
                    f = true;
                    break;
                }
            }
            if (f) {
                tr_dept = miniLang.exec(gl_Trntm.getTr_dept(), context);
            } else {
                tr_dept = trDept;
            }
        }

        // 辅助项代码
        tr_proj = gl_Trntm.getTr_proj();

        Map<String, String> gltrn = new HashMap<String, String>();
        gltrn.put("tr_note", tr_note);
        gltrn.put("ac_code", ac_code);
        gltrn.put("tr_de", tr_de);
        gltrn.put("tr_cr", tr_cr);
        gltrn.put("tr_custom", tr_custom);
        gltrn.put("tr_dept", tr_dept);
        gltrn.put("tr_proj", tr_proj);

        return gltrn;
    }

    public int getNoteStart(String note) {
        int ret = -1;

        int start = note.indexOf("(");
        String methodStr = note.substring(0, start);

        for (String m : methods) {
            int target = methodStr.indexOf(m);
            if (target >= 0) {
                return target;
            }
        }

        return ret;
    }
}
