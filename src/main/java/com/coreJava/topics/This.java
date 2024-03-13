package com.coreJava.topics;

public class This {

	int a = 4;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		This data = new This();
		data.testData();
	}

	public void testData() {
		int a = 2;
		int b = a + this.a;
		System.out.println("A Value inside Method = " + a);
		System.out.println("A Value Outside Method = " + this.a);
		System.out.println("B Value for a + this.a = " + b);

	}

}
