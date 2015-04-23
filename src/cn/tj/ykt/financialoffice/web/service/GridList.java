package cn.tj.ykt.financialoffice.web.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 功能描述：GridList服务类返回类型描述类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class GridList<T> {

  private int total;
  private List<T> data;
  
  public GridList() {
    total = 0;
    data = new ArrayList<T>();
  }

  public GridList(int total, List<T> data) {
    this.total = total;
    this.data = data;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
}
