package cn.tj.ykt.middleware.fw.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.dao.Page;
import cn.tj.ykt.financialoffice.fw.dao.PageDao;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.middleware.test.BaseTest;

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
}
