import java.util.ArrayList;

public class PrimMST {
    // The main function that constructs Minimum Spanning Tree
    // (MST) using Prim's algorithm.
    public static int[] getPrimMST(Graph graph, int source, int size) {
        ArrayList<Vertex> vertices = graph.getVertices();
        double[] keys = new double[size]; // The current weight of a vertex.
        keys[source] = 0; // Assign starting vertex with a weight of 0;
        int[] parent = new int[size]; // Array to store the parent of each vertex in MST.

        // minHeap represents set E
        Heap pq = new Heap();

        // Initialize the weight all other vertices to infinity.
        for (int i = 0; i < size; i++) {
            if (i != source) {
                keys[i] = Double.MAX_VALUE;
                parent[i] = -1;
            }
        }

        // Initialize a priority queue with a weight for every vertex.
        pq.heap_ini(keys, size);

        // In the following loop, min heap contains all nodes
        // not yet added to MST.
        while (!pq.isEmpty()) {
            // Extract the vertex with minimum current weight.
            Heap.HeapElement pqE = pq.delete_min();
            int vIndex = pqE.getId();

            // Traverse through all adjacent vertices of the
            // extracted vertex and update their weights if necessary.
            ArrayList<Vertex.Edge> edges = vertices.get(vIndex).getEdges();
            for(Vertex.Edge e: edges) {
                int adjVIndex = e.getEndVertex().getId();

                // If v is not yet included in MST, perform relaxation procedure/decrease
                // key on edge. i.e: If weight of vertex-endVertex(v) edge
                // is less than curr weight of v, then update weight and parent of v.
                if (pq.in_heap(adjVIndex) && e.getWeight() < pq.key(adjVIndex)) {
                    double newKey = e.getWeight();
                    parent[adjVIndex] = vIndex; // Update parent vertex for v
                    pq.decrease_key(adjVIndex, newKey);
                }
            }
        }

        return parent;
    }

    // A utility function used to print the constructed MST
    public static void printMST(Graph graph, int[] parent, int sourceV, int size)
    {
        System.out.println("\nPrim's algorithm - Adjacency edge list representation:");
        System.out.println("Parent v: -> (Parent v, Child v, weight) | (Child v, Parent v, weight) [Edge reversed]");
        ArrayList<Vertex> vertices = graph.getVertices();
        double cost = 0.0;

        for (int i = 0; i < size; i++) {
            if (i == sourceV)
                continue;

            // Iterate through the edge list of the parent vertex.
            for(Vertex.Edge e: vertices.get(parent[i]).getEdges()) {
                // Check if the current end vertex is a child of the parent.
                if (vertices.get(i).getItem() == e.getEndVertex().getItem()) {
                    System.out.print(vertices.get(parent[i]).getItem() + " -> ");

                    // Print the edge and the weight (in the direction it was added to the MST)
                    System.out.print("(" + vertices.get(parent[i]).getItem() + ", " +
                            vertices.get(i).getItem() + ", " + e.getWeight() + ") | ");

                    // Print the edge in the opposite direction.
                    System.out.print("(" + vertices.get(i).getItem() + ", " +
                            vertices.get(parent[i]).getItem() + ", " + e.getWeight() + ")");

                    cost += e.getWeight();
                }
            }
            System.out.println();
        }
        System.out.println("Total edge weight: " + cost);
    }
}