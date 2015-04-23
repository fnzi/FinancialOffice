package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.web.service.GridList;
import cn.tj.ykt.financialoffice.web.service.GridListService;

/**
 * <pre>
 * 功能描述：获取调整数据
 * 创建者：闫世峰
 * 修改者：许芳
 * </pre>
 */
@Service("getAdjustService")
public class GetAdjustService extends GridListService<Adjust> {

    @Override
    public GridList<Adjust> doExecute(Map<String, Object> param) {
        try {
            GridList<Adjust> ass = new GridList<Adjust>();
            List<Adjust> data;
            Long count;
            int start = Integer.parseInt((String) param.get("start"));
            int limit = Integer.parseInt((String) param.get("limit"));
            String status= (String) param.get("status");
            String status1= (String) param.get("status1");
            if(status1!=null){
            	  data = getDao().findPageByHql("from Adjust where status in (32,33)", start, limit);
                  count = getDao().countByHql("select count(*) from Adjust where status in (32,33) ");
            }else{
            	  data = getDao().findPageByHql(getSql(), start, limit,status);
                  count = getDao().countByHql(getCountSql(),status);
            }
            ass.setData(data);
            ass.setTotal(count.intValue());

            return ass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSql() {
        return "from Adjust where status in ? ";
    }
    
    private String getCountSql() {
        return "select count(*) from Adjust where status in ?  ";
    }

}
