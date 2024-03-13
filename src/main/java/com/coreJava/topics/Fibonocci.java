package com.coreJava.topics;

public class Fibonocci {

	public static void main(String[] args) {
		//Test
		int a = 0;
		int b = 1;
		int value = 0;
		int sum = 0;
		while (sum < 9) {
			value = a + b;
			System.out.println(value);
			a = b;
			b = value;
			sum++;
		}

	}

}
