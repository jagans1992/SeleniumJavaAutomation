package com.coreJava.topics;

public class OverriddingChild extends OverriddingParent{

	String name = "child"; // if we comment this the the Parent data will be called
	
	public OverriddingChild() {
		super(); // This should be alway be at first line
		System.out.println("Child Constructor");
	}
	// Overriding the Parent Method
	public void gearMethod() {
		System.out.println("Gear from Child");
		super.gearMethod();
	}
	
	// Method belongs to Child
	public void MusicMethod() {
		System.out.println("Music from Child");
	}
	
	// Overriding the Variable from Parent and Child 
	public void getVariable() {
		System.out.println("Name from Child is: " + name); 
		System.out.println("Name from Parent is: " + super.name);// Parent class Variable will be called
	}
	
	public static void main(String[] args) {
		OverriddingChild ch = new OverriddingChild();
		ch.gearMethod();
		ch.breakMethod();
		ch.MusicMethod();
		ch.getVariable();
	}
}
