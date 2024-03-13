package com.coreJava.topics;

public class OverriddingParent {

	String name = "Parent";
	
	public OverriddingParent() {
		System.out.println("Parent Constructor");
	}

	public void gearMethod() {
		System.out.println("Gear from Parent");
	}

	public void breakMethod() {
		System.out.println("Break from Parent");
	}
}
