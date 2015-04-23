package cn.tj.ykt.financialoffice.system.cfg.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.system.cfg.Column;

/**
 * <pre>
 * 功能描述：系统检查
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class SystemCheck {

    /**
     * 系统基础支持的必须字段
     */
    static List<String> systemColumn = new ArrayList<String>();

    static {
        systemColumn.add("create_date");
        systemColumn.add("sort_index");
        systemColumn.add("batch_no");
        systemColumn.add("data_type");
    }

    /**
     * 验证配置文件系统基础字段是否正确
     */
    public static void checkSystemColumn(Map<String, Column> columns) {
        for (String col : systemColumn) {
            if (!columns.containsKey(col)) {
                throw new SystemException("配置文件配置错误，[createTabler/columns]下没有发现[" + col + "]字段");
            }
        }
    }

    /**
     * 获取系统基础字段数
     */
    public static int lengthSystemColumn() {
        return systemColumn.size();
    }

    /**
     * 获取系统基础字段集合
     */
    public static List<String> getSystemColumn() {
        return systemColumn;
    }
}
