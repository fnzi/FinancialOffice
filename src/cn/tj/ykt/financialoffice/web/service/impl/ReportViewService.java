package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.Page;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.QueryCondition;
import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;
import cn.tj.ykt.financialoffice.web.service.bean.ExcelBean;
import cn.tj.ykt.financialoffice.web.service.util.ReportUtil;

@Service("reportViewService")
@Scope("request")
public class ReportViewService extends JspService {

	public static final String dateStart = "_s";
	public static final String dateEnd = "_e";
	public static final String defaultType = "text";
	public static final String dateFormat = "yyyy-MM-dd";
	public static final String amount = "amount";
	
	private Map<String, Object> queryParam = new HashMap<String, Object>();

	@Override
	public JspResult doExecute(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*** 获取参数 ***/
		String report = getParam(param, "report");
		if (report == null || report.equals("")) {
			throw new ServiceException("获取指定的报表失败");
		}
		map.put("report", report);
		
		/*** 报表名称 ***/
		String title = ConfigUtil.reportName(report);
		map.put("title", title);

		List<QueryCondition> options = ConfigUtil.options(report);
		/*** 1.查询参数 ***/
		String where = " where data_type in ('41', '10')";
		String whereCol = this.where(param, options);
		where += whereCol;
		/*** 2.筛选条件 ***/
		String opts = this.list2String(options, queryParam);
		map.put("options", opts);
		/*** 3.数据表名称 ***/
		String tName = ConfigUtil.tableName(report);
		/*** 4.校验是否进行数据查询 ***/
		boolean check = this.checkParam(param, options);
		/*** 5.数据字段 ***/
		String sqlCols = this.dataColumn(report, options, queryParam);
		/*** 数据列表 ***/
		Integer currentPage = getPageNum(param);
		Page page = new Page(currentPage);
		if (check) {
			/*** 数据列头 ***/
			showCols(report);
			String thead = this.getThead(report);
			map.put("thead", thead);

			/*** group by ***/
			String groupSql = this.groupBy(report);
			String groupBy = "";
			if (!"".equals(groupSql)) {
				groupBy = " group by " + groupSql;
			}
			
			/*** order by ***/
			String orderSql = this.orderBy(report);
			String orderBy = "";
			if (!"".equals(orderSql)) {
				orderBy = " order by " + orderSql;
			}
			 
			String sql = "select " + sqlCols + " from " + tName + where + groupBy + orderBy;
			int pageSize = ConfigUtil.max(report);
			page.setShowCount(pageSize);
			
			Page pager = getDao().findPageBySql(sql, page, queryParam);
			List<Object> allData = (List<Object>) getDao().findListBySQL(sql, queryParam);
			map.put("pager", pager);
			
			/*** 总计数据 ***/
			String sumColsSql = this.sumColsSQL(report);
			/*** 总计all ***/
			String sumSql = "select " + sumColsSql + " from " + tName + where;
			Object sumData = getDao().findBySQL(sumSql, queryParam);
			String sumHtm = this.sumColsTd(sumData);
			map.put("sumHtml", sumHtm);
			
			/*** 总计limit ***/
			String from = "select " + sumColsSql + " from " + tName + where + groupBy + orderBy + " limit " + page.getCurrentResult() + ", " + pageSize;
			String sumLimit = this.sumColsLimitSQL(report, from);
			String sumLimitSql = "select " + sumLimit;
			Object sumLimitData = getDao().findBySQL(sumLimitSql, queryParam);
			String sumHtmlLimit = this.sumColsTd(sumLimitData);
			map.put("sumHtmlLimit", sumHtmlLimit);
			
			/*** 手工调整数据 ***/
			String adjustSql = ReportUtil.adjustSql(report, sqlCols, whereCol);
			List<Object> adjustRes = (List<Object>) getDao().findListBySQL(adjustSql, queryParam);
			int flag = 0;
			if (adjustRes != null) {
				for (Object o : adjustRes) {
     				Object[] col = (Object[]) o;
     				for (int i = 1; i < col.length; i++) {
						if (col[i] == null) {
							flag++;
						}
					}
				}
			}
			String adjustHtml = "";
			if (flag != 0) {
				adjustHtml = this.adjustColsTd(adjustRes);
			}
			map.put("adjustHtml", adjustHtml);
			
			/*** 手工调整数据:总计 ***/
			String adjustSumSql = "select " + sumColsSql + " from " + tName + " where data_type in (10, 30)" + whereCol;
			Object adjustSumData = getDao().findBySQL(adjustSumSql, queryParam);
			String adjustSumHtm = this.adjustSumColsTd(adjustSumData);
			map.put("adjustSumHtml", adjustSumHtm);
			
			/** 导出参数 **/
			String isExport = getParam(param, "isExport");
			
			/*** 导出 ***/
			if (isExport.equals("1")) {
				if (allData != null) {
					this.exportXls(report, title, allData, sumData);
				}
			}
		}
		
		return new JspResult("reportView", map);
	}

    /**
     * <pre>
     * 功能描述：展示所有数据列（移除隐藏数据字段）
     * @param report
     * @return
     * </pre>
     */
    public Map<String, Column> showCols(String report) {
        return ConfigUtil.showCols(report);
    }

	/**
	 * <pre>
	 * 功能描述：queryCondition数组转换成字符串
	 * @param report
	 * @param queryParam
	 * @return
	 * </pre>
	 */
	private String list2String(List<QueryCondition> options, Map<String, Object> queryParam) {
		try {
			StringBuffer sb = new StringBuffer();
			
			if (options != null) {
				for (int i = 0; i < options.size(); i++) {
					String name = options.get(i).getName();
					String type = options.get(i).getType();
					String field = options.get(i).getMapping();
					if (type.equals("date")) {
						String format = options.get(i).getFormat();
						String start = "";
						String end = "";
						if (queryParam.containsKey(field + dateStart) && queryParam.get(field + dateStart) != null && !"".equals(queryParam.get(field + dateStart))) {
							start = queryParam.get(field + dateStart).toString();
							start = DateUtil.formatConversion(start, format, dateFormat);
						} else {
							start = DateUtil.current(dateFormat);
						}
						if (queryParam.containsKey(field + dateEnd) && queryParam.get(field + dateEnd) != null && !"".equals(queryParam.get(field + dateEnd))) {
							end = queryParam.get(field + dateEnd).toString();
							end = DateUtil.formatConversion(end, format, dateFormat);
						} else {
							end = DateUtil.current(dateFormat);
						}
						sb.append(name + ": ");
						sb.append("<input class='datepicker' type='text' readonly name='" + field + dateStart + "' id='" + field + dateStart + "' value='" + start + "' />");
						sb.append(" -- ");
						sb.append("<input class='datepicker' type='text' readonly name='" + field + dateEnd + "' id='" + field + dateEnd + "' value='" + end + "' />");
					} else if (type.equals("text")) {
						String val = "";
						if (queryParam.containsKey(field) && queryParam.get(field) != null && !"".equals(queryParam.get(field))) {
							val = queryParam.get(field).toString();
							val = StringUtil.cleanQuery(val);
						}
						sb.append(name + ": ");
						sb.append("<input type='text' name='" + field + "' id='" + field + "' value='" + val + "' />");
					} else {
						String val = "";
						if (queryParam.containsKey(field) && queryParam.get(field) != null && !"".equals(queryParam.get(field))) {
							val = queryParam.get(field).toString();
							val = StringUtil.cleanQuery(val);
						}
						sb.append(name + ": ");
						sb.append("<input type='" + defaultType + "' name='" + field + "' id='" + field + "' value='" + val + "' />");
					}
				}
			}
			
			return sb == null ? "" : sb.toString();
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}
		
	/**
	 * <pre>
	 * 功能描述：columns数组转换成字符串(name)
	 * @param report
	 * @return
	 * </pre>
	 */
	private String getThead(String report) {
		StringBuffer sb = new StringBuffer();
	
		Map<String, Column> col = this.showCols(report);
		if (col != null) {
			sb.append("<tr>");
			for (String key : col.keySet()) {
				String name = col.get(key).getName();
				sb.append("<th>" + name + "</th>");
			}
			sb.append("</tr>");
		}
		
		return sb == null ? "" : sb.toString();
	}
	
	/**
	 * 获取数据列的长度
	 * @param report
	 * @return
	 */
	private int TheadLen(String report) {
		int len = 0;
		Map<String, Column> col = this.showCols(report);
		len = col.size();
		
		return len;
	}

	/**
	 * 数据列
	 * @param report
	 * @return
	 */
	private String[] theadList(String report) {
		int len = this.TheadLen(report);
		
		String[] str = new String[len];
		Map<String, Column> col = this.showCols(report);
		if (col != null) {
			int i = 0;
			for (String key : col.keySet()) {
				String name = col.get(key).getName();
				str[i] = name;
				i ++;
			}
		}
		
		return str;
	}

	
	/**
	 * <pre>
	 * 功能描述：sql中的查询字段
	 * @param report
	 * @return
	 * </pre>
	 */
	private String dataColumn(String report, List<QueryCondition> options, Map<String, Object> queryParam) {
		StringBuffer sb = new StringBuffer();
		
		Map<String, Column> sumCols = this.sumCols(report);
		Map<String, Column> dataCols = this.showCols(report);
		if (dataCols != null && sumCols != null) {
			for (String key : dataCols.keySet()) {
				// 判断key是否存在与sumColumns
				if (sumCols.containsKey(key)) {
					String map = sumCols.get(key).getMapping();
					String type = sumCols.get(key).getType();
					String str = "case when (" + map + " is null or " + map + " = '') then '' else " + map + " end";
					if (type != null && type.equals(amount)) {
						str = "replace( " + str + ",',','' )";
						sb.append("format(sum( convert( " + str + ", decimal(10, 2) ) ), 2) as " + map + ", ");
					} else {
						sb.append("sum( convert( " + str + ", SIGNED ) ) as " + map + ", ");
					}
				} else {
					String map = dataCols.get(key).getMapping();
					String str = "";
					/*** 时间段 ***/
					if (options != null) {
						String start = "";
						String end = "";
						for (int j = 0; j < options.size(); j++) {
							String field = options.get(j).getMapping();
							String type = options.get(j).getType();
							if (field.equals(map) && type.equals("date")) {
								str = field;
								if (queryParam.containsKey(field + dateStart) && queryParam.get(field + dateStart) != null && !"".equals(queryParam.get(field + dateStart))) {
									start = queryParam.get(field + dateStart).toString();
								}
								if (queryParam.containsKey(field + dateEnd) && queryParam.get(field + dateEnd) != null && !"".equals(queryParam.get(field + dateEnd))) {
									end = queryParam.get(field + dateEnd).toString();
								}
								sb.append("'" + start + " - " + end + "' as " + map + ", ");
								break;
							}
						}
					}
					if (!map.equals(str)) {
						sb.append(map + ", ");
					}
				}
			}
		}

		String strSql = (sb == null ? "" : sb.toString());
		if (!"".equals(strSql)) {
			int len = strSql.length();
			strSql = strSql.substring(0, len - 2);
		}
				
		return strSql;
	}

	/**
	 * <pre>
	 * 功能描述：where语句
	 * @param param
	 * @param options
	 * @return
	 * </pre>
	 */
	private String where(Map<String, Object> param, List<QueryCondition> options) {
		try {
			StringBuffer sb = new StringBuffer();

			if (options.size() > 0) {
				for (int i = 0; i < options.size(); i++) {
					String type = options.get(i).getType();
					String field = options.get(i).getMapping();

					if (type.equals("date")) {
						String format = options.get(i).getFormat();
						String startBefore = (String) getParam(param, field + dateStart);
						String endBefore = (String) getParam(param, field + dateEnd);

						if (startBefore != null && !"".equals(startBefore)) {
							String start = DateUtil.formatConversion(startBefore, dateFormat, format);
							queryParam.put(field + dateStart, start);
							sb.append(" and " + field + " >= :" + field + dateStart);
						}
						if (endBefore != null && !"".equals(endBefore)) {
							String end = DateUtil.formatConversion(endBefore, dateFormat, format);
							queryParam.put(field + dateEnd, end);
							sb.append(" and " + field + " <= :" + field + dateEnd);
						}
					} else if (type.equals("text")) {
						String val = (String) getParam(param, field);
						if (val != null && !"".equals(val)) {
							queryParam.put(field, "%" + val + "%");
							sb.append(" and " + field + " like :" + field);
						}
					} else {
						String val = (String) getParam(param, field);
						if (val != null && !"".equals(val)) {
							queryParam.put(field, "%" + val + "%");
							sb.append(" and " + field + " like :" + field);
						}
					}
				}
			}

			return sb == null ? "" : sb.toString();
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}
	
	/**
	 * <pre>
	 * 功能描述：校验当前的参数（时间段）是否存在
	 * @param param
	 * @param options
	 * @return
	 * </pre>
	 */
	private Boolean checkParam(Map<String, Object> param, List<QueryCondition> options) {
		Boolean bool = false;
		if (options.size() > 0) {
			for (int i = 0; i < options.size(); i++) {
				String type = options.get(i).getType();
				String field = options.get(i).getMapping();

				if (type.equals("date")) {
					String startBefore = (String) getParam(param, field + dateStart);
					String endBefore = (String) getParam(param, field + dateEnd);

					if ((startBefore != null && !"".equals(startBefore)) && (endBefore != null && !"".equals(endBefore))) {
						bool = true;
					}
				}				
			}
		}
		
		return bool;
	}
	
	/**
	 * <pre>
	 * 功能描述：整合sumColumns
	 * @param report
	 * @return
	 * </pre>
	 */
	public Map<String, Column> sumCols(String report) {
		Map<String, Column> dataCols = this.showCols(report);
		List<String> sumCols = ConfigUtil.sumColumns(report);
		
		Map<String, Column> cols = new LinkedHashMap<String, Column>();
		if (dataCols != null) {
			for (String key : sumCols) {
				if (dataCols.containsKey(key)) {
					Column c = dataCols.get(key);
					cols.put(key, c);
				}
			}
		}
		
		return cols;
	}

	/**
	 * <pre>
	 * 功能描述：求和的查询字段
	 * @param report
	 * @param cols
	 * @return
	 * </pre>
	 */
	private String sumColsSQL(String report) {
		StringBuffer sb = new StringBuffer();
				
		Map<String, Column> sumCols = this.sumCols(report);
		Map<String, Column> dataCols = this.showCols(report);
		if (dataCols != null && sumCols != null) {
			int i = 0;
			for (String key : dataCols.keySet()) {
				// 判断key是否存在与sumColumns
				if (sumCols.containsKey(key)) {
					String map = sumCols.get(key).getMapping();
					String type = sumCols.get(key).getType();
					String str = "case when (" + map + " is null or " + map + " = '') then '' else " + map + " end";
					if (type != null && type.equals(amount)) {
						str = "replace( " + str + ",',','' )";
						sb.append("format(sum( convert( " + str + ", decimal(10, 2) ) ), 2) as " + map + ", ");
					} else {
						sb.append("sum( convert( " + str + ", SIGNED ) ) as " + map + ", ");
					}
				} else {
					String map = dataCols.get(key).getMapping();
					if (i == 0) {
						sb.append("'总计' as '总计', ");
					} else {
						sb.append("'' as " + map + ", ");
					}
					i ++;
				}
			}
		}
		
		String strSql = (sb == null ? "" : sb.toString());
		if (!"".equals(strSql)) {
			int len = strSql.length();
			strSql = strSql.substring(0, len - 2);
		}
		
		return strSql;
	}
	
	/**
	 * <pre>
	 * 功能描述：求和的查询字段（limit）
	 * @param report
	 * @param from
	 * @return
	 * </pre>
	 */
	private String sumColsLimitSQL(String report, String from) {
		StringBuffer sb = new StringBuffer();
		
		String sql = this.sumColsSQL(report);
		
		sb.append(sql + " from ("+ from +") as a ");

		return sb == null ? "" : sb.toString();
	}
	
	/**
	 * <pre>
	 * 功能描述：group by 语句
	 * @param report
	 * @return
	 * </pre>
	 */
	private String groupBy(String report) {
		StringBuffer sb = new StringBuffer();
		
		List<String> col = ConfigUtil.group(report);
		if (col != null) {
			for (String key : col) {
				sb.append(key + ", ");
			}
		}

		String str = (sb == null ? "" : sb.toString());
		if (!"".equals(str)) {
			int len = str.length();
			str = str.substring(0, len - 2);
		}
		
		return str;
	}
	
	/**
	 * <pre>
	 * 功能描述：order by 语句
	 * @param report
	 * @return
	 * </pre>
	 */
	private String orderBy(String report) {
		StringBuffer sb = new StringBuffer();
		
		List<String> col = ConfigUtil.order(report);
		if (col != null) {
			for (String key : col) {
				sb.append(key + ", ");
			}
		}
		
		String str = (sb == null ? "" : sb.toString());
		if (!"".equals(str)) {
			int len = str.length();
			str = str.substring(0, len - 2);
		}
		
		return str;
	}
	
	/**
	 * <pre>
	 * 功能描述：总计数据展示（页面）
	 * @param sumData
	 * @return
	 * </pre>
	 */
	private String sumColsTd(Object sumData){
		StringBuffer sb = new StringBuffer();
		
		if (sumData instanceof Object[]) {
			sb.append("<tr>");
			Object[] fields = (Object[]) sumData;
			for (Object o : fields) {
				sb.append("<td>" + o + "</td>");
			}
			sb.append("</tr>");
		}
		
		return sb == null ? "" : sb.toString();
	}
	
	/**
	 * <pre>
	 * 功能描述：手工调整数据展示（页面）
	 * @param adjustRes
	 * @return
	 * </pre>
	 */
	private String adjustColsTd(List<Object> adjustRes){
		StringBuffer sb = new StringBuffer();
		
		if (adjustRes != null) {
			sb.append("<tr>");
			for (Object col : adjustRes) {
 				Object[] fields = (Object[]) col;
 				for (Object o : fields) {
 					if (o != null) {
 						sb.append("<td>" + o + "</td>");
 					} else {
 						sb.append("<td>&nbsp;</td>");
 					}
 				}
			}
			sb.append("</tr>");
		}
		
		return sb == null ? "" : sb.toString();
	}

	/**
	 * <pre>
	 * 功能描述：手工调整总计数据展示（页面）
	 * @param adjustRes
	 * @return
	 * </pre>
	 */
	private String adjustSumColsTd(Object adjustSumData){
		StringBuffer sb = new StringBuffer();
		
		if (adjustSumData instanceof Object[]) {
			sb.append("<tr>");
			Object[] fields = (Object[]) adjustSumData;
			for (Object o : fields) {
				if (o != null) {
					sb.append("<td>" + o + "</td>");
				} else {
					sb.append("<td>&nbsp;</td>");
				}
			}
			sb.append("</tr>");
		}
		
		return sb == null ? "" : sb.toString();
	}

	/**
	 * 导出数据
	 * @param report
	 * @param title
	 * @param data
	 * @return
	 */
	public String exportXls(String report, String title, List<Object> data, Object sumData){
		String fileName = title + DateUtil.current(dateFormat) + ".xls";
		
		List list = new ArrayList();
		int len = this.TheadLen(report);
		
		// 数据列表
		for (Object os : data) {
			int i = 0;
			Object[] obj = new Object[len];
			for(Object o : (Object[])os) {
				obj[i] = o;
				i ++;
			}
			list.add(obj);
		}
		
		// 总计数据
		if (sumData instanceof Object[]) {
			int j = 0;
			Object[] obj = new Object[len];
			Object[] fields = (Object[]) sumData;
			for (Object o : fields) {
				obj[j] = o;
				j ++;
			}
			list.add(obj);
		}

		
		String[] str = this.theadList(report);
        
		ExcelBean.creatExcel(getResponse(), title, str, list,fileName);
		
		return null;
	}
}
