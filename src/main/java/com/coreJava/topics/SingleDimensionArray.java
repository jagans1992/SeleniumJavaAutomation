package com.coreJava.topics;

import java.util.ArrayList;

public class SingleDimensionArray {

	public static void main(String[] args) {
		minNumber();
		maxNumber();
		arrayReverse();
	}

	public static void minNumber() {
		int a[] = { 7, 3, 5, 6, 11, 9 };
		int min = a[0];
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min) {
				min = a[i];
			}
		}
		System.out.println("Minimum Number= " + min);
	}

	public static void maxNumber() {
		int a[] = { 7, 3, 5, 6, 11, 9 };
		int max = a[0];
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) {
				max = a[i];
			}
		}
		System.out.println("Maximum Number= " + max);
	}

	public static void arrayReverse() {
		int a[] = { 7, 3, 5, 6, 11, 9 };
		ArrayList<Integer> rev = new ArrayList<Integer>();
		for (int i = a.length - 1; i > 0; i--) {
			rev.add(a[i]);
		}
		System.out.println("Array Reverse = "+ rev);
	}
}
