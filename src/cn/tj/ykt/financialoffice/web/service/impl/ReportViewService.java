package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.dao.Page;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.QueryCondition;
import cn.tj.ykt.financialoffice.system.cfg.Viewer;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

@Service("reportViewService")
@Scope("request")
public class ReportViewService extends JspService {

	public static final String dateStart = "_s";
	public static final String dateEnd = "_e";
	public static final String defaultType = "text";
	public static final String dateFormat = "yyyy-MM-dd";
	
	private Map<String, Object> queryParam = new HashMap<String, Object>();

	@Override
	public JspResult execute(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*** 获取参数 ***/
		String report = getParam(param, "report");
		if (report == null || report.equals("")) {
			throw new ServiceException("获取指定的报表失败");
		}
		map.put("report", report);
		
		/*** 报表名称 ***/
		String title = this.repoetName(report);
		map.put("title", title);

		/*** 数据列头 ***/
		showCols(report);
		String thead = this.getThead(report);
		map.put("thead", thead);
		
		List<QueryCondition> options = this.options(report);
		/*** 1.查询参数 ***/
		String where = this.where(param, options);
		/*** 2.筛选条件 ***/
		String opts = this.list2String(options, queryParam);
		map.put("options", opts);
		/*** 3.数据表名称 ***/
		String tName = this.tableName(report);
		/*** 4.校验是否进行数据查询 ***/
		boolean check = this.checkParam(param, options);
		/*** 5.数据字段 ***/
		String sqlCols = this.dataColumn(report, options, queryParam);
		/*** 数据列表 ***/
		Integer currentPage = getPageNum(param);
		Page page = new Page(currentPage);
		if (check) {
			String sql = "select " + sqlCols + " from " + tName + " " + where;
			int pageSize = this.max(report);
			page.setShowCount(pageSize);
			
			Page pager = getDao().findPageBySql(sql, page, queryParam);
			map.put("pager", pager);
		}
		
		return new JspResult("reportView", map);
	}
	
	/**
	 * <pre>
	 * 功能描述：报表名称
	 * @param report
	 * @return
	 * </pre>
	 */
	public String repoetName(String report) {
		ConfigurationContext context = XmlContext.getContext();
		String title = context.getConfiguration(report).getName();
		
		return title;
	}
	
	/**
	 * <pre>
	 * 功能描述：数据表名称
	 * @param report
	 * @return
	 * </pre>
	 */
	public String tableName(String report) {
		ConfigurationContext context = XmlContext.getContext();
		CreateTabler ct = context.getCreateTabler(report);
		if (ct == null) {
			throw new ServiceException("获取指定报表的createTabler失败");
		}
		
		String name = ct.getTableName();
		return name;
	}
	
	/**
	 * <pre>
	 * @param report
	 * @return
	 * </pre>
	 */
	public int max(String report) {
		ConfigurationContext context = XmlContext.getContext();
		Viewer viewer = context.getViewer(report);
		if (viewer == null) {
			throw new ServiceException("获取指定报表的viewer失败");
		}

		int pageSize = viewer.getMax();
		return pageSize;
	}
	
	/**
	 * <pre>
	 * 功能描述：筛选条件配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public List<QueryCondition> options(String report){
		ConfigurationContext context = XmlContext.getContext();
		Viewer viewer = context.getViewer(report);
		if (viewer == null) {
			throw new ServiceException("获取指定报表的viewer失败");
		}
		
		List<QueryCondition> options = viewer.getQueryConditions();
		if (options == null) {
			options = new ArrayList<QueryCondition>();
			throw new ServiceException("获取指定报表的queryCondition失败");
		}
		
		return options;
	}
	
	
	/**
	 * <pre>
	 * 功能描述：数据列配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public Map<String, Column> tableColumns(String report) {
		ConfigurationContext context = XmlContext.getContext();
		CreateTabler ct = context.getCreateTabler(report);
		if (ct == null) {
			throw new ServiceException("获取指定报表的createTabler失败");
		}
		
		Map<String, Column> cols = ct.getColumns();
		if (cols == null) {
			cols = new LinkedHashMap<String, Column>();
			throw new ServiceException("获取指定报表的columns失败");
		}
		
		return cols;
	}
	
	/**
	 * <pre>
	 * 功能描述：隐藏字段配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public List<String> hidden(String report) {
		ConfigurationContext context = XmlContext.getContext();
		Viewer viewer = context.getViewer(report);
		if (viewer == null) {
			throw new ServiceException("获取指定报表的viewer失败");
		}

		List<String> hidden = viewer.getHiddenColumns();
		if (hidden == null) {
			hidden = new ArrayList<String>();
			throw new ServiceException("获取指定报表的hiddenColumns失败");
		}
		
		return hidden;
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
			// TODO Auto-generated catch block
			throw new SystemException(e.getMessage(), e);
		}
	}
	
	/**
	 * <pre>
	 * 功能描述：展示所有数据列
	 * @param report
	 * @return
	 * </pre>
	 */
	public Map<String, Column> showCols(String report) {
		/*** 获取hidden ***/
		List<String> hidden = this.hidden(report);
	
		Map<String, Column> col = this.tableColumns(report);
		if (col != null) {
			for (String key : hidden) {
				col.remove(key);
			}
		}
		
		return col;
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
	 * <pre>
	 * 功能描述：sql中的查询字段
	 * @param report
	 * @return
	 * </pre>
	 */
	private String dataColumn(String report, List<QueryCondition> options, Map<String, Object> queryParam) {
		StringBuffer sb = new StringBuffer();
		
		Map<String, Column> col = this.showCols(report);
		if (col != null) {
			for (String key : col.keySet()) {
				String map = col.get(key).getMapping();
				String str = "";
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

		String str = (sb == null ? "" : sb.toString());
		if (!"".equals(str)) {
			int len = str.length();
			str = str.substring(0, len - 2);
		}
		
		return str;
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
			sb.append("where 1=1");

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

}
