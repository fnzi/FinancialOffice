package cn.tj.ykt.financialoffice.system.cfg.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.system.cfg.CatchDataer;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.QueryCondition;
import cn.tj.ykt.financialoffice.system.cfg.Viewer;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

public class ConfigUtil {

	/**
	 * <pre>
	 * 报表名称
	 * @param report
	 * @return
	 * </pre>
	 */
	public static String reportName(String report) {
		ConfigurationContext context = XmlContext.getContext();
		String title = context.getConfiguration(report).getName();
		
		return title;
	}
	
	/**
	 * <pre>
	 * 数据表名称
	 * @param report
	 * @return
	 * </pre>
	 */
	public static String tableName(String report) {
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
	 * 每页记录数配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public static int max(String report) {
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
	 * 筛选条件配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public static List<QueryCondition> options(String report){
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
	 * 数据列配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public static Map<String, Column> tableColumns(String report) {
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
	 * sumColumns配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public static List<String> sumColumns(String report) {
		ConfigurationContext context = XmlContext.getContext();
		Viewer viewer = context.getViewer(report);
		if (viewer == null) {
			throw new ServiceException("获取指定报表的viewer失败");
		}
		
		List<String> sum = viewer.getSumColumns();
		if (sum == null) {
			sum = new ArrayList<String>();
			throw new ServiceException("获取指定报表的sumColumns失败");
		}
		
		return sum;
	}
	
	/**
	 * <pre>
	 * 隐藏字段配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public static List<String> hidden(String report) {
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
     * 功能描述：展示所有数据列（移除隐藏数据字段）
     * @param report
     * @return
     * </pre>
     */
    public static Map<String, Column> showCols(String report) {
        Map<String, Column> showCols = new LinkedHashMap<String, Column>();

        /*** 获取hiddenColumns ***/
        List<String> hidden = hidden(report);

        Map<String, Column> col = tableColumns(report);
        if (col != null) {
            for (String key : col.keySet()) {
                if (hidden.contains(key)) {
                    continue;
                } else {
                    showCols.put(key, col.get(key));
                }
            }
        }

        return showCols;
    }

	/**
	 * <pre>
	 * group by配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public static List<String> group(String report) {
		ConfigurationContext context = XmlContext.getContext();
		Viewer viewer = context.getViewer(report);
		if (viewer == null) {
			throw new ServiceException("获取指定报表的viewer失败");
		}

		List<String> group = viewer.getGroupColumns();
		if (group == null) {
			group = new ArrayList<String>();
			throw new ServiceException("获取指定报表的groupColumns失败");
		}
		
		return group;
	}
	
	/**
	 * <pre>
	 * order by配置
	 * @param report
	 * @return
	 * </pre>
	 */
	public static List<String> order(String report) {
		ConfigurationContext context = XmlContext.getContext();
		Viewer viewer = context.getViewer(report);
		if (viewer == null) {
			throw new ServiceException("获取指定报表的viewer失败");
		}
		
		List<String> order = viewer.getOrderColumns();
		if (order == null) {
			order = new ArrayList<String>();
			throw new ServiceException("获取指定报表的orderColumns失败");
		}
		
		return order;
	}

	/**
	 * <pre>
	 * 获取字段名的字段索引值
	 * @param report
	 * @param columnName
	 * @return
	 * </pre>
	 */
	public static int indexColumn(String report, String columnName) {
		Map<String, Column> cols = tableColumns(report);
		int index = -1;
		for (String colname : cols.keySet()) {
			index = index + 1;
			if (colname.equals(columnName)) {
				break;
			}
		}

		if (index == -1) {
			throw new SystemException("未找到该列，请检查：" + columnName);
		}

		return index;
	}

    /**
     * <pre>
     * 获取header数量
     * @param report
     * @return
     * </pre>
     */
    public static Integer headerSize(String report) {
        ConfigurationContext context = XmlContext.getContext();
        CatchDataer catchDataer = context.getCatchDataer(report);
        if (catchDataer == null) {
            throw new ServiceException("获取指定报表的catchDataer失败");
        }

        return catchDataer.getHeaders().size();
    }
}
