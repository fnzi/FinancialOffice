package cn.tj.ykt.financialoffice.system.cfg;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 功能描述：view系配置实体描述
 * </pre>
 */
@XmlRootElement(name = "viewer")
public class Viewer {

    // 显示data字段
    private List<String> sumColumns = new ArrayList<String>();
    // 检索条件处理
    private List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
    // 页条数
    private int max;

    public List<QueryCondition> getQueryConditions() {
        return queryConditions;
    }

    public void setQueryConditions(List<QueryCondition> queryConditions) {
        this.queryConditions = queryConditions;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List<String> getSumColumns() {
        return sumColumns;
    }

    public void setSumColumns(List<String> sumColumns) {
        this.sumColumns = sumColumns;
    }
}
