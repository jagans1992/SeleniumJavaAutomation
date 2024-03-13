package com.coreJava.topics;

public class Overloading {

/* overloading is a concept where you can define multiple methods in a class with the same name 
 * but with different parameter lists. This allows you to create methods that perform similar tasks but with different inputs. 
 * Overloading is particularly useful when you want to provide flexibility and convenience to users of your class, 
 * allowing them to call methods with different types or numbers of parameters while still maintaining a coherent interface.*/
	
	public static void main(String[] args) {
		Overloading object = new Overloading();
		System.out.println(object.methodOverloading("Jagan"));
		System.out.println(object.methodOverloading("Jagan ", "Priya"));

	}
	
	public String methodOverloading(String data) {
		return data;
	}
	
	public String methodOverloading(String data, String data2) {
		return data + data2;
	}
}
