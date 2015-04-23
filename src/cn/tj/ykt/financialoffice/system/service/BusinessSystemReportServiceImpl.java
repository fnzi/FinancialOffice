package cn.tj.ykt.financialoffice.system.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.system.cfg.BusinessSystem;
import cn.tj.ykt.financialoffice.system.cfg.Configuration;
import cn.tj.ykt.financialoffice.system.cfg.Configurations;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：业务系统业务报表抽象服务类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
@Service("businessSystemReportService")
public class BusinessSystemReportServiceImpl implements BusinessSystemReportService {

    /** 获取全部业务系统 */
    public List<BusinessSystem> getBusinessSystem() {
        Set<BusinessSystem> systems = new HashSet<BusinessSystem>();

        ConfigurationContext context = XmlContext.getContext();
        Configurations configurations = context.getConfigurations();
        Map<String, Configuration> configs = configurations.getCfgs();
        for (String key : configs.keySet()) {
            // 去掉无效的日报
            if (configs.get(key).getEnable().equals("false")) {
                continue;
            }
            systems.add(configs.get(key).getSystem());
        }

        return new ArrayList<BusinessSystem>(systems);
    }

    /** 获取全部报表 */
    public List<BusinessReport> getReports() {
        Set<BusinessReport> reports = new HashSet<BusinessReport>();

        ConfigurationContext context = XmlContext.getContext();
        Configurations configurations = context.getConfigurations();
        Map<String, Configuration> configs = configurations.getCfgs();
        for (String key : configs.keySet()) {
            // 去掉无效的日报
            if (configs.get(key).getEnable().equals("false")) {
                continue;
            }

            BusinessReport br = new BusinessReport();
            br.setReportId(configs.get(key).getId());
            br.setReportName(configs.get(key).getName());

            reports.add(br);

        }

        return new ArrayList<BusinessReport>(reports);
    }

    /** 获取业务系统下的报表 */
    public List<BusinessReport> getReportsBySystem(String id) {
        Set<BusinessReport> reports = new HashSet<BusinessReport>();

        ConfigurationContext context = XmlContext.getContext();
        Configurations configurations = context.getConfigurations();
        Map<String, Configuration> configs = configurations.getCfgs();
        for (String key : configs.keySet()) {

            // 去掉无效的日报
            if (configs.get(key).getEnable().equals("false")) {
                continue;
            }

            BusinessSystem bs = configs.get(key).getSystem();

            if (bs != null && id.equals(bs.getId().trim())) {
                BusinessReport br = new BusinessReport();
                br.setReportId(configs.get(key).getId());
                br.setReportName(configs.get(key).getName());

                reports.add(br);
            }
        }

        return new ArrayList<BusinessReport>(reports);
    }
}
