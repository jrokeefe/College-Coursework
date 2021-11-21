
public class BoardingHeap {

	private Passenger[] heap; //array of passengers currently in the heap
	private int size;
	private int index;

    //This should be your only constructor
    public BoardingHeap() {
    		heap = new Passenger[100];
    		size = 0;
    		index = 1;
    }
    
    //Public heap methods. You may add private methods
    public void enqueue(Passenger p) {
    		if(isEmpty()) {
    			index = 1;
    		}
    		++size;
    		if (size == heap.length) {
    			int n = 0;
    			Passenger[] intmedHeap = new Passenger[heap.length*2];
    			for (Passenger a : heap) {
    				intmedHeap[n] = a;
    				++n;
    			}
    			heap = intmedHeap;
    			heap[size] = p;
    		}
    		else {
    			heap[size] = p;
    		}
    }
    
    public Passenger dequeue() {
		if(isEmpty()) {
			throw new IllegalStateException("cannot peek into empty unsorted array");
		}
		
		Passenger p = heap[index];
		size--;
		heap[index++] = null;
		return p;
    }
    
    public boolean isEmpty() {	
    		return size == 0;
    }
    
    public int getSize() {
    		return size;
    }
}