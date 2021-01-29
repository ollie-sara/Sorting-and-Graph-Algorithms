package sorting;

public class SelectionSort implements Sorter{

	/*
	 *  My implementation of Selection Sort
	 */
	@Override
	public <T extends Comparable<T>> void sort(T[] toSort) {
		for(int i = 0; i < toSort.length-1; i++) {
			T min = toSort[i];							// Find max in Array of element [i] to [n]
			int index = i;
			for(int o = i; o < toSort.length; o++) {
				if(toSort[o].compareTo(min) < 0) {
					min = toSort[o];
					index = o;
				}
			}									
			toSort[index] = toSort[i];					// Exchange values in both
			toSort[i] = min;	
		}
	}

}
