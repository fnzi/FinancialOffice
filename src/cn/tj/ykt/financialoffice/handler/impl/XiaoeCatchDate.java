package cn.tj.ykt.financialoffice.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.minilang.MiniLang;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：抓取Excel文件，备份Excel文件
 * -----------------小额平台
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class XiaoeCatchDate extends CatchDateHandler {

    public static final int cache = 10 * 1024;

    @Override
    public String process(MessageBroker messageBroker) {
        try {

            miniLang = MiniLang.getInstance();
            methods = miniLang.getMiniLangMethods();

            LogUtil.logInfo("小额平台日报下载：" + messageBroker.getReportId());
            Map<String, String> params = messageBroker.getExportQueryParams();
            String url = messageBroker.getDownUrl();
            String storePath = messageBroker.getStorePath();
            storePath = storePath + getFileName(messageBroker.getReportId(), messageBroker.getBatchNo());

            String downUrl = url + "?";
            String qps = "";

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

                qps = qps + "&" + qp + "=" + val;
            }

            downUrl = downUrl + qps.substring(1);

            LogUtil.logInfo("url:" + downUrl);
            LogUtil.logInfo("storePath:" + storePath);
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
