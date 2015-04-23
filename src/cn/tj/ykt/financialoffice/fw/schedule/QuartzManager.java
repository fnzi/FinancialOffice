package cn.tj.ykt.financialoffice.fw.schedule;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

/**
 * <pre>
 * 功能描述：定时任务管理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class QuartzManager {

    private Scheduler scheduler;
    private static String TRIGGER_GROUP_NAME = "trigger1";
    private static String JOB_GROUP_NAME = "group1";

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     */
    public void addJob(String jobName, Class<? extends Job> jobClass, String jobTime, Map<String, String> params) throws SchedulerException, ParseException {

        JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, jobClass);
        for (String key : params.keySet()) {
            jobDetail.getJobDataMap().put(key, params.get(key));
        }

        SimpleTrigger trigger = new SimpleTrigger(jobName, TRIGGER_GROUP_NAME, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, Long.parseLong(jobTime));
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动
        if (!scheduler.isShutdown())
            scheduler.start();
    }

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 CronTrigger
     */
    public void addCronJob(String jobName, Class<? extends Job> jobClass, String jobTime, Map<String, String> params) throws SchedulerException, ParseException {

        JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, jobClass);
        for (String key : params.keySet()) {
            jobDetail.getJobDataMap().put(key, params.get(key));
        }

        CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME, jobTime);
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动
        if (!scheduler.isShutdown())
            scheduler.start();
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     */
    public void removeJob(String jobName) throws SchedulerException {
        scheduler.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
        scheduler.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
        scheduler.deleteJob(jobName, TRIGGER_GROUP_NAME);// 删除任务
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
