package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.service.A3PluginAnalyseComponent;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.test.BaseTest;

//public class MiniLangTest extends BaseTest {
public class MiniLangTest {
    @Test
    public void test001() {
        MiniLang lang = MiniLang.getInstance();

        lang.exec("method(1,22,333)", null);
    }

    @Test
    public void test002() {
        MiniLang lang = MiniLang.getInstance();

        System.out.println(lang.exec("date_format(1,22,333)", null));
    }

    @Test
    public void test003() {
        String str = "date_format(rowCol(A,2), 'MM.dd')日公交换卡—卡余额";

        int start = str.indexOf("date_format");
        int end = str.lastIndexOf(")");

        String method = str.substring(start, end + 1);

        String last = str.replace(method, MiniLang.getInstance().exec(method, null));

        System.out.println(method);
        System.out.println(last);

    }

    @Test
    public void test004() {
        String str = "rowCol(A,2), 'MM.dd'";
        int end = str.lastIndexOf(")");

        System.out.println(str.substring(0, end + 1));
        System.out.println(str.substring(end + 1));
    }

    @Test
    public void test005() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);

        String str = "rowCol(B,4)";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(str, map);
        System.out.println(res);
    }

    @Test
    public void test006() {
        String str = "date_format(row(A,2), 'MM.dd')";

        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(str, null);

        String argMethod = "row(A,2)";
        lang.exec(argMethod, null);
    }

    @Test
    public void test007() {
        String report = "MerchantSettleWeekReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1421052458288");

        String tr_note = "row('天津天越文化传播有限公司',D,F)";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(tr_note, map);
        System.out.println(res);
    }

    @Test
    public void test008() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1420797401948");

        // String tr_note = "add(add(add('1', '2'), '3'), '4')";
        String tr_note = "add(add(add('1', '2'), add('2', '2')), '4')";

        // String tr_note = "add('5', '2')";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(tr_note, map);
        System.out.println(res);
    }

    @Test
    public void test009() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1420797401948");

        String tr_note = "row('天津一商友谊有限公司-名都店', 'D', 'E')";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(tr_note, map);
        System.out.println(res);
    }

    @Test
    public void test010() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1420797401948");

        // String tr_note = "add(add(add('1', '2'), '3'), '4')";
        String tr_note = "add_add('中文', '4')";

        // String tr_note = "add('5', '2')";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(tr_note, map);
        System.out.println(res);
    }

    @Test
    public void test011() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1420797401948");

        String tr_note = "col('20140212',5,4)";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(tr_note, map);
        System.out.println(res);
    }

    @Test
    public void test013() {
        List<String> list = new ArrayList<String>();
        list.add("A");
        // list.add("B");
        String a = StringUtil.list2String(list);
        System.out.println(a);
    }

    @Test
    public void test015() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1421374800902");

        String tr_note1 = "number_format(row('天津一商友谊有限公司-名都店', 'D', 'E'), 'removeThousandCharacter')";
        String tr_note2 = "date_format(rowCol(B,4), 'yyyyMMdd', 'MM月dd')";
        MiniLang lang = MiniLang.getInstance();
        String res1 = lang.exec(tr_note1, map);
        System.out.println(res1);

        String res2 = lang.exec(tr_note2, map);
        System.out.println(res2);
    }

    @Test
    public void test017() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1421374800902");
        map.put("index", "1");

        String tr_note1 = "loop(E, C, '总计')";
        MiniLang lang = MiniLang.getInstance();
        String res1 = lang.exec(tr_note1, map);
        System.out.println(res1);
    }

    // add(row('总计',C,H), row('总计',C,I)
    @Test
    public void test019() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        List<String> p = new ArrayList<String>();
        p.add("1421374800902");
        p.add("1421374800903");
        map.put("batchNo", p);

        String tr_note1 = "add(row('总计',C,H), row('总计',C,I), 1)";
        MiniLang lang = MiniLang.getInstance();
        String res1 = lang.exec(tr_note1, map);
        System.out.println(res1);
    }

    @Test
    public void test021() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        // map.put("reportId", report);
        // List<String> p = new ArrayList<String>();
        // p.add("1421374800902");
        // p.add("1421374800903");
        // map.put("batchNo", p);

        String tr_note1 = "to_custom(000102054991070)";
        MiniLang lang = MiniLang.getInstance();
        String res1 = lang.exec(tr_note1, map);
        System.out.println(res1);
    }

    @Test
    public void test023() {
        String report = "JCTSaleSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1422432772331");
        map.put("index", 1);

        String tr_note1 = "to_custom(loop(B, A))";
        MiniLang lang = MiniLang.getInstance();
        String res1 = lang.exec(tr_note1, map);
        System.out.println(res1);
    }

    @Test
    public void test012() {
        String report = "JCTSaleSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1422494529353");
        map.put("index", 0);

        A3PluginAnalyseComponent apac = new A3PluginAnalyseComponent();
        String s = "date_format(rowCol(A,4), 'yyyyMMdd', 'MM.dd')日实名VIP城通标准卡（loop(D, A, '合计')）充值日报";
        System.out.println(apac.callMiniLang(s, map));
    }

    @Test
    public void test014() throws Exception {
        MiniLangParse parse = new MiniLangParse();
        System.out.println(parse.parse("总（）add(总row('总计',CD,H), row('总计',C,I), 1111)"));
    }

    @Test
    public void test016() {
        String report = "JCTSaleSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1422494529353");
        map.put("index", 0);

        A3PluginAnalyseComponent apac = new A3PluginAnalyseComponent();
        String s = "date_format(rowCol(A,4), 'yyyyMMdd', 'MM.dd')日实名VIP城通标准卡（loop(D, A, '合计')）充值日报";
        System.out.println(apac.callMiniLang(s, map));
    }

    @Test
    public void test025() {
        String report = "RealVIPSaleCheckDaily";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1423017780139");
        map.put("index", 3);

        String tr_note = "loop(A,A,'合计')";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(tr_note, map);
        System.out.println(res);
    }

    @Test
    public void test018() {
        String str = "yesterday('yyyy-MM-dd')";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(str, new HashMap<String, Object>());
        System.out.println(res);
    }

    @Test
    public void test020() {
        String str = "split(yesterday('yyyy-MM-dd'), 8, 10)";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(str, new HashMap<String, Object>());
        System.out.println(res);
    }

    @Test
    public void test022() {
        List<String> ls1 = new ArrayList<String>();
        ls1.add("ls11");
        ls1.add("ls12");
        ls1.add("ls13");
        List<String> ls2 = new ArrayList<String>();
        ls2.add("ls21");
        ls2.add("ls22");
        ls2.add("ls23");

        List<List<String>> data = new ArrayList<List<String>>();
        data.add(ls1);
        data.add(ls2);

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("excelInData", data);

        String str = "cell(A,2)";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(str, context);
        System.out.println(res);
    }

    @Test
    public void test027() {
        String report = "MerchantSettleReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1420797401948");

        // String tr_note = "add(add(add('1', '2'), '3'), '4')";
        String tr_note = "subtract(0, 3)";

        // String tr_note = "add('5', '2')";
        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(tr_note, map);
        System.out.println(res);
    }

    @Test
    public void test024() {
        String method = "blank(1)";

        MiniLang lang = MiniLang.getInstance();
        String res = lang.exec(method, new HashMap<String, Object>());
        System.out.println("|" + res + "|");
    }

    @Test
    public void test026() {
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            l.add(String.valueOf(i));
        }
        for (String s : l) {
            String report = "CustomerServiceForBadCardInCard_Artificial";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("reportId", report);
            map.put("batchNo", "1424151390546");
            map.put("index", s);

            String method = "scan(G, E, '小计')";

            MiniLang lang = MiniLang.getInstance();
            try {
                String res = lang.exec(method, map);
                System.out.println("|" + res + "|");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Test
    public void test028() {
        String report = "CustomerServiceForBadCardInCard_Artificial";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1424151390546");
        map.put("index", "30");

        String method = "scan(G, E, '小计')";

        MiniLang lang = MiniLang.getInstance();
        try {
            String res = lang.exec(method, map);
            System.out.println("|" + res + "|");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test029() {
        String report = "GoodCardBackCardReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1424157850132");
        map.put("index", "0");

        String method = "to_dept(loop(D, C, '总计', ''))";

        MiniLang lang = MiniLang.getInstance();
        try {
            String res = lang.exec(method, map);
            System.out.println("|" + res + "|");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test030() {
        String report = "GoodCardBackCardReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1424157850132");
        map.put("index", "9");

        String method = "index_move(D, -1)";

        MiniLang lang = MiniLang.getInstance();
        try {
            String res = lang.exec(method, map);
            System.out.println("|" + res + "|");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test032() {
        String report = "GoodCardBackCardReport";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", report);
        map.put("batchNo", "1424157850132");
        map.put("index", "0");

        String method = "to_sparegoldcode('040201301')";

        MiniLang lang = MiniLang.getInstance();
        try {
            String res = lang.exec(method, map);
            System.out.println("|" + res + "|");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test034() {
        Map<String, Object> map = new HashMap<String, Object>();

        String method = "get_network_name()";
        MiniLang lang = MiniLang.getInstance();

        try {
            String res = lang.exec(method, map);
            System.out.println("|" + res + "|");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test036() {
        Map<String, Object> map = new HashMap<String, Object>();

        String method = "get_network_orgnid()";
        MiniLang lang = MiniLang.getInstance();

        try {
            String res = lang.exec(method, map);
            System.out.println("|" + res + "|");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test038() {
        Map<String, Object> map = new HashMap<String, Object>();

        String method = "to_networka3note('友谊路客服网点')";
        MiniLang lang = MiniLang.getInstance();

        try {
            String res = lang.exec(method, map);
            System.out.println("|" + res + "|");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
