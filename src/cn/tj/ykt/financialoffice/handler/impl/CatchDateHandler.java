package cn.tj.ykt.financialoffice.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：抓取Excel文件，备份Excel文件
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class CatchDateHandler extends DefaultHandler {

    public static final int cache = 10 * 1024;

    @Override
    public String process(MessageBroker messageBroker) {

        LogUtil.logInfo("抓取Excel文件");
        // try {
        // String url = messageBroker.getUrl();
        // if (url == null) {
        // throw new IllegalArgumentException("url not null");
        // }
        // String storePath = messageBroker.getStoreFilePath();
        // if (storePath == null) {
        // throw new IllegalArgumentException("storePath not null");
        // }
        // String jsessionid = messageBroker.getJsessionid();
        // String cookieVal = "";
        // if (jsessionid != null) {
        // cookieVal = "JSESSIONID=" + jsessionid;
        // }
        //
        // HttpResponse response = Request.Get(url).addHeader("Cookie",
        // cookieVal).execute().returnResponse();
        //
        // HttpEntity entity = response.getEntity();
        // InputStream is = entity.getContent();
        //
        // File file = new File(storePath);
        // file.getParentFile().mkdirs();
        // FileOutputStream fileout = new FileOutputStream(file);
        //
        // byte[] buffer = new byte[cache];
        // int ch = 0;
        // while ((ch = is.read(buffer)) != -1) {
        // fileout.write(buffer, 0, ch);
        // }
        // is.close();
        // fileout.flush();
        // fileout.close();
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // return FAILURE;
        // }

        System.out.println("CatchDateHandler");

        return SUCCESS;
    }

}
