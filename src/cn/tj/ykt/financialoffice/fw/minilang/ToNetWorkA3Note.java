package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;

/**
 * <pre>
 * 功能描述：获取网点名称功能函数处理类
 * eg:to_networka3note('友谊路客服网点')
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class ToNetWorkA3Note implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {
        String str = "";

        if (context != null) {
            /** 1.解析参数 **/
            String name = args.get(0).toString();
            name = name.trim();

            /** 2.查询数据表 **/
            String sql = "select a3name from sys_dept where `name` = '" + name + "' ";
            GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
            Object result = dao.findBySQL(sql);
            if (result != null) {
                str = result.toString();
            }
        }

        return str;
    }

}
