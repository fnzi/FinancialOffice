package cn.tj.ykt.financialoffice.fw.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import cn.tj.ykt.financialoffice.fw.entity.Adjust;
import cn.tj.ykt.financialoffice.fw.entity.GlTrntm;
import cn.tj.ykt.financialoffice.fw.entity.Menu;
import cn.tj.ykt.financialoffice.fw.entity.User;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
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

    @Transactional
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

    @Test
    public void test007() {
        String sql = "SELECT * from merchantsettlereport where number = '1' and settle_date = '20140212'";

        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
        Object data = dao.findBySQL(sql);

        if (data instanceof Object[]) {
            Object[] fields = (Object[]) data;
            for (Object o : fields) {
                System.out.println(o);
            }
        }

    }

    @Transactional
    @Test
    public void test008() {
        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

        BigInteger len = (BigInteger) dao.findBySQL("select count(*) from sys_user");
        System.out.println(len.compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void test010() {
        // GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
        NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");

        Adjust adjust1 = new Adjust();
        adjust1.setReportTime(DateUtil.current("yyyy/MM/dd hh:ss:mm"));// 业务日期
        adjust1.setAdjustSystem("小额平台");// 调整业务系统名称
        adjust1.setAdjustBusiness("商户日结算");// 调整的业务
        adjust1.setReportId("MerchantSettleReport");
        adjust1.setAdjustReason("调整");// 调整的原因
        adjust1.setAdjuster("张三");// 调整人
        adjust1.setAdjustTime(DateUtil.current("yyyy/MM/dd hh:ss:mm"));// 调整时间
        adjust1.setOldValue("100");// 调整前金额
        adjust1.setNewValue("90");// 调整后金额
        adjust1.setFlag("付款");// 收款/付款

        dao.save(adjust1);
        System.out.println(adjust1.getAdjustId());

        Adjust adjust2 = dao.get(adjust1.getAdjustId(), Adjust.class);
        System.out.println(adjust2.getAdjustId());
        System.out.println(adjust2.getAdjustSystem());

    }

    @Transactional
    @Test
    public void test012() {
        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");

        List<Adjust> adjusts = dao.findListByHQL("from Adjust");
        for (Adjust a : adjusts) {
            System.out.println(a.getAdjustId());
            System.out.println(a.getAdjustSystem());
        }
    }

    @Transactional
    @Test
    public void test014() {
        GenericDaoImpl dao = (GenericDaoImpl) SpringUtil.getBean("genericDao");

        String sql = "SELECT sa.* FROM sys_adjust sa,ref_adjust_report rar,merchantsettlereport report WHERE "
                + "rar.adjustid = sa.adjustId AND rar.reportbatchno = report.batch_no AND report.data_type = '30'";

        List<Adjust> adjusts = dao.getSession().createSQLQuery(sql).addEntity(Adjust.class).list();
        for (Adjust a : adjusts) {
            System.out.println(a.getAdjustId());
            System.out.println(a.getAdjustSystem());
        }
    }

    @Transactional
    @Test
    public void test016() {
        GenericDaoImpl dao = (GenericDaoImpl) SpringUtil.getBean("genericDao");

        final String sql = "SELECT sa.* FROM sys_adjust sa,ref_adjust_report rar,merchantsettlereport report WHERE "
                + "rar.adjustid = sa.adjustId AND rar.reportbatchno = report.batch_no AND report.data_type = '30'";

        Adjust adjust = dao.getSession().doReturningWork(new ReturningWork<Adjust>() {
            @Override
            public Adjust execute(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String t1 = rs.getString("adjustId");
                    String t2 = rs.getString(1);

                    System.out.println(t1);
                    System.out.println(t2);
                }

                Adjust a = new Adjust();
                a.setAdjustId(10L);

                return a;
            }
        });

        System.out.println(adjust.getAdjustId());
    }
    
    @Transactional
    @Test
    public void test018() {
        NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");

        String sql = "SELECT sa.* FROM sys_adjust sa,ref_adjust_report rar,merchantsettlereport report WHERE "
                + "rar.adjustid = sa.adjustId AND rar.reportbatchno = report.batch_no AND report.data_type = '30'";

        List<Adjust> adjusts = dao.getHibernateSession().createSQLQuery(sql).addEntity(Adjust.class).list();
        for (Adjust a : adjusts) {
            System.out.println(a.getAdjustId());
            System.out.println(a.getAdjustSystem());
        }
    }

    @Transactional
    @Test
    public void test020() {
        NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");

        final String sql = "SELECT sa.* FROM sys_adjust sa,ref_adjust_report rar,merchantsettlereport report WHERE "
                + "rar.adjustid = sa.adjustId AND rar.reportbatchno = report.batch_no AND report.data_type = '30'";

        Adjust adjust = dao.getJdbc(new ReturningWork<Adjust>() {
            @Override
            public Adjust execute(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String t1 = rs.getString("adjustId");
                    String t2 = rs.getString(1);

                    System.out.println(t1);
                    System.out.println(t2);
                }

                Adjust a = new Adjust();
                a.setAdjustId(10L);

                return a;
            }
        });

        System.out.println(adjust.getAdjustId());
    }

    @Test
    public void test022() {
        String sql = "select gt.* from sys_gl_trntm gt where gt.adjustId = :adjustId and gt.status = '00'";

        NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");
        List<GlTrntm> gltrntms = dao.getHibernateSession().createSQLQuery(sql).addEntity(GlTrntm.class).setParameter("adjustId", "19").list();

    }

    @Transactional
    @Test
    public void test024() {

        NativeDao dao = (NativeDao) SpringUtil.getBean("nativeDao");
        List<Menu> menus = (List<Menu>) dao.createCriteria(Menu.class).add(Restrictions.eq("keyid", "MerchantSettleReport")).list();
        for (Menu m : menus) {
            System.out.println(m.getKeyid());
        }
    }

    @Transactional
    @Test
    public void test026() {

        GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao_a3");
        Integer result = (Integer) dao.findBySQL("select max(id) as id from gl_trntm");
        System.out.println(result);
    }
    @Transactional
    @Test
    public void test028() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Tf_sign", 0);
        params.put("Fc_code", "CNY");
        params.put("Ac_code", "11352");
        params.put("Assesor", "");
        params.put("Asse_sign", "0");
        params.put("Tr_proj_ex", "");
        params.put("Bill_num", "");
        params.put("Tallyer", "");
        params.put("Bill_date", new Date());
        params.put("Tr_date", new Date());
        params.put("Tr_de", "1235587.11");
        params.put("ocode", "0004");
//        params.put("Unit_price", "0");
        params.put("id", "19820");
        params.put("Sett_type", "");
        params.put("uyear", "2015");
        params.put("Tr_proj", "");
        params.put("Tr_cr", "");
        params.put("Tr_mode", "");
        params.put("Tr_Id", 0);
//        params.put("Exch_rate", "0");
//        params.put("Quantity", "0");
        params.put("Tr_num", "111100");
        params.put("Tr_bills", "1");
        params.put("Tr_note", "12月01日小额消费统计日报（日结）");
        params.put("Tr_custom", "");
        params.put("Producer", "");
//        params.put("Fc_money", "0");
        params.put("Open_num", "0");
        params.put("Tr_dept", "");
        params.put("Tr_type", "02");
        GenericDao genericDao = (GenericDao) SpringUtil.getBean("genericDao_a3");
        genericDao
                .executeSql(
                        "insert into gl_trntm (Id,Tf_sign,Tr_date,Tr_type,Tr_num,Tr_Id,Tr_ref,Tr_mode,Open_num,Tr_bills,Tr_note,Tr_note_f,Ac_code,Ac_code_f,Tr_de,Tr_cr,Bill_date,Bill_num,Sett_type,Assesor,Producer,Tallyer,Asse_sign,Fc_code,Tr_dept,Tr_proj,Tr_proj_ex,Tr_custom,Tr_vendor,Tr_indivi,Tr_unite,Unite_code,ocode,uyear,sett_dept,invtype,orderno,empno,tr_corp,corp_num)values(:id,:Tf_sign, :Tr_date, :Tr_type, :Tr_num, :Tr_Id,'',:Tr_mode, :Open_num,:Tr_bills, :Tr_note,'',:Ac_code, '',:Tr_de,:Tr_cr, :Bill_date,:Bill_num, :Sett_type, :Assesor, :Producer, :Tallyer,:Asse_sign, :Fc_code,  :Tr_dept, :Tr_proj, :Tr_proj_ex,:Tr_custom, '','','','',:ocode,:uyear, '','','','','','')",
                        params);
    }
}
