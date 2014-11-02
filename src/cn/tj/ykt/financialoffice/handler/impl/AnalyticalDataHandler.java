package cn.tj.ykt.financialoffice.handler.impl;

import java.util.ArrayList;
import java.util.List;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;
import cn.tj.ykt.financialoffice.fw.util.ExcelUtil;
import cn.tj.ykt.financialoffice.kernel.internal.handler.DefaultHandler;
import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;

/**
 * <pre>
 * 功能描述：解析获取Excel中data
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class AnalyticalDataHandler extends DefaultHandler {

    @Override
    public String process(MessageBroker messageBroker) {

        LogUtil.logInfo("解析获取Excel中data");

        String filePath = messageBroker.getTempFilePath();
        if (filePath == null || "".equals(filePath)) {
            throw new SystemException("file Path 必须赋值");
        }

        Integer row = messageBroker.getRow();
        if (row == null) {
            throw new SystemException("row 必须赋值");
        }

        Integer col = messageBroker.getCol();
        if (col == null) {
            throw new SystemException("col 必须赋值");
        }

        try {
            List<List<String>> excelInData = ExcelUtil.analyseExcel(filePath);
            // 有效data
            List<List<String>> data = new ArrayList<List<String>>();

            if (excelInData.isEmpty()) {
                throw new SystemException("excel行解析失败");
            }

            next: for (int i = row; i < excelInData.size(); i++) {

                List<String> line = excelInData.get(i);
                if (line.isEmpty()) {
                    throw new SystemException("excel列解析失败");
                }

                List<String> record = new ArrayList<String>();

                // 排除总计数据
                for (int j = col; j < line.size(); j++) {
                    if ("总计".equals(line.get(j).trim())) {
                        continue next;
                    }
                }

                for (int j = col; j < line.size(); j++) {
                    record.add(line.get(j));
                }
                data.add(record);
            }
            messageBroker.setData(data);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e.getMessage(), e);
        }
    }
}
