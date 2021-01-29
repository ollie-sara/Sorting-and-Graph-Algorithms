package sorting;

public class MergeSort implements Sorter {

	/*
	 * ITERATIVE MERGESORT / "REINES 2-WEGE MERGESORT"
	 */
	@Override
	public <T extends Comparable<T>> void sort(T[] toSort) {
		int length = 1;												// Initialize length of "segments" to merge
		while(length < toSort.length) {								// Do one iteration per segment length. Length is doubled so we get log n
			int right = 0;											// initialize right to 0 (temporarily)
			while(right + length < toSort.length) {					// Iterate through array with this lenght till all segments have been merged
				int left = right;									// set left to where right is
				int middle = left+length;							// set middle to end of first segment ("length" elements from left)
				right = Math.min(middle + length, toSort.length);	// set right to end of second segment ("length" elements from middle)
				merge(toSort, left, middle, right);					// merge two segments
			}	
			length *= 2;											// double segment size
		}
	}
	
	/*
	 *  merging operation
	 */
	@SuppressWarnings({"unchecked"})
	<T extends Comparable<T>> void merge(T[] A, int left, int middle, int right) {
		Object[] B = new Object[right-left];						// Java is pretty iffy with this but I don't really care
		int i = left;												// Set first pointer to first segment
		int j = middle;												// Set second pointer to second segment
		int k = 0;													// Initialize counter to keep track of elements checked
		while(i < middle && j < right) {							// Do till one segment is empty
			if(A[i].compareTo(A[j]) <= 0) {							// Add smaller of both pointers
				B[k] = A[i];
				i++;
			} else {
				B[k] = A[j];
				j++;
			}
			k++;
		}
		
		while(i < middle) {											// Finish first segment if not done yet
			B[k] = A[i];
			i++;
			k++;
		}
		
		while(j < right) {											// Finish second segment if not done yet
			B[k] = A[j];
			j++;
			k++;
		}
		
		for(int p = 0; p < k; p++) {								// Copy the results over to actual array
			A[p+left] = (T) B[p];
		}
	}

}
