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
 * -----------------天津城市一卡通报表系统
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class YktReportCatchDate extends CatchDateHandler {

    public static final int cache = 10 * 1024;

    public static final String module = YktReportCatchDate.class.getName();

    @Override
    public String process(MessageBroker messageBroker) {
        try {

            String baseUrl = messageBroker.getDownUrl();

            String username = messageBroker.getUsername();
            String password = messageBroker.getPassword();

            String storePath = messageBroker.getStorePath();
            storePath = storePath + getFileName(messageBroker.getReportId(), messageBroker.getBatchNo());

            Map<String, String> params = messageBroker.getExportQueryParams();

            miniLang = MiniLang.getInstance();
            methods = miniLang.getMiniLangMethods();

            /** 1.获取访问权限 */
            Request.Post(baseUrl + "/openreports/login.action").bodyForm(Form.form().add("userName", username).add("password", password).build()).execute()
                    .returnResponse();

            /** 2.获取报表数据 */
            String url = baseUrl + "/jor_report/reportviewer";
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

            String target1 = "jobSessionId='";
            String target2 = "' style='display:none'";

            int index1 = ret.indexOf(target1);
            int index2 = ret.indexOf(target2);

            String jobSessionId = ret.substring(index1 + target1.length(), index2);
            LogUtil.logDebug(jobSessionId, module);

            /** 3.下载报表 */
            String downUrl = baseUrl + "/jor_report/jatoolsreport?_action_type=export&do_export=1&_job_session_id={0}&as=xls";
            downUrl = MessageFormat.format(downUrl, jobSessionId);

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
