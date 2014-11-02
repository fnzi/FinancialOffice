package cn.tj.ykt.financialoffice.fw;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestRef {

	@Test
	public void test001() {
		List<String> l = new ArrayList<String>();
		
		TestRef tr = new TestRef();
		tr.put(l);
		tr.out(l);
		tr.out(l);
		tr.out(l);
		
	}
	
	private void put(List<String> i) {
		i.add("1");
		i.add("2");
	}
	
	private void out(List<String> i) {
		for(String s : i) {
			System.out.println(s);
		}
	}
}
