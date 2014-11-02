package cn.tj.ykt.financialoffice.fw.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * <pre>
	 * 日期格式转换
	 * @param str
	 * @param formatBefore
	 * @param formatAfter
	 * @return
	 * </pre
	 * @throws Exception 
	 */
	public static String formatConversion(String str, String formatBefore, String formatAfter) throws Exception {
		SimpleDateFormat sdf_before = new SimpleDateFormat(formatBefore);
		SimpleDateFormat sdf_after = new SimpleDateFormat(formatAfter);
		
        Date date;
        try {
            date = sdf_before.parse(str);
            return sdf_after.format(date);
        } catch(Exception e) {
        	 e.printStackTrace();
        	 throw e;
        }
	}
	
	public static String current(String format) throws Exception {
		 Date dt = new Date();
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat(format);
			 return sdf.format(dt);
		 } catch(Exception e) {
        	 e.printStackTrace();
        	 throw e;
        }
	}
}
