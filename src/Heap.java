import java.util.Arrays;

/**
 * Heap class used as a min priority queue in the MST of Prim's algorithm.
 */

public class Heap {
    private HeapElement[] heap;
    private int size; // Number of array slots [length + 1]
    //TODO keys[] probs not needed.
    private double[] keys; // Parallel array containing passed key parameter.
    private boolean[] inHeap; // Parallel array for noting which vertices are in heap.
    private HeapElement[] vertexInfo; // Array parallel to the original order of adj list.
    private int[] positions; // Stores the positions of each vertex in the heap based on the id.

    // Inner class that stores the id of a vertex, and an edge.
    // Represents a potential vertex in the MST.
    public static class HeapElement {
        private int id; // Represents the index of a vertex in a graph.
        private Vertex.Edge edge;
        private double key; // Represents the current weight/priority of a vertex in the heap.

        private HeapElement(int id, double key, Vertex.Edge edge) {
            this.id = id;
            this.key = key;
            this.edge = edge;
        }

        public int getId() {
            return this.id;
        }
        public Vertex.Edge getEdge() {
            return this.edge;
        }
        public void setEdge(Vertex.Edge e) {
            //TODO remove
            System.out.println("Edge  " + e.getEndVertex().getItem() + " - Weight - " +
                    e.getWeight());
            this.edge = e;
        }
    }

    /*
        initializes a heap with the array keys of n elements indexed from
        1 to n, where key[i] is the key of the element whose id is i.
     */
    public void heap_ini(double[] keys, int n) {
        size = n + 1;
        heap = new HeapElement[size];

        this.keys = keys;
        vertexInfo = new HeapElement[size];
        positions = new int[size];
        inHeap = new boolean[size];
        Arrays.fill(inHeap, true);

        // Add elements n times.
        int i = 0;
        while (i < n) {
            heap[i + 1] = new HeapElement(i, keys[i], null);

            vertexInfo[i] = heap[i + 1];
            positions[i] = i + 1;
            i++;

            int j = i;
            while (j != 1 && heap[j/2].key > heap[j].key) {
                positions[heap[j].id] = j / 2;
                positions[heap[j / 2].id] = j;
                // swap
                HeapElement temp = heap[j];
                heap[j] = heap[j/2];
                heap[j/2] = temp;
                j = j/2;
            }
        }
        //for (int j = 1; j <= n; j++)
          //  System.out.println("id: " + heap[j].id + " - key: " + heap[j].key);
    }

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
        //return vertexInfo[id].key;
        // return keys[id];
    }

    public void reheap(int pos) {
        int smallest = pos;

        if (((2 * pos) < (size - 1)) && heap[smallest].key > heap[2 * pos].key) {
            smallest = 2 * pos;
        }
        if (((2 * pos + 1) < (size - 1)) && (heap[smallest].key > heap[2 * pos + 1].key)) {
            smallest = 2 * pos + 1;
        }
        if (smallest != pos) {
            positions[heap[pos].id] = smallest;
            positions[heap[smallest].id] = pos;
            //swap
            HeapElement temp = heap[pos];
            heap[pos] = heap[smallest];
            heap[smallest] = temp;

            reheap(smallest);
        }
    }

    // deletes the element with minimum key from the heap;
    public HeapElement delete_min() {
        /*System.out.println("Before deletion:");
        for (int j = 1; j < size; j++)
            System.out.println("id: " + heap[j].id + " - key: " + heap[j].key);*/

        HeapElement minElem = null;

        if (size > 0) {
            minElem = heap[1];
            inHeap[minElem.id] = false;

            // Update positions
            positions[heap[1].id] = size - 1;
            positions[heap[size - 1].id] = 1;

            // Remove element and form a semiheap.
            heap[1] = heap[size - 1];
            size--;

            // REHEAP at index 1.
            reheap(1);
        }
        /*System.out.println("After deletion:");
        for (int j = 1; j < size; j++)
            System.out.println("id: " + heap[j].id + " - key: " + heap[j].key);*/
        return minElem;
    }

    /*
        Sets the key of the element whose id is id to new_key
        if its current key is greater than new_key.
     */
    public void decrease_key(int id, double new_key) {
        // Retrieve index of old key in heap
        int i = positions[id];

        double currKey = key(id);

        if (currKey > new_key) {
            vertexInfo[id].key = new_key;
            keys[id] = new_key;
        }

        // UPHEAP at index in heap.
        while (i > 1) {
            if (heap[i].key < heap[i/2].key) {
                // Update positions
                positions[heap[i].id] = i / 2;
                positions[heap[i / 2].id] = i;
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

    public void setEdge(int id, Vertex.Edge e) {
        System.out.println(inHeap[id]);
        vertexInfo[id].setEdge(e);
    }
}