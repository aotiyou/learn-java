package com.itranswarp.learnjava;

import java.io.StringBufferInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {
	public static void main(String[] args) {
		hexTest();
		String exp = "x + 2 * (y - 5)";
		SuffixExpression se = compile(exp);
		Map<String, Integer> env = Map.of("x", 1, "y", 9);
		int result = se.execute(env);
		System.out.println(env);
		System.out.println(exp + " = " + result + " " + (result == 1 + 2 * (9 - 5) ? "✓" : "✗"));
	}

	static SuffixExpression compile(String exp) {
		// TODO:
		// 定义运算符的优先级别
		Map<String, Integer> expPriority = Map.of("+", 1, "-", 1, "*", 2, "/", 2, "(", 0, ")", 0);

		// 将表达式的数值和运算符通过正则匹配出来
		String expReg = "\\d+|[\\+\\-\\*\\/\\(\\)]";
		Matcher expMatcher = Pattern.compile(expReg).matcher(exp);

		// 定义后缀表达式栈和运算符栈
		// 后缀表达式栈存储最终得到的后缀表达式栈
		// 运算符栈存储算法过程中，优先级为不存在括号等改变优先级别的运算符
		// 优先级别一旦被改变，则将符号转移到后缀表达式栈
		Deque<String> suffixExpStack = new LinkedList<>();
		Deque<String> operateStack = new LinkedList<>();

		while(expMatcher.find()) {
			String current = expMatcher.group();

			// 如果是左括号，或操作符栈为空，则将当前运算符存入运算符栈，作为在右括号前的所有符号标识
			if ("(".equals(current) || operateStack.isEmpty()) {
				operateStack.push(current);
				continue;
			}

			// 如果是右括号，则将操作符栈的操作符弹出
			// 压入后缀表达式栈，直到遇到操作符栈的左括号标识
			if (")".equals(current)) {
				String temp = operateStack.pop();
				while (temp != null && !temp.equals("(")) {
					suffixExpStack.push(temp);
					temp = operateStack.pop();
				}
				continue;
			}

//			// 数值直接存入后缀表达式栈，并进入下一次循环
//			if (!expPriority.containsKey(current)) {
//				suffixExpStack.push(current);
//				continue;
//			}

			// 如果是非左括号和右括号的运算符
			// 比较运算符的栈顶运算符优先级别与当前优先级别
			// 如果当前优先级别高，则可以压入操作符栈
			// 如果当前优先级别低，则压入后缀表达式栈
			String topOperate = operateStack.peek();
			if (expPriority.get(topOperate) <= expPriority.get(current)) {
				operateStack.push(current);
			} else {
				topOperate = operateStack.pop();
				operateStack.push(current);
				suffixExpStack.push(topOperate);
			}
		}

		// 将运算符栈剩余的运算符压入后缀表达式
		while(!operateStack.isEmpty()) {
			suffixExpStack.push(operateStack.pop());
		}

		return new SuffixExpression(suffixExpStack);
	}



	// 利用Stack把一个给定的整数转换为十六进制：
	public static void hexTest(){
		String hex = toHex(12500);
		if(hex.equalsIgnoreCase("30D4")){
			System.out.println("测试通过");
		}else {
			System.out.println("测试失败");
		}
	}

	static String toHex(int n) {
		if(n == 0){
			return "0";
		}
//		Deque<Character> stack = new ArrayDeque();
//		char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		Deque<String> stack = new LinkedList();
		while (n != 0){
//			stack.push(hexChars[n % 16]);
			stack.push(Integer.toHexString(n % 16));
			n = n / 16;
		}
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()){
			sb.append(stack.pop());
		}
		return sb.toString();
	}
}

class SuffixExpression {
	Deque<? extends String> suffix;

	public SuffixExpression(Deque<? extends String> suffix){
		this.suffix = suffix;
	}

	int execute(Map<String, Integer> env) {
		// TODO:
		Deque<Integer> resultStack = new LinkedList<>();
		System.out.println(suffix.toString());
		while (!this.suffix.isEmpty()) {
			// 因为后缀表达式压栈是从中缀表达式头部到尾部，所以计算需要从栈底开始取值
			String value = this.suffix.pollLast();

			// 如果是数字，直接压入结果栈。
			if(value.matches("^[0-9]+$")){
				resultStack.push(Integer.valueOf(value));
				continue;
			}

			// 运算符，则取栈顶两个元素进行运算
			int val1 = resultStack.pop();
			int val2 = resultStack.pop();
			resultStack.push(operate(val2, val1, value));
		}
		return resultStack.pop();
	}

	public int operate(int val1, int val2, String operate){
		switch (operate) {
			case "+": return val1 + val2;
			case "-": return val1 - val2;
			case "*": return val1 * val2;
			case "/": return val1 / val2;
			default: throw new IllegalArgumentException("运算符不正确！");
		}
	}
}


