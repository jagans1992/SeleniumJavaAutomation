package com.coreJava.topics;

public class ReverseNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = 546;
		int b =0;
		
		while(a != 0) {
			int c= a%10;
			b = c + b*10;
			a = a/10;
		}
		System.out.println(b);
	}
	


}
