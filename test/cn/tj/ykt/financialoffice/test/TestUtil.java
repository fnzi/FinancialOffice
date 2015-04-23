package cn.tj.ykt.financialoffice.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import cn.tj.ykt.financialoffice.fw.minilang.MiniLangUtil;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;
import cn.tj.ykt.financialoffice.system.service.BusinessReport;
import cn.tj.ykt.financialoffice.system.service.BusinessSystemReportServiceImpl;


public class TestUtil {

	@Test
    public void test1() {
        boolean sign = true; // 初始化标志为为'true'  
        String word = "AB";
        for (int i = 0; i < word.length(); i++) {  
            if ((word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')  
                    || (word.charAt(i) >= 'a' && word.charAt(i) <= 'z')) {  
                System.out.println(123);
            }  
        }  
//        System.out.println(125);
    }
	
	@Test
	public void test2() {
		/**
		char c = 'A';
		char cc = 'B';
		int i = (int)c;
		int j = (int)cc;
		System.out.println(i);
		System.out.println(j);
		**/
		String str = "AB";
		char[] cha=str.toCharArray();
		for(char s:cha){
			int ii = (int)s;
			System.out.println(s+"是"+ii);
		}
	}
	
	@Test
	public void test003() {
		Assert.assertEquals(0, MiniLangUtil.letter2Num("A"));
		System.out.println(MiniLangUtil.letter2Num("A"));
		
		Assert.assertEquals(25, MiniLangUtil.letter2Num("Z"));
		System.out.println(MiniLangUtil.letter2Num("Z"));
		
		Assert.assertEquals(26, MiniLangUtil.letter2Num("AA"));
		System.out.println(MiniLangUtil.letter2Num("AA"));
		
		Assert.assertEquals(702, MiniLangUtil.letter2Num("AAA"));
		System.out.println(MiniLangUtil.letter2Num("AAA"));
	}
	
	@Test
	public void test004(){
		String a = "4";
		String b = "5";
		
		if (Integer.parseInt(a) <= Integer.parseInt(b)) {
			System.out.println(123);
		} else {
			System.out.println(124);
		}
	}
	
	@Test
	public void test005(){
		String val = "20140212";
		
		String date = "";
		try {
			date = DateUtil.formatConversion(val, "yyyyMMdd", "MM.dd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(date);
	}

	@Test
	public void isChineseChar() {
		boolean temp = false;
		String str1 = "nihao";
		String str2 = "你好妹";
		
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
		Matcher m1 = p.matcher(str1);
		System.out.println(m1.matches());
		Matcher m2 = p.matcher(str2);
		System.out.println(m2.matches());

		if(m1.find()){
			temp =  true;
		}
		System.out.println(temp);
	}	

	@Test
	public void test006() throws ParseException {
//		NumberFormat nf= NumberFormat.getInstance(); 
//		System.out.println(nf.format("1,200.00"));
		
		DecimalFormat df1 = new DecimalFormat("#,###.000");
		System.out.println(df1.format(1000));
		
		System.out.println(df1.parse("111,222.000"));
		
		DecimalFormat df2 = new DecimalFormat("####.00");
		System.out.println(df2.format(df1.parse("111,222.000")));
		
	}
	
	@Test
	public void test007() {
		String word = "removeThousandCharacter";
    	List<String[]> numType = new ArrayList<String[]>();
    	
    	String[] remove1k = {"removeThousandCharacter", "#,###.00", "####.00"};
    	numType.add(remove1k);
    	
    	String[] add1k = {"addThousandCharacter", "####.00", "#,###.00"};
    	numType.add(add1k);
    	
    	List<String> p = new ArrayList<String>();

		for (String[] str : numType) {
			for(String s : str) {
				if(word.equals(s)) {
					p.add(str[1].toString());
					p.add(str[2].toString());
					break;
				}
			}
		}
		
		p.size();
		System.out.println(123);
	}
	
	@Test
	public void test008() {
		BusinessSystemReportServiceImpl cla1 = new BusinessSystemReportServiceImpl();
//		List<BusinessReport> list = cla1.getReports();
//		list.size();
		
		List<BusinessReport> sys = cla1.getReportsBySystem("xiaoe");
		sys.size();
		
		System.out.println(sys.size());
	}

	@Test
	public void test009() {
		String a = "20,912.73";
		a = a.replaceAll(",", "");
		String b = "15.00";
		b = b.replaceAll(",", "");
		
		BigDecimal a1 = new BigDecimal(a).setScale(2, 4);
		BigDecimal b1 = new BigDecimal(b).setScale(2, 4);
		
		BigDecimal sum = a1.add(b1);
		System.out.println(sum.toString());
		
	}

	@Test
	public void test011() {
		String a = "  aa ";
		System.out.println(a.trim());
		
	}

}
