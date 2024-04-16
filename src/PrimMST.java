import java.util.ArrayList;

public class PrimMST {
    // The main function that constructs Minimum Spanning Tree
    // (MST) using Prim's algorithm.
    public static int[] getPrimMST(Graph graph, int source, int size) {
        ArrayList<Vertex> vertices = graph.getVertices();
        double[] keys = new double[size]; // The current weight of a vertex.
        keys[source] = 0; // Assign starting vertex with a weight of 0;
        int[] parent = new int[size]; // Array to store constructed MST

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


                // If v is not yet included in MST and weight of
                // u-v is less than key value of v, then update
                // key value and parent of v

                // If v is not yet included in MST, perform relaxation procedure / decrease key on edge. i.e: If weight of
                // vertex-endVertex (v) is less than curr weight of v, then update weight of v.
                if (pq.in_heap(adjVIndex) && e.getWeight() < pq.key(adjVIndex)) {
                    double newKey = e.getWeight();
                    parent[adjVIndex] = vIndex; // Update
                    pq.decrease_key(adjVIndex, newKey);
                }
            }
        }

        return parent;
    }

    // A utility function used to print the constructed MST
    public static void printMST(Graph graph, int[] parent, int sourceV, int size)
    {
        /*for (int i = 1; i < size; i++) {
            System.out.println(parent[i] + " - " + i);
        }*/

        System.out.println("\nPrim's algorithm - Adjacency edge list representation:");
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

/*public class PrimMST {
    public static Graph getPrimMST (Graph ogGraph, int size) {
        ArrayList<Vertex> vertices = ogGraph.getVertices();
        int source = 0; // Random starting vertex index
        double[] keys = new double[size]; // The current weight of a vertex.
        keys[source] = 0; // Assign vertex with a weight of 0;

        // Initialize the weight all other vertices to infinity.
        for (int i = 0; i < vertices.size(); i++) {
            if (i != source)
                keys[i] = Double.MAX_VALUE;
        }

        Graph newTree = new Graph(size); // Initialize an empty graph.


        Heap pq = new Heap();
        pq.heap_ini(keys, size);

        while (!pq.isEmpty()) {
            // Extract the vertex with minimum current weight.
            Heap.HeapElement pqE = pq.delete_min();

            // Add vertex and edge to new Tree.
            int index = pqE.getId();
            Vertex.Edge newE = pqE.getEdge();

            // TODO NOT PROPERLY PRUNING EDGES
            if (newE == null) {
                newTree.add(vertices.get(index).getItem());
                //System.out.println(vertices.get(index).getItem() + " null");
            }
            else {
                newTree.add(vertices.get(index).getItem(), newE);
                //newTree.add(vertices.get(index).getItem(), newE.getEndVertex().getItem(), newE.getWeight());

                System.out.println(vertices.get(index).getItem() + " " + newE.getEndVertex().getItem() + " " + newE.getWeight());
            }
            //int addedPos = newTree.getVertices().size() - 1;

            // Traverse through all adjacent vertices of u (the
            // extracted vertex) and update their weights if necessary.
            ArrayList<Vertex.Edge> edges = vertices.get(index).getEdges();

            *//*System.out.println();
            vertices.get(index).printEdges();
            System.out.println();*//*

            for(Vertex.Edge e: edges) {
                // Check if adj vertex is in priority queue.
                int adjVIndex = e.getEndVertex().getId();
                if (!pq.in_heap(adjVIndex))
                    continue;

                // Perform relaxation procedure / decrease key on edge. i.e: If weight of
                // vertex - endVertex (v) is less than curr weight of v, then update weight of v.
                if (e.getWeight() < pq.key(adjVIndex)) {
                    double newKey = e.getWeight();
                    pq.decrease_key(adjVIndex, newKey);
                    //pq.setEdge(adjVIndex, e);
                    newTree.add(vertices.get(index).getItem(), e.getEndVertex().getItem(), e.getWeight());
                    //System.out.println("id: " + index + " | u: " + vertices.get(index).getItem() +
                    //      " | v: " + e.getEndVertex().getItem() + " | w: " + e.getWeight());
                }
            }
        }

        return newTree;
    }*/
