package cn.tj.ykt.financialoffice.web.service.util;

import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;


public class ReportUtil {

	/**
	 * <pre>
	 * 报表手工调整
	 * @param report
	 * @param queryCol
	 * @param whereCol
	 * @return
	 * </pre>
	 */
	public static String adjustSql(String report, String queryCol, String whereCol) {
		StringBuffer sb = new StringBuffer();
		
		/** 1.获取报表的数据表名 **/
		String reportTable = ConfigUtil.tableName(report);
		
		/** select 数据列 **/
		sb.append("select " + queryCol);
		
		/** from **/
		sb.append(" from ref_adjust_report a");
		
		/** left join **/
		sb.append(" left join sys_adjust b on b.adjustId = a.adjustid");
		sb.append(" left join "+ reportTable +" c on c.batch_no = a.reportbatchno");
		
		/** where **/
		sb.append(" where b.`status` = 33 and data_type = 30" + whereCol);
		
		return sb == null ? "" : sb.toString();
	}
}
