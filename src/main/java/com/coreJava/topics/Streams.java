//package com.coreJava.topics;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import org.junit.Assert;
//
//public class Streams {
//
//	public static void main(String[] args) {
////		removeDuplicates();
////		toUpperCase();
////		specificList();
////		mergeList();
//		uniqueNumbers();
//	}
//
//	public static void removeDuplicates() {
//		ArrayList<String> list = new ArrayList<>();
//		list.add("Jagan");
//		list.add("Priya");
//		list.add("Jagan");
//		list.add("Sara");
//		list.add("Jagan");
//		list.add("Diana");
//		list.add("Sara");
//		System.out.println("Method to Remove Duplicates");
//		System.out.println("Before Removing Duplicates" + list);
//		List<Object> Actual = list.stream().distinct().collect(Collectors.toList());
//		System.out.println("After Removing Duplicates" + Actual);
//
//	}
//
////	public static void toUpperCase() {
////
////		ArrayList<String> list = new ArrayList<>();
////		list.add("Jagan");
////		list.add("Priya");
////		list.add("Jagan");
////		list.add("Sara");
////		list.add("Jagan");
////		list.add("Diana");
////		list.add("Sara");
////		System.out.println("Method to change list to UpperCase");
////		System.out.println("Before performing Streams" + list);
////		List<Object> Actual = list.stream().sorted() // sort the array
////				.distinct() // remove Duplicates
////				.map(s -> s.toUpperCase()) // UpperCase
////				.collect(Collectors.toList()); // add to new list
////		System.out.println("After performing Streams" + Actual);
////
////	}
//
//	public static void specificList() {
//
//		ArrayList<String> list = new ArrayList<>();
//		list.add("Jagan");
//		list.add("Priya");
//		list.add("Jagan");
//		list.add("Sara");
//		list.add("Jagan");
//		list.add("Diana");
//		list.add("Sara");
//		System.out.println("\t");
//		System.out.println("Method to change list to UpperCase");
//		System.out.println("Before performing Streams" + list);
//		// Get the count
//		long count = list.stream().filter(s -> s.startsWith("J")).count();
//		System.out.println("count= " + count);
//		// Get the list
//		list.stream().filter(s -> s.length() >= 3).forEach(s -> System.out.println(s));
//		// Limit
//		list.stream().filter(s -> s.length() >= 4).limit(1).forEach(s -> System.out.println(s));
//
//	}
//
//	public static void mergeList() {
//
//		ArrayList<String> list1 = new ArrayList<>();
//		list1.add("Jagan");
//		list1.add("Priya");
//		list1.add("Jagan");
//		list1.add("Sara");
//		list1.add("Jagan");
//		list1.add("Diana");
//		list1.add("Sara");
//
//		ArrayList<String> list2 = new ArrayList<>();
//		list2.add("Don");
//		list2.add("Babu");
//		list2.add("Arnold");
//		list2.add("Akon");
//		System.out.println("\t");
//		Stream<String> newStream = Stream.concat(list1.stream(), list2.stream());
////		newStream.sorted().forEach(s->System.out.println(s));
//		boolean value = newStream.anyMatch(s -> s.equalsIgnoreCase("Sara"));
//		Assert.assertTrue(value);
//
//	}
//
//	public static void uniqueNumbers() {
//		List<Integer> num = Arrays.asList(2, 1, 3, 5, 6, 7, 9, 0, 1, 2, 11, 13, 14, 14);
//		num.stream().distinct().sorted().forEach(s -> System.out.println(s));
//		List<Integer> value = num.stream().distinct().sorted().collect(Collectors.toList());
//		System.out.println("Index of 5= " + value.get(5));
//	}
//}
