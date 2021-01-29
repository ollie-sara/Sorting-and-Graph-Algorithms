package test;

import sorting.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

class SortingTest {
	
	Sorter[] algos = new Sorter[] {new SelectionSort(),
								   new InsertionSort(),
								   new HeapSort(),
								   new QuickSort(),
								   new MergeSort()};
	
	@Test
	void binarySearch() {
		Sorter selSort = new SelectionSort();
		Integer[] array = new Integer[50];
		for(int i = 0; i < 100; i += 2) array[i/2] = i;
		
		int q1 = selSort.binarySearch(0, array, array.length);
		int q2 = selSort.binarySearch(5, array, array.length);
		int q3 = selSort.binarySearch(97, array, array.length);
		int q4 = selSort.binarySearch(-1, array, array.length);
		int q5 = selSort.binarySearch(1, array, array.length);
		assertEquals(0, q1);
		assertEquals(3, q2);
		assertEquals(49, q3);
		assertEquals(0, q4);
		assertEquals(1, q5);
	}
	
	/*
	 * Tests my all my algos for regular sorting
	 */
	@Test
	void regularSorting() {
		for(Sorter algo : algos) {
			System.out.println("Currently testing regular sorting for: " + algo.getClass().toString());
			long startTime = System.currentTimeMillis();
			
			// INTEGERS
			Integer[] unsortedInt = new Integer[100];
			Integer[] sortedInt = new Integer[100];
			fillArray(100, unsortedInt);
			for(int i = 0; i < 100; i++) sortedInt[i] = i;
			
			algo.sort(unsortedInt);
//			System.out.println(Arrays.toString(unsortedInt));
			assertArrayEquals(sortedInt, unsortedInt);
			
			// STRINGS
			String[] unsortedString = new String[] {"complaint", "recognition", "performance", "possession", "sample", "student", "manufacturer", "paper", "presence", "success", "music", "membership", "difficulty", "math", "conversation", "session", "alcohol", "strategy", "conclusion", "outcome"};									  
			String[] sortedString = new String[] {"alcohol", "complaint", "conclusion", "conversation", "difficulty", "manufacturer", "math", "membership", "music", "outcome", "paper", "performance", "possession", "presence", "recognition", "sample",  "session",  "strategy", "student", "success"};
			
			algo.sort(unsortedString);
			assertArrayEquals(sortedString, unsortedString);
			
			// LARGE TEST
			Integer[] unsortedIntLarge = new Integer[10000];
			Integer[] sortedIntLarge = new Integer[10000];
			fillArray(10000, unsortedIntLarge);
			for(int i = 0; i < 10000; i++) sortedIntLarge[i] = i;
			
			algo.sort(unsortedIntLarge);
			assertArrayEquals(sortedIntLarge, unsortedIntLarge);
			System.out.println("Time required: " + (System.currentTimeMillis()-startTime) + "ms");
		}
	}
	
	/*
	 * Tests special cases, like empty arrays or arrays with one element
	 */
	@Test
	void specialCases() {
		for(Sorter algo : algos) {
			System.out.println("Currently testing special cases for: " + algo.getClass().toString());
			
			// Empty array
			Integer[] emptyInteger = new Integer[] {};
			
			algo.sort(emptyInteger);
			assertArrayEquals(new Integer[] {}, emptyInteger);
			
			// Array with one element
			Integer[] oneInteger = new Integer[] {1};
			
			algo.sort(oneInteger);
			assertArrayEquals(new Integer[] {1}, oneInteger);
		}
	}
	
	/*
	 *  Fills an Integer Array randomly so that it can be sorted afterwards
	 */
	void fillArray(int n, Integer[] array) {
		ArrayList<Integer> toAdd = new ArrayList<Integer>(n);
		for(int i = 0; i < n; i++) {
			toAdd.add(i);
		}
		
		int o = 0;
		Random rand = new Random();
		while(!toAdd.isEmpty()) {
			int index = rand.nextInt(toAdd.size());
			array[o] = toAdd.get(index);
			toAdd.remove(index);
			o++;
		}
	}

}
