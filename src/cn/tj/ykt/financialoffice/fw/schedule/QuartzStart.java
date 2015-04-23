package cn.tj.ykt.financialoffice.fw.schedule;

import java.util.List;

import org.quartz.Job;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;

/**
 * <pre>
 * 功能描述：定时任务启动类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public abstract class QuartzStart {

    public static final String module = QuartzStart.class.getName();

    private QuartzManager qm;

    public void start() {
        try {
            List<JobVo> jobs = getJobs();
            for (int i = 0; i < jobs.size(); i++) {
                JobVo job = jobs.get(i);
                Class<? extends Job> jobClass = job.getJob();
                String jobName = job.getJobName();
                // 执行时间
                String jobTime = job.getJobTime();
                // 执行方式
                String execFlag = job.getExecFlag();
                if (execFlag.equals("0")) {
                    qm.addJob(jobName, jobClass, jobTime, job.getParams());
                    LogUtil.logInfo("任务:{0} 以频率方式启动", module, jobName);
                } else {
                    qm.addCronJob(jobName, jobClass, jobTime, job.getParams());
                    LogUtil.logInfo("任务:{0} 以定时方式启动", module, jobName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract List<JobVo> getJobs();

    public void setQm(QuartzManager qm) {
        this.qm = qm;
    }

}
