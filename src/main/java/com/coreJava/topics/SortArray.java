package com.coreJava.topics;

public class SortArray {

	public static void main(String[] args) {
		int [] arr = {2,1,6,7,12,5,3,0};
		int temp;
		for (int i=0; i< arr.length; i++) {
			for(int j=i+1; j<arr.length; j++) {
				if(arr[i] > arr[j]) { // 6>7
					temp = arr[i]; // assign arr[i] to temp=6
					arr[i] = arr[j]; // assaign arr[j] to arr[i]=7
					arr[j] = temp; // assaign temp to arra[j]=6
				}
			}
		}
		for (int i=0; i<arr.length; i++) {
			System.out.println(arr[i]);
		}

	}

}
