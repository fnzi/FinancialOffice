package cn.tj.ykt.financialoffice.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.ExportQueryData;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.context.ConfigurationContext;
import cn.tj.ykt.financialoffice.system.context.XmlContext;

public class CatchDateHandlerTest {

    public static final int cache = 10 * 1024;

    @Test
    public void test001() {
        try {
            String url = "http://172.22.30.101/umps_tj/MerchantSettleDailyQuery.do?merchant_noQ=&merchant_name=&begin_dateQ=2015-01-21&end_dateQ=2015-01-21&hztype=&cycletype=&merchant_class=&cooperationType=&card_class=&currentPage=1&pageSize=10&totalPage=0&exportFlag=1";
            String storePath = "D:\\test\\temp.xls";

            String cookieVal = "JSESSIONID=34C967CB3E93DB23491481637F0207F1";

            HttpResponse response = Request.Get(url).addHeader("Cookie", cookieVal).execute().returnResponse();

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            File file = new File(storePath);
            file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);

            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test002() {
        try {
            String url = "http://172.16.4.165:8088/jor_report/jatoolsreport?_action_type=export&do_export=1&_job_session_id=jt17f6f002&as=xls";
            String storePath = "D:\\test\\temp.xls";

            String cookieVal = "JSESSIONID=39A8CDB55964F021CC71B8FD3E4BDA15";

            HttpResponse response = Request.Get(url).addHeader("Cookie", cookieVal).execute().returnResponse();

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            File file = new File(storePath);
            file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);

            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test004() {
        try {
            String url = "http://www.baidu.com/";
            String storePath = "D:\\test\\temp.xls";

            String cookieVal = "JSESSIONID=39A8CDB55964F021CC71B8FD3E4BDA15";

            String ret = Request.Get(url).addHeader("Cookie", cookieVal).execute().returnContent().asString();

            String target = "<input type=\"submit\" id=\"su\" value=\"百度一下\" class=\"bg s_btn\">";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test006() {
        String target1 = "id=\"su\" value=\"";
        String target2 = "\" class=\"bg s_btn\"";

        String s = "<input type=\"submit\" id=\"su\" value=\"百度一下\" class=\"bg s_btn\"></span><span class=\"tools\">";

        int index1 = s.indexOf(target1);
        int index2 = s.indexOf(target2);

        System.out.println(s.substring(index1 + target1.length(), index2));
    }

    @Test
    public void test008() {
        try {
            Request.Post("http://172.16.4.165:8088/openreports/login.action").bodyForm(Form.form().add("userName", "admin").add("password", "admin").build())
                    .execute().returnResponse();

            // Header header = response.getFirstHeader("Set-Cookie");
            // if (header != null) {
            // String setCookie = header.getValue();
            // String jsessionid = setCookie.substring("JSESSIONID=".length(),
            // setCookie.indexOf(";"));
            // System.out.println(jsessionid);
            // }

            String url = "http://172.16.4.165:8088/jor_report/reportviewer";

            Form form = Form.form();
            form.add("file", "D:\\reports\\\\充值明细报表.xml");
            form.add("p_net_id", "");
            form.add("p_net_desc", "");
            form.add("p_city_cardfaceno", "");
            form.add("p_newcard_no", "");
            form.add("p_jiaoyi_type", "");
            form.add("p_card_type", "");
            form.add("dojo.p_start_date", "");
            form.add("p_start_date", "");
            form.add("dojo.p_end_date", "");
            form.add("p_end_date", "");
            form.add("submitType", "查询");
            form.add("reportId", "133");
            form.add("step", "0");
            form.add("driver", "oracle.jdbc.driver.OracleDriver");
            form.add("url", "jdbc:oracle:thin:@172.16.4.165:1521:xe");
            form.add("user", "cm1");
            form.add("pwd", "d2FuZw==");
            form.add("displayInline", "false");

            String ret = Request.Post(url).bodyForm(form.build(), Charset.forName("UTF-8")).execute().returnContent().asString();

            String target1 = "jobSessionId='";
            String target2 = "' style='display:none'";

            int index1 = ret.indexOf(target1);
            int index2 = ret.indexOf(target2);

            String jobSessionId = ret.substring(index1 + target1.length(), index2);
            System.out.println(jobSessionId);

            String downUrl = "http://172.16.4.165:8088/jor_report/jatoolsreport?_action_type=export&do_export=1&_job_session_id=" + jobSessionId + "&as=xls";
            String storePath = "D:\\test\\temp.xls";

            HttpResponse response = Request.Get(downUrl).execute().returnResponse();

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            File file = new File(storePath);
            file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);

            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test010() {
        try {
            String downUrl = "http://172.22.30.101/umps3/MerchantSettleDailyQuery.do?merchant_noQ=&merchant_name=&begin_dateQ=2015-01-29&end_dateQ=2015-01-29&hztype=1&cycletype=1&merchant_class=&cooperationType=&card_class=&currentPage=1&pageSize=10&totalPage=0&exportFlag=1";
            String storePath = "D:\\test\\temp.xls";

            HttpResponse response = Request.Get(downUrl).execute().returnResponse();

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            File file = new File(storePath);
            file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);

            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test012() {
        MessageBroker messageBroker = new MessageBroker();
        String reportId = "test_report";

        ExportQueryData exportQueryData = null;

        ConfigurationContext context = XmlContext.getContext();
        List<ExportQueryData> exportQueryDatas = context.getConfigurations().getExportQueryData();
        for (ExportQueryData eqd : exportQueryDatas) {
            if (eqd.getReportId().equals(reportId)) {
                exportQueryData = eqd;
            }
        }

        messageBroker.setUsername(exportQueryData.getUsername());
        messageBroker.setPassword(exportQueryData.getPassword());

        Map<String, String> map = exportQueryData.getQuerys();
        map.put("reportId", reportId);

        Map<String, String> params = new HashMap<String, String>();

        if (!map.keySet().contains("way")) {
            throw new SystemException("日报导出<way>结点必须配置");
        } else if (!map.keySet().contains("storePath")) {
            throw new SystemException("日报导出<storePath>结点必须配置");
        } else if (!map.keySet().contains("reportId")) {
            throw new SystemException("日报导出<reportId>结点必须配置");
        } else if (!map.keySet().contains("downUrl")) {
            throw new SystemException("日报导出<downUrl>结点必须配置");
        }

        for (Object key : map.keySet()) {
            String p = String.valueOf(key);
            if (p.equals("way")) {
                messageBroker.setWay(map.get(p));
            } else if (p.equals("storePath")) {
                messageBroker.setStorePath(map.get(p));
            } else if (p.equals("reportId")) {
                messageBroker.setReportId(map.get(p));
            } else if (p.equals("downUrl")) {
                messageBroker.setDownUrl(map.get(p));
            } else {
                params.put(p, map.get(p));
            }
        }

        messageBroker.setExportQueryParams(params);

        CatchDateHandler handler = new CatchDateHandler();

        handler.process(messageBroker);
    }

    @Test
    public void test014() {
        CatchDateHandler catchDateHandler = new NetWorkCatchDate();
        catchDateHandler.process(new MessageBroker());
    }
}
