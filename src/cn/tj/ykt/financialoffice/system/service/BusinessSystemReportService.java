package cn.tj.ykt.financialoffice.system.service;

import java.util.List;

import cn.tj.ykt.financialoffice.system.cfg.BusinessSystem;

/**
 * <pre>
 * 功能描述：业务系统业务报表抽象服务类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface BusinessSystemReportService {

    /** 获取全部业务系统 */
    public List<BusinessSystem> getBusinessSystem();

    /** 获取全部报表 */
    public List<BusinessReport> getReports();

    /** 获取业务系统下的报表 */
    public List<BusinessReport> getReportsBySystem(String id);
}
