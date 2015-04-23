package cn.tj.ykt.financialoffice.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.minilang.MiniLang;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：抓取Excel文件，备份Excel文件
 * -----------------网点系统
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class NetWorkCatchDate extends CatchDateHandler {

    public static final int cache = 10 * 1024;

    public static final String module = NetWorkCatchDate.class.getName();

    @Override
    public String process(MessageBroker messageBroker) {
        try {

            String baseUrl = messageBroker.getDownUrl();

            String storePath = messageBroker.getStorePath();
            storePath = storePath + getFileName(messageBroker.getReportId(), messageBroker.getBatchNo());

            Map<String, String> params = messageBroker.getExportQueryParams();

            // Map<String, String> params = new HashMap<String, String>();
            // params.put("box2-inputEl", "超长度网点一分一网点,+超长度网点联调一网点");
            // params.put("startTime", "2014-08-05");
            // params.put("endTime", "2015-02-16");
            // params.put("orgnId",
            // "41E36519BAC4456B92D3937F83BA0526,CC84D82E95E549CBB946A1E411515258");
            // params.put("userId", "6CA5859220C5494EA170A2BFB7E6BE50");
            // params.put("jobId", "1");
            // params.put("orgnId_login", "297D6E6AE48D40168051001063A28C27");
            // params.put("report", "16rwhuaikatuikahzb");

            miniLang = MiniLang.getInstance();
            methods = miniLang.getMiniLangMethods();

            /** 1.获取报表数据 */
            String url = baseUrl;
            Form form = Form.form();
            for (String qp : params.keySet()) {
                String val = params.get(qp);

                boolean f = false;
                for (String m : methods) {
                    if (val.indexOf(m) >= 0) {
                        f = true;
                        break;
                    }
                }
                if (f) {
                    val = miniLang.exec(val, new HashMap<String, Object>());
                }

                form.add(qp, val);
            }

            String ret = Request.Post(url).bodyForm(form.build(), Charset.forName("UTF-8")).execute().returnContent().asString();

            System.out.println(ret);

            String target1 = "function report1_saveAsExcel() {\r\n\t\tvar address = \"";
            String target2 = "document.getElementById( \"report1_saveAs_frame\" ).src = address;";

            int index1 = ret.indexOf(target1) + target1.length();
            int index2 = ret.indexOf(target2) - 6;
            /** 2.下载报表 */
            String downUrl = ret.substring(index1, index2);

            HttpResponse response = Request.Get(downUrl).execute().returnResponse();

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            messageBroker.setTempFilePath(storePath);
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

            return SUCCESS;
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

}
