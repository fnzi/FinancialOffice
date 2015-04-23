package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.entity.NetWorkInfo;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;

/**
 * <pre>
 * 功能描述：获取网点名称功能函数处理类
 * eg:get_network_orgnid()
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class GetNetWorkOrgnId implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {
        String str = "";

        if (context != null) {
            /** 1.解析参数 **/

            /** 2.查询数据表 **/
            String sql = "from NetWorkInfo nw where nw.flag='0'";
            GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
            List<NetWorkInfo> nws = dao.findListByHQL(sql);

            for (NetWorkInfo nw : nws) {
                str = str + nw.getNetworkCode() + ",";
            }

            if (str.length() > 0) {
                int len = str.length();
                str = str.substring(0, len - 1);
            }
        }

        return str;
    }

}
