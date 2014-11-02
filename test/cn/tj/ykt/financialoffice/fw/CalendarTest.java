package cn.tj.ykt.financialoffice.fw;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class CalendarTest {

	@Test
	public void test001() {
		Calendar calendar = Calendar.getInstance();
         calendar.setTime(new Date());
         String str = calendar.toString();
         System.out.println(str);
	}
	
	
	@Test
	public void test002() {
		
		String value = "2014-10-12";
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		
        Date date;
        try {
            date = sdf.parse(value);
            
            System.out.println(sdf2.format(date));
            
        } catch(Exception e) {
        	
        }
            
	}
}
