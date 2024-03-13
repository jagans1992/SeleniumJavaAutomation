package com.coreJava.topics;

public class WhileDemo {

	public static void main (String[] args) {
		System.out.println("While Loop");
		whileLoop();
		System.out.println("Do While Loop");
		doWhileLoop();
	}
	
	public static void whileLoop() {
		int i=10;
		
		while(i>0) {
			System.out.println(i);
			i--;
		}
	}
	
	public static void doWhileLoop() {
		// In do While loop 1 loop of execution guarantee 
		int i =9;
		do {
			System.out.println(i);
			i--;
		}while(i>5);
	}
}
