package cn.tj.ykt.financialoffice.fw;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NullTest {

	@Test
	public void test001() {
		List<String> l = new ArrayList<String>();
		for(String s : l) {
			System.out.println(s);
		}
	}
	
	@Test
	public void test002() {
		List<String> l = null;
		for(String s : l) {
			System.out.println(s);
		}
	}
}
