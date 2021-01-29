package sorting;
public class QuickSort implements Sorter {
	
	/*
	 *  QUICK SORT IMPLEMENTATION
	 */
	@Override
	public <T extends Comparable<T>> void sort(T[] toSort) {
		sort(toSort, 0, toSort.length-1);
	}
	
	/*
	 *  Actual recursive QuickSort function. sort() is only there to hide some of the parameters that are not needed for the end-user
	 */
	<T extends Comparable<T>> void sort(T[] toSort, int low, int up) {
		if(low < up) {																// This if-statement stops arrays of length 1 of being sorted, effecively ending the recursion
			T pivot = toSort[up];													// Choose our pivot (we use the rightmost element but there are better versions)
			int i = low;															
			int j = up-1;
			while(i < j) {															// Partition all other elements into one group that are larger than the pivot and one that are smaller
				while(i < up && toSort[i].compareTo(pivot) < 0) i++;				// Iterate up till you find an element that is larger
				while(j > low && toSort[j].compareTo(pivot) > 0) j--;				// Iterate down till you find an element that is smaller
				if(i < j) {															// if i and j havent passed each other, switch out the two elements so that they're in the correct partition
					T temp = toSort[i];
					toSort[i] = toSort[j];
					toSort[j] = temp;
				}
			}
			if(toSort[i].compareTo(pivot) > 0) {									// If our middle element is larger than our pivot, switch them out. If not, don't
				toSort[up] = toSort[i];
				toSort[i] = pivot;
			}
			
			sort(toSort, low, i-1);													// Start recursion for lower partition
			sort(toSort, i+1, up);													// Start recursion for higher partition
		}
	}
}
