package com.xiyoulinux.calculate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Calculate implements MathFunction {
	
//	用户输入计算的数学表达式
	public void input() {
		System.out.print(">>");
		Scanner input = new Scanner(System.in);
		originalExpression = input.nextLine();
		while (!checkError()) {
			input();
		}
		input.close();
	}
	
//	检查输入的数学表达式是否有错
	public boolean checkError() {
		return false;
	}
	
//	把原始数据中的求和，平均数等处理成数学式子。并且输入不规范的改成规范的（例如“.7”改为“0.7”）
	public List<String> toNormalExpression() {
		return null;
	}
	
//	生成新的一个List返回逆波兰表达式
	public List<String> toSuffixExpression(List<String> normalExpression) {
		getPriority("D:/priority.txt");
		return null;
	}
	
//	根据给定的逆波兰表达式，计算出最终结果
	public String calculate(List<String> suffixExpression) {
		return null;
	}
	
//	从文件中获取运算符的优先级
	private void getPriority(String filePath) {
		priority = new HashMap<String, Integer>();
		try {
			BufferedReader priorityFile = new BufferedReader(new FileReader(filePath));
			String EveryLineStr;
			
			//读取每一行
			while ((EveryLineStr = priorityFile.readLine()) != null) {
				String [] operatorAndPriority = EveryLineStr.split(" ");//取得空格前后的值
				priority.put(operatorAndPriority[0], Integer.valueOf(operatorAndPriority[1]));
				/*System.out.println(operatorAndPriority[0] + Integer.valueOf(operatorAndPriority[1]));*/
			}
			priorityFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String originalExpression;//原始用户输入的表达式
	private Map<String,Integer> priority;//运算符优先级，数字越小优先级越高，零代表优先级最高
}