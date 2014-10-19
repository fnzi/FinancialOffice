package cn.tj.ykt.financialoffice.system.cfg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * 功能描述：创建table系配置实体描述
 * </pre>
 */
public class CreateTabler {

    // 表名
    private String tableName;
    // 字段列
    private Map<String, Column> columns = new LinkedHashMap<String, Column>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(LinkedHashMap<String, Column> columns) {
        this.columns = columns;
    }

}
