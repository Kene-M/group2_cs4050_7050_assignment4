import java.util.Arrays;

/**
 * Heap class used as a min priority queue in the MST of Prim's algorithm.
 */
public class Heap {
    private HeapElement[] heap; // Min heap based on the "key" of a vertex.
    private int size; // Number of array slots [length + 1]
    private boolean[] inHeap; // Parallel array for noting which vertices are in heap.
    private int[] positions; // Stores the positions of each vertex in the heap based on the id.

    // Inner class that stores the id of a vertex, and an edge.
    // Represents a potential vertex in the MST.
    public static class HeapElement {
        private int id; // Represents the index of a vertex in a graph.
        private double key; // Represents the current weight/priority of a vertex in the heap.

        private HeapElement(int id, double key) {
            this.id = id;
            this.key = key;
        }

        public int getId() {
            return this.id;
        }
    }

    /*
        initializes a heap with the array keys of n elements indexed from
        1 to n, where key[i] is the key of the element whose id is i.
     */
    public void heap_ini(double[] keys, int n) {
        size = n + 1;
        heap = new HeapElement[size];
        positions = new int[size];
        inHeap = new boolean[size];
        Arrays.fill(inHeap, true);

        // Add elements n times.
        int i = 0;
        while (i < n) {
            heap[i + 1] = new HeapElement(i, keys[i]); // Insert new element.
            positions[i] = i + 1; // Note position of new element.
            i++;

            // UPHEAP if necessary.
            int j = i;
            while (j != 1 && heap[j/2].key > heap[j].key) {
                // Update positions of swapped elements.
                positions[heap[j].id] = j / 2;
                positions[heap[j / 2].id] = j;
                // swap
                HeapElement temp = heap[j];
                heap[j] = heap[j/2];
                heap[j/2] = temp;
                j = j/2;
            }
        }
    }

    // Returns true if the heap is empty, otherwise false.
    public boolean isEmpty() {
        if (size <= 1) {
            return true;
        }
        return false;
    }

    // returns true if the element whose id is in the heap;
    public boolean in_heap(int id) {
        return inHeap[id];
    }

    // returns the minimum key of the heap;
    public double min_key() {
        return heap[1].key;
    }

    // Returns the id of the element with minimum key in the heap.
    public int min_id() {
        return heap[1].id;
    }

    // returns the key of the element whose id is id in the heap;
    public double key(int id) {
        return heap[positions[id]].key;
    }

    /*
    Used for maintaining the heap property after a deletion (done up-ground).
     */
    public void reheap(int pos) {
        int smallest = pos;

        // Check whether the left child (2i) or right child (2i + 1) is smaller.
        if (((2 * pos) < (size - 1)) && heap[smallest].key > heap[2 * pos].key) {
            smallest = 2 * pos;
        }
        if (((2 * pos + 1) < (size - 1)) && (heap[smallest].key > heap[2 * pos + 1].key)) {
            smallest = 2 * pos + 1;
        }
        // Swap with left or right child if necessary.
        if (smallest != pos) {
            // Update positions of swapped elements.
            positions[heap[pos].id] = smallest;
            positions[heap[smallest].id] = pos;

            // swap
            HeapElement temp = heap[pos];
            heap[pos] = heap[smallest];
            heap[smallest] = temp;

            reheap(smallest); // recurse on subtree of next level.
        }
    }

    // deletes the element with minimum key from the heap;
    public HeapElement delete_min() {
        HeapElement minElem = null;

        if (size > 0) {
            minElem = heap[1];
            inHeap[minElem.id] = false;

            // Update positions of swapped elements.
            positions[heap[1].id] = size - 1; // Removed element is at removed position.
            positions[heap[size - 1].id] = 1; // Potential root is at index 1;

            // Remove element and form a semiheap.
            heap[1] = heap[size - 1];
            size--;

            // REHEAP at index 1.
            reheap(1);
        }

        return minElem;
    }

    /*
        Sets the key of the element whose id is id to new_key
        if its current key is greater than new_key.
     */
    public void decrease_key(int id, double new_key) {
        // Retrieve index of old key in heap
        int i = positions[id];

        double currKey = key(id); // Get current key at specified id.

        // Decrease key
        if (currKey > new_key) {
            heap[i].key = new_key;
        }

        // UPHEAP at index in heap.
        while (i > 1) {
            if (heap[i].key < heap[i/2].key) {
                // Update positions of swapped elements.
                positions[heap[i].id] = i/2;
                positions[heap[i/2].id] = i;
                // Swap
                HeapElement temp = heap[i];
                heap[i] = heap[i/2];
                heap[i/2] = temp;
                i = i/2;
            }
            else
                break;
        }
    }
}