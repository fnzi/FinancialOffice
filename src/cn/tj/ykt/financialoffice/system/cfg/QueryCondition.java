package cn.tj.ykt.financialoffice.system.cfg;

import cn.tj.ykt.financialoffice.fw.code.ICodeEnum;

/**
 * <pre>
 * 功能描述：view系查询条件配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class QueryCondition {

    /**
     * [查询条件的数据类型]枚举描述
     */
    public enum QueryConditionType implements ICodeEnum {
        DATE("date", "date"), INPUT("input", "input");

        QueryConditionType(String key, String description) {
            this.key = key;
            this.description = description;
        }

        private String key;
        private String description;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getDescription() {
            return description;
        }
    };

    /**
     * 查询条件的数据类型
     */
    private String type;
    /**
     * 查询条件的画面显示label
     */
    private String name;
    /**
     * 查询条件的排序
     */
    private String order;
    /**
     * 查询条件的数据库字段名字
     */
    private String mapping;
    /**
     * 查询条件的格式化（画面校验）
     */
    private String format;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
