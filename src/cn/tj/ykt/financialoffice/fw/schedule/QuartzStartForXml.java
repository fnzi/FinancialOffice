package cn.tj.ykt.financialoffice.fw.schedule;

import java.util.ArrayList;
import java.util.List;

import cn.tj.ykt.financialoffice.scheduler.TransmitReportTask;
import cn.tj.ykt.financialoffice.system.cfg.ExportQueryData;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

/**
 * <pre>
 * 功能描述：定时任务从Xml启动类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class QuartzStartForXml extends QuartzStart {

    @Override
    public List<JobVo> getJobs() {
        List<JobVo> jobs = new ArrayList<JobVo>();

        ConfigurationContext context = XmlContext.getContext();
        List<ExportQueryData> exportQueryData = context.getConfigurations().getExportQueryData();

        for (ExportQueryData eqd : exportQueryData) {
            JobVo job = new JobVo();
            job.setJobName(eqd.getReportId());
            job.setJobTime(eqd.getCron());
            job.setJob(TransmitReportTask.class);
            eqd.getQuerys().put("reportId", eqd.getReportId());
            job.setParams(eqd.getQuerys());
            jobs.add(job);
        }

        return jobs;
    }
}
