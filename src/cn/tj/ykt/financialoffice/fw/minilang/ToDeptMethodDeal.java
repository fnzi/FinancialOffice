package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;

public class ToDeptMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {
        String str = "";

        if (context != null) {
            /** 1.解析参数 **/
            String name = args.get(0).toString();
            name = name.trim();

            /** 2.查询数据表 **/
            String sql = "select department from sys_dept where `name` = '" + name + "' ";
            GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
            Object result = dao.findBySQL(sql);
            if (result != null) {
                str = result.toString();
            }
        }

        return str;
    }

}
