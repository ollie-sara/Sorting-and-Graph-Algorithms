package sorting;

public class HeapSort implements Sorter {
	
	/*
	 *  HEAP SORT
	 */
	@Override
	public <T extends Comparable<T>> void sort(T[] toSort) {
		
		for(int i = toSort.length/2; i >= 0; i--) {				// Restores heap condition for first half (second half is just leaves). Works from the bottom up
			restoreHeap(toSort, i, toSort.length);
		}
		for(int m = toSort.length-1; m >= 0; m--) {				// Switches max element with our last non-sorted element, afterwards restores heap condition
			T temp = toSort[m];
			toSort[m] = toSort[0];
			toSort[0] = temp;
			restoreHeap(toSort, 0, m);
		}
				
	}
	
	/*
	 *  RESTORES HEAP CONDITION (Every child must be smaller than parent)
	 */
	<T extends Comparable<T>> void restoreHeap(T[] heap, int index, int upper) {
		while((2*index)+1 < upper) {								// This while loop stops as soon as first child doesnt exist
			int j = (2*index)+1;									// Sets first child to max
			if(j+1 < upper) {										// checks if second child exists
				if(heap[j].compareTo(heap[j+1]) < 0) j = j+1;		// checks if second child is larger than first, if yes then changes max
			}
			if(heap[index].compareTo(heap[j]) >= 0) return;			// checks if parent is larger than largest child, if yes, heap condition for this node is complete, return
			T temp = heap[j];										// if not, switches out largest child with parent
			heap[j] = heap[index];
			heap[index] = temp;
			index = j;												// restores heap condition for ex-parent, might still be smaller than one of the next children
		}
	}

}
