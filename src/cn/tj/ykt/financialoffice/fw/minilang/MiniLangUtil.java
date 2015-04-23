package cn.tj.ykt.financialoffice.fw.minilang;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

public class MiniLangUtil {

	/**
	 * 校验字符串是否为英文字母
	 * @param word
	 * @return
	 */
	public static boolean strIsEnglish(String word) {
        for (int i = 0; i < word.length(); i++) {  
            if (!(word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')  
                    && !(word.charAt(i) >= 'a' && word.charAt(i) <= 'z')) {  
            	return false;  
            }  
        }  
		return true;
	}
	
	/**
	 * 字母转换ascii（列）
	 * @param word
	 * @return
	 */
	public static int letter2Num(String word) {
		double num = 0;

		char base = 'A';
		String wordUpper = word.toUpperCase();

		char[] cha = wordUpper.toCharArray();
		int len = cha.length;

		for (int i = len - 1; i >= 0; i--) {
			double diff = cha[i] - base + 1;
			diff = diff * Math.pow(26, i);
			num = num + diff;
		}

		num -= 1;

		return (int) num;
	}
	
	/**
	 * 有效行/列比较
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compareByNum(String str1, String str2) {
		if (Integer.parseInt(str1) <= Integer.parseInt(str2)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 取得正确的数据行/列
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int diffByNum(String str1, String str2) {
		int diff = 0;
		
		diff = Integer.parseInt(str1) - Integer.parseInt(str2);
		
		return diff;
	}
	
	/**
	 * 校验中文
	 * @param word
	 * @return
	 */
	public static boolean isChineseChar(String word) {
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
		return pattern.matcher(word).matches();
	}
	
	/**
	 * 校验数字
	 * @param word
	 * @return
	 */
	public static boolean isNumeric(String word){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(word).matches();
	}

	/**
	 * 
	 * @param word
	 * @param formatBefore
	 * @param formatAfter
	 * @return
	 * @throws ParseException
	 */
	public static String digitalConversion (String word, String formatBefore, String formatAfter) throws ParseException {
		String num = "";
		
		System.out.println(word);
		DecimalFormat df1 = new DecimalFormat(formatBefore);
		DecimalFormat df2 = new DecimalFormat(formatAfter);
		num = df2.format(df1.parse(word));

		return num;
	}
}
