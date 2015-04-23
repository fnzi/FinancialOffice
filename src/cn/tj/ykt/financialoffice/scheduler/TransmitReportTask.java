package cn.tj.ykt.financialoffice.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.tj.ykt.financialoffice.fw.dao.NativeDao;
import cn.tj.ykt.financialoffice.fw.entity.ReportStatus;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.kernel.service.DownDataService;
import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;

/**
 * <pre>
 * 功能描述：日报同步定时任务
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class TransmitReportTask implements Job {

    private static String module = TransmitReportTask.class.getName();

    public TransmitReportTask() {
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

        NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");
        Session session = dao.getHibernateSession();
        Transaction tx = null;

        NativeDao dao_a3 = (NativeDao) SpringUtil.getBean("nativeDao_a3");
        Session session_a3 = dao_a3.getHibernateSession();
        Transaction tx_a3 = null;

        String reportName = "";
        String reportId = "";

        try {

            tx = session.beginTransaction();
            tx_a3 = session_a3.beginTransaction();

            MessageBroker messageBroker = new MessageBroker();

            JobDataMap map = arg0.getJobDetail().getJobDataMap();

            Map<String, String> params = new HashMap<String, String>();

            if (!map.keySet().contains("way")) {
                throw new SystemException("日报导出<way>结点必须配置");
            } else if (!map.keySet().contains("storePath")) {
                throw new SystemException("日报导出<storePath>结点必须配置");
            } else if (!map.keySet().contains("reportId")) {
                throw new SystemException("日报导出<reportId>结点必须配置");
            } else if (!map.keySet().contains("downUrl")) {
                throw new SystemException("日报导出<downUrl>结点必须配置");
            } else if (!map.keySet().contains("trNum")) {
                throw new SystemException("日报导出<trNum>结点必须配置");
            }

            for (Object key : map.keySet()) {
                String p = String.valueOf(key);
                if (p.equals("way")) {
                    messageBroker.setWay(map.getString(p));
                } else if (p.equals("storePath")) {
                    messageBroker.setStorePath(map.getString(p));
                } else if (p.equals("reportId")) {
                    messageBroker.setReportId(map.getString(p));
                    reportId = map.getString(p);
                } else if (p.equals("downUrl")) {
                    messageBroker.setDownUrl(map.getString(p));
                } else if (p.equals("trNum")) {
                    messageBroker.setTrNum(map.getString(p));
                } else {
                    params.put(p, map.getString(p));
                }
            }

            reportName = ConfigUtil.reportName(messageBroker.getReportId());

            messageBroker.setExportQueryParams(params);

            DownDataService downData = (DownDataService) SpringUtil.getBean("downDataService");
            downData.execute(messageBroker);

            ReportStatus rs = new ReportStatus(reportId, reportName, DateUtil.current("yyyy/MM/dd HH:mm:ss"), "成功");
            dao.save(rs);

            tx.commit();
            tx_a3.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            if (tx_a3 != null) {
                tx_a3.rollback();
            }
            ReportStatus rs = new ReportStatus(reportId, reportName, DateUtil.current("yyyy/MM/dd HH:mm:ss"), "失败");
            dao.save(rs);
            LogUtil.logError(e.getMessage(), module, e);
        } finally {
            session.close();
            session_a3.close();
        }
    }
}
