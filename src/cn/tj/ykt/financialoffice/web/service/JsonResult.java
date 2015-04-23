package cn.tj.ykt.financialoffice.web.service;

/**
 * <pre>
 * 功能描述：Json服务类返回类型描述类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class JsonResult {

    private boolean success;
    private String msg;
    private Object data;

    public JsonResult() {
        super();
    }

    public JsonResult(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public JsonResult(boolean success, Object data) {
        super();
        this.success = success;
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
