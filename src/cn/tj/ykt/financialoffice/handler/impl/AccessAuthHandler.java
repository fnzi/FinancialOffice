package cn.tj.ykt.financialoffice.handler.impl;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：验证获取登录权限
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class AccessAuthHandler extends DefaultHandler {

    @Override
    public String process(MessageBroker messageBroker) {

        LogUtil.logInfo("验证获取登录权限");
        // try {
        // String url = messageBroker.getAuthUrl();
        // if (url != null) {
        // HttpResponse response = Request.Get(url).execute().returnResponse();
        //
        // Header header = response.getFirstHeader("Set-Cookie");
        // if (header != null) {
        // String setCookie = header.getValue();
        // String jsessionid = setCookie.substring("JSESSIONID=".length(),
        // setCookie.indexOf(";"));
        // messageBroker.setJsessionid(jsessionid);
        // }
        //
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        System.out.println("AccessAuthHandler");
        return null;
    }

}
