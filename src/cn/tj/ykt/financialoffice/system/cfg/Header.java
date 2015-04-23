package cn.tj.ykt.financialoffice.system.cfg;

/**
 * <pre>
 * 功能描述：抓取数据系excel模板header配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class Header {

    private String sort_index;
    private String description;
    private String readonly;

    public String getSort_index() {
        return sort_index;
    }

    public void setSort_index(String sort_index) {
        this.sort_index = sort_index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }
}
