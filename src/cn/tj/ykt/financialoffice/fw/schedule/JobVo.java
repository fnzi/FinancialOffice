package cn.tj.ykt.financialoffice.fw.schedule;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;

/**
 * <pre>
 * 功能描述：job bean
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class JobVo {

    private String jobName;
    private String jobTime;

    private Class<? extends Job> job;

    /** 频率执行还是定点执行 */
    private String execFlag = "1";

    /** 启动标示 */
    private String enableState;

    private Map<String, String> params = new HashMap<String, String>();

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    public String getExecFlag() {
        return execFlag;
    }

    public void setExecFlag(String execFlag) {
        this.execFlag = execFlag;
    }

    public String getEnableState() {
        return enableState;
    }

    public void setEnableState(String enableState) {
        this.enableState = enableState;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Class<? extends Job> getJob() {
        return job;
    }

    public void setJob(Class<? extends Job> job) {
        this.job = job;
    }

}
