package cn.tj.ykt.financialoffice.fw.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.tj.ykt.financialoffice.fw.helper.LogUtil;

/**
 * <pre>
 * 功能描述：excel文件解析处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class ExcelUtil {

    public static List<List<String>> analyseExcel(String url) throws Exception {
        List<List<String>> file = new ArrayList<List<String>>();

        InputStream is = null;
        Workbook workbook = null;
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        try {
            is = new FileInputStream(url);

            if (url.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else {
                workbook = new XSSFWorkbook(is);
            }
            sheet = workbook.getSheetAt(0);

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                row = sheet.getRow(rowIndex);

                List<String> listCell = new ArrayList<String>();

                if (null == row) {
                    continue;
                }

                for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                    cell = row.getCell((int) columnIndex);
                    if (null != cell) {
                        listCell.add(cell.toString().trim());
                    } else {
                        listCell.add("");
                    }
                }

                file.add(listCell);
            }
        } catch (FileNotFoundException e) {
            LogUtil.logError("导入模板文件没有找到！\n" + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            LogUtil.logError("文件导入异常\n" + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            LogUtil.logError("Excel解析出错\n" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (null != is) {
                is.close();
            }
        }
        return file;
    }
}
