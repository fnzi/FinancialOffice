package cn.tj.ykt.financialoffice.system.cfg;

/**
 * <pre>
 * 功能描述：view系DB字段配置实体描述
 * </pre>
 */
public class Column {

    /**
     * 查询条件的数据类型
     */
    private String type;
    /**
     * 查询条件的画面显示label
     */
    private String name;
    /**
     * 查询条件的数据库字段名字
     */
    private String mapping;

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

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
}
