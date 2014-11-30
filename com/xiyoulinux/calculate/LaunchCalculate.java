package com.xiyoulinux.calculate;

import java.util.List;

public class LaunchCalculate {

	public static void main(String[] args) {
		Calculate t = new Calculate();
		
		t.input();
		List<String> SuffixExpression = t.toSuffixExpression(t.toNormalExpression());
		String result = t.calculate(SuffixExpression);
		
		System.out.println("½á¹ûÊÇ:" + result);
	}

}
