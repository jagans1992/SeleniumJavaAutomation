package com.coreJava.topics;

public class Pyramids {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		leftBottomPiramid();
		leftTopPiramid() ;
	}

	public static void leftBottomPiramid() {
		System.out.println("LEFT Bottom PIRAMID");
		for(int i=0; i<5; i++) {
			for(int j=5; j>i; j--) {
				System.out.print("*");
				System.out.print("\t");
			}
			System.out.println("");
		}
	}
	
	
	public static void leftTopPiramid() {
		System.out.println("LEFT TOP PIRAMID");
		for(int i=0; i<5; i++) {
			for(int j=0; j<=i; j++) {
				System.out.print("*");
				System.out.print("\t");
			}
			System.out.println("");
		}
	}
	
	public static void RightBottomPiramid() {
		System.out.println("LEFT Bottom PIRAMID");
		for(int i=0; i<5; i++) {
			for(int j=5; j>i; j--) {
				System.out.print("*");
				System.out.print("\t");
			}
			System.out.println("");
		}
	}
}
