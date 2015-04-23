package cn.tj.ykt.financialoffice.web.service.bean;

import java.io.OutputStream;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;

@SuppressWarnings("unchecked")
public class ExcelBean {

	protected HSSFWorkbook workbook;
	protected HSSFSheet sheet;
	protected int startX;
	protected int startY;
	protected int cX;
	protected int currentX;
	protected int currentY;
	protected int virtualY;
	protected HSSFRow currentRow;
	protected HSSFCell currentCell;
	protected String title;

	public ExcelBean(HSSFWorkbook workbook, HSSFSheet sheet, int startX,
			int startY, int cX, String title) {
		this.workbook = null;
		this.sheet = null;
		currentRow = null;
		currentCell = null;
		this.title = null;
		this.workbook = workbook;
		this.sheet = sheet;
		this.startX = startX;
		this.startY = startY;
		this.cX = cX;
		this.title = title;
		virtualY = 0;
		init();
	}

	protected void init() {
		currentX = startX - 1;
		currentY = startY - 1;
		if (title != null) {
			createRow();
			createCell(title);
			Region r = new Region(currentY, (short) currentX, currentY + 1,
					(short) ((currentX + cX) - 1));
			sheet.addMergedRegion(r);
			setFont("黑体", 16, (short) 700);
			setHAlign((short) 2);
			setVAlign((short) 1);
			currentY++;
		}
	}

	public void createOneRow(String rowname) {
		if (rowname != null) {
			createRow();
			createCell(rowname);
			Region r = new Region(currentY, (short) currentX, currentY,
					(short) ((currentX + cX) - 1));
			sheet.addMergedRegion(r);
			setFont("黑体", 8, (short) 700);
			setHAlign((short) 1);
			setVAlign((short) 1);
		}
	}

	public void createRow() {
		if (virtualY != 0) {
			currentY = virtualY + 1;
			virtualY = 0;
		} else {
			currentY++;
		}
		currentX = startX - 1;
		currentRow = sheet.createRow(currentY);
	}

	public void createCell(String value) {
		currentX++;
		currentCell = currentRow.createCell((short) currentX);
		//currentCell.setEncoding((short) 1);
		currentCell.setCellValue(value);
		currentCell.setCellType(1);
		createStyle();
	}

	public void createCell(String value, short h, short v) {
		createCell(value);
		setHAlign(h);
		setVAlign(v);
	}

	public void createMemo(String value, int cx, int cy) {
		createCell(value);
		Region r = new Region(currentY, (short) currentX, (currentY + cy) - 1,
				(short) ((currentX + cx) - 1));
		sheet.addMergedRegion(r);
		currentX += cx - 1;
		virtualY = (currentY + cy) - 1;
	}

	public void createStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		currentCell.setCellStyle(style);
	}

	public void setFont(String fontName, int size, short bold) {
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) size);
		font.setFontName(fontName);
		font.setBoldweight(bold);
		currentCell.getCellStyle().setFont(font);
	}

	public void setHAlign(short align) {
		currentCell.getCellStyle().setAlignment(align);
	}

	public void setVAlign(short align) {
		currentCell.getCellStyle().setVerticalAlignment(align);
	}

	public void setBackground(short color) {
		currentCell.getCellStyle().setFillBackgroundColor(color);
	}

	public void setForeground(short color) {
		currentCell.getCellStyle().setFillForegroundColor(color);
	}

	public void setFillPattern(short style) {
		currentCell.getCellStyle().setFillPattern(style);
	}

	public static void creatExcel(HttpServletResponse response, String title,
			String col_lable[], List datalist, String filename) {
		int colnum = col_lable.length;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("new sheet");
			ExcelBean excel = new ExcelBean(wb, sheet, 0, 0, colnum, title);
			excel.createRow();
			for (short cur_col = 0; cur_col < colnum; cur_col++) {
				excel.createCell(col_lable[cur_col]);
				excel.setHAlign((short) 2);
				excel.setVAlign((short) 1);
				excel.setFont("黑体", 12, (short) 700);
			}

			for (Iterator iterator = datalist.iterator(); iterator.hasNext();) {
				Object o = iterator.next();
				excel.createRow();
				if (o instanceof Map) {
					Map map = (Map) o;
					String value;
					for (Iterator iterator1 = map.keySet().iterator(); iterator1
							.hasNext(); excel.createCell(value)) {
						Object key = iterator1.next();
						value = map.get(key) != null ? map.get(key).toString()
								: "";
					}

				} else {
					Object obj[] = (Object[]) o;
					for (short cur_col = 0; cur_col < colnum; cur_col++)
						excel.createCell(obj[cur_col] != null ? obj[cur_col]
								.toString() : "");

				}
			}

			excel.putOut(response, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void creatExcel(HttpServletResponse response, String title,
			Map colMap, Map map, String filename) {
		int colnum = 2;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("new sheet");
			ExcelBean excel = new ExcelBean(wb, sheet, 0, 0, colnum, title);
			for (short cur_col = 0; cur_col < colnum; cur_col++) {
				excel.setHAlign((short) 2);
				excel.setVAlign((short) 1);
				excel.setFont("黑体", 12, (short) 700);
			}

			Object key;
			for (Iterator iterator = map.keySet().iterator(); iterator
					.hasNext(); excel.createCell(map.get(key) != null ? map
					.get(key).toString() : "")) {
				key = iterator.next();
				excel.createRow();
				String col = colMap.get(key) != null ? colMap.get(key)
						.toString() : "";
				excel.createCell(col);
			}

			excel.putOut(response, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void putOut(HttpServletResponse response, String FileName)
			throws Exception {
		response.setContentType("application/x-msdownload");
		response.setHeader("content-disposition", "attachment; filename="+new String(FileName.getBytes("GBK"),"iso-8859-1"));
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		os.close();
	}

	public static void main(String args[]) {
	}
}
