package cn.tj.ykt.financialoffice.fw.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.test.BaseTest;

public class DaoTest extends BaseTest {

    @Transactional
    @Test
    public void test001() {
        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

        List<Object[]> l = (List<Object[]>) dao.findListBySQL("select * from sys_user");
        for (Object o : l.get(0)) {
            System.out.println(o);
        }
    }

    @Transactional
    @Test
    public void test002() {
        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

        String sql = "create table if not exists sss(a varchar(255) default NULL,b varchar(255) default NULL)";

        dao.executeSql(sql);

    }

    @Transactional
    @Test
    public void test003() {
        PageDao dao = (PageDao) SpringUtil.getBean("pageDao");

        Page page = new Page(1);

        Page data = dao.findPageBySql("select * from sss", page);

        for (Object os : data.getData()) {
            for (Object o : (Object[]) os) {
                System.out.print(o);
                System.out.print(", ");
            }
            System.out.println();
        }

        System.out.println(data.getPageStr());
    }

    @Test
    public void test004() {
        try {
            GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

            String sql = "insert into sss (a, b) values (:a, :b)";

            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("a", "100");
            params.put("b", "100");
            dao.executeSql(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 原生sql，like查询测试
     * </pre>
     */
    @Test
    public void test005() {
        try {
            PageDao dao = (PageDao) SpringUtil.getBean("pageDao");

            String sql = "select * from merchantsettlereport where 1=1  and merchant_name like :aa";

            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("aa", "%友谊商厦");
            List<Object> data = (List<Object>) dao.findListBySQL(sql, params);
            System.out.println(data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    public void test006() {
        GenericDao dao = (GenericDao) SpringUtil.getBean("pageDao");
        String sql = "insert into MerchantSettleReport (create_date,check_date) VALUES (:create_date,:check_date)";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("create_date", "20141028");
        params.put("check_date", "");

        dao.executeSql(sql, params);
    }
}
