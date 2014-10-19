package cn.tj.ykt.financialoffice.kernel.internal.message;

import java.io.File;
import java.util.List;

public class MessageBroker {

    // -----------------------------------
    /** 报表唯一标示 */
    private String reportId;
    /** 报表临时文件的路径 */
    private String tempFilePath;
    /** excel中解析的有效数据 */
    private List<List<String>> data;

    /** 有效行 */
    private Integer row;
    /** 有效列 */
    private Integer col;

    // -----------------------------------

    private String authUrl;

    private String jsessionid;

    private String url;
    private String tempFileName;

    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    public String getStoreFilePath() {
        return tempFilePath + File.separator + tempFileName;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

}
