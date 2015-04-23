package cn.tj.ykt.financialoffice.fw.service;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.tj.ykt.financialoffice.fw.util.PreferencesUtil;

/**
 * <pre>
 * 功能描述：A3凭证编号生成处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Component("generateTrNumComponent")
public class GenerateTrNumComponent {

    PreferencesUtil preferencesUtil = new PreferencesUtil();

    private static String NUM = "__NUM__";

    /**
     * 凭证编号格式： 凭证号+日期
     */
    public synchronized String doGenerate(String trNum) {

        trNum = StringUtils.leftPad(trNum, 4, "0");

        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.get(Calendar.DATE));

        date = StringUtils.leftPad(date, 2, "0");

        StringBuffer sb = new StringBuffer();
        sb.append(date).append(trNum);

        return sb.substring(0, 6);
    }

    /**
     * 生成序号
     */
    public synchronized String doGenerateNum() {

        String old = preferencesUtil.get(NUM);
        if (old == null || old.equals("") || old.length() > 2) {
            old = "0";
        }

        int num = Integer.parseInt(old);
        String ret = StringUtils.leftPad(String.valueOf(num), 2, "0");

        return ret;
    }

    /**
     * 序号增长
     */
    public synchronized void addNum() {
        String old = doGenerateNum();

        int num = Integer.parseInt(old) + 1;
        String ret = StringUtils.leftPad(String.valueOf(num), 2, "0");
        preferencesUtil.put(NUM, ret);
    }
}
