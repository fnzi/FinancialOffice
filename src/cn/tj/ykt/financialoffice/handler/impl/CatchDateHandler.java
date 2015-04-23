package cn.tj.ykt.financialoffice.handler.impl;

import java.util.Set;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.minilang.MiniLang;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：抓取Excel文件
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class CatchDateHandler extends DefaultHandler {

    public static final int cache = 10 * 1024;

    public static String YKT_REPORT = "yktreport";
    public static String XIAO_E = "xiaoe";
    public static String NET_WORK = "network";

    protected Set<String> methods = null;
    protected MiniLang miniLang = null;

    @Override
    public String process(MessageBroker messageBroker) {

        String ret = "";

        LogUtil.logInfo("抓取Excel文件");

        String way = messageBroker.getWay();
        if (way == null) {
            throw new SystemException("日报下载<way>结点必须配置，请检查");
        }

        // 获取本次数据录入批次
        String batchNo = getBatchNo();
        messageBroker.setBatchNo(batchNo);

        if (way.equals(YKT_REPORT)) {
            YktReportCatchDate yktReport = new YktReportCatchDate();
            ret = yktReport.process(messageBroker);
        } else if (way.equals(XIAO_E)) {
            XiaoeCatchDate xiaoe = new XiaoeCatchDate();
            ret = xiaoe.process(messageBroker);
        } else if (way.equals(NET_WORK)) {
            NetWorkCatchDate netWork = new NetWorkCatchDate();
            ret = netWork.process(messageBroker);
        } else {
            LogUtil.logError("日报下载way配置错误，请检查");
        }

        return ret;
    }

    /** 获取数据导入批次 */
    private String getBatchNo() {
        return String.valueOf(System.currentTimeMillis());
    }

    public String getFileName(String report, String batchNo) {
        String date = DateUtil.current("yyyyMMdd");
        return new StringBuffer("/").append(date).append("-").append(report).append("-").append(batchNo).append(".xls").toString();
    }
}
