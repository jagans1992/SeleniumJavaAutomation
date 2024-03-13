package com.coreJava.topics;

public class MultiDimensionArray {

	public static void main(String[] args) {
		minNumber();
		maxNumber();
		primeNumber();
		}

	public static void minNumber() {
		int[][] arr = {{9,4,6},{3,11,7}};
		int min = arr[0][0]; 
		for(int i=0; i<2; i++) {
			for(int j=0; j<3; j++) {
				if(arr[i][j] < min){
					min = arr[i][j];
				}			}	
		}
		System.out.println("Minimum Value= " + min);
	}
	
	public static void maxNumber() {
		int[][] arr = {{9,4,6},{3,11,7}};
		int min = arr[0][0]; 
		for(int i=0; i<2; i++) {
			for(int j=0; j<3; j++) {
				if(arr[i][j] > min){
					min = arr[i][j];
				}			}	
		}
		System.out.println("Maximum Value= " + min);
	}
	
	public static void primeNumber() {
		int[][] arr = {{9,4,6},{3,11,7}};
		for(int i=0; i<2; i++) {
			for(int j=0; j<3; j++) {
				if(arr[i][j] % 2 == 0){
					System.out.println("Prime Value= " + arr[i][j]);
				}			}	
		}
	}
}