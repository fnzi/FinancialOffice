package cn.tj.ykt.financialoffice.clearing;

/**
 * <pre>
 * 功能描述：清算逻辑处理接口(付款清算，收款清算)
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface ClearingProcess {

    public void process();

    public void reProcess(String settleDate);
}
