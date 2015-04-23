package cn.tj.ykt.financialoffice.system.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 功能描述：view系配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class Viewer {

    /** 求和字段 */
    private List<String> sumColumns = new ArrayList<String>();
    /** 合计分组 */
    private List<String> groupColumns = new ArrayList<String>();
    /** 排序 */
    private List<String> orderColumns = new ArrayList<String>();
    /** 是否有序号 */
    private boolean hasNum;
    /** 检索条件处理 */
    private List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
    /** 页条数 */
    private int max;
    /** 隐藏字段 */
    private List<String> hiddenColumns = new ArrayList<String>();

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

    public List<String> getHiddenColumns() {
        return hiddenColumns;
    }

    public void setHiddenColumns(List<String> hiddenColumns) {
        this.hiddenColumns = hiddenColumns;
    }

    public List<String> getGroupColumns() {
        return groupColumns;
    }

    public void setGroupColumns(List<String> groupColumns) {
        this.groupColumns = groupColumns;
    }

    public List<String> getOrderColumns() {
        return orderColumns;
    }

    public void setOrderColumns(List<String> orderColumns) {
        this.orderColumns = orderColumns;
    }

    public boolean isHasNum() {
        return hasNum;
    }

    public void setHasNum(boolean hasNum) {
        this.hasNum = hasNum;
    }
}
