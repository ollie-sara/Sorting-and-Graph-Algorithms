package sorting;

public class InsertionSort implements Sorter {
	
	/*
	 * My implementation of Insertion Sort, generic, works with any comparable
	 */
	@Override
	public <T extends Comparable<T>> void sort(T[] toSort) {
		
		for(int i = 0; i < toSort.length; i++) {				// Iterate with invariant that all values till i-1 are sorted
			T toInsert = toSort[i];								// Take i-th element (to extend our invariant by 1)
			
			int index = binarySearch(toInsert, toSort, i);		// Do binarySearch on already sorted elements to find where to plug in i-th element
			for(int o = i-1; o >= index; o--) {					// Move all elements larger than i-th element
				toSort[o+1] = toSort[o];
			}
			toSort[index] = toInsert;							// Insert i-th element at correct position
		}
		
	}
	
}
