package sorting;

public interface Sorter {
	public <T extends Comparable<T>> void sort(T[] toSort);

	/*
	 *  My implementation of binary search, crucial for some sorting algos
	 */
	public default <T extends Comparable<T>> int binarySearch(T key, T[] array, int upGiven) {
		int low = 0;									// Initialise lower bound to smallest element
		int up = upGiven;								// initialise upperbound to largest element + 1 (here you can pass a number)
		int mid;										
		while (low <= up) {								// Will be false once low is higher than up
			mid = (up + low) / 2;						// Find middle between lower and upper bound
			int compare = key.compareTo(array[mid]);	// Figure out whether or not our key is smaller or larger
			if (compare < 0) {							// If our key is smaller, only search in the lower half
				up = mid - 1;
			} else if (compare > 0) {					// If our key is larger, only search in the upper half
				low = mid + 1;
			} else {									// If our key is equivalent, return the middle index
				return mid;
			}
		}
		return low;										// If push comes to shove, return the lower bound
	}
}
