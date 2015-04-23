package cn.tj.ykt.financialoffice.kernel.internal.message;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.cfg.Header;

/**
 * <pre>
 * 功能描述：内核结点数据传输描述类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class MessageBroker {

    // -----------------------------------
    /** 报表唯一标示 */
    private String reportId;

    /** 获取日报名称 */
    private String reportName;

    /** 报表临时文件的路径 */
    private String tempFilePath;
    /** excel中解析的有效数据 */
    private List<List<String>> data;

    /** 表名 */
    private String tableName;
    /** 字段列 */
    private Map<String, Column> columns;
    // -----------------------------------

    private String authUrl;

    private String jsessionid;

    private String url;
    private String tempFileName;

    private String returnValue;

    // -----------------------------------
    /**
     * A3凭证配置信息
     */
    private List<Gl_Trntm> glTrntms;

    // -----------------------------------
    /**
     * <pre>
     * 运行时变量
     * 标记同一批次同步的数据
     * </pre>
     */
    private String batchNo;
    /**
     * <pre>
     * 运行时变量
     * 标记有效数据开始行
     * </pre>
     */
    private int dataLine;
    /**
     * excel模板header
     */
    private List<Header> headers;

    /** 报表下载查询参数 */
    private Map<String, String> exportQueryParams;
    private String downUrl;
    private String way;
    private String storePath;

    private String username;
    private String password;

    /** excel中解析data */
    private List<List<String>> excelInData;

    /** 凭证编号 */
    private String trNum;

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public List<Gl_Trntm> getGlTrntms() {
        return glTrntms;
    }

    public void setGlTrntms(List<Gl_Trntm> glTrntms) {
        this.glTrntms = glTrntms;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public int getDataLine() {
        return dataLine;
    }

    public void setDataLine(int dataLine) {
        this.dataLine = dataLine;
    }

    public Map<String, String> getExportQueryParams() {
        return exportQueryParams;
    }

    public void setExportQueryParams(Map<String, String> exportQueryParams) {
        this.exportQueryParams = exportQueryParams;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<List<String>> getExcelInData() {
        return excelInData;
    }

    public void setExcelInData(List<List<String>> excelInData) {
        this.excelInData = excelInData;
    }

    public String getTrNum() {
        return trNum;
    }

    public void setTrNum(String trNum) {
        this.trNum = trNum;
    }

}
