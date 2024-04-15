import java.util.ArrayList;

public class PrimMST {
    public static Graph getPrimMST (Graph ogGraph, int size) {
        ArrayList<Vertex> vertices = ogGraph.getVertices();
        int source = 1; // Random starting vertex
        double[] keys = new double[size]; // The current weight of a vertex.
        keys[source] = 0; // Assign vertex with a weight of 0;

        // Initialize the weight all other vertices to infinity.
        for (int i = 0; i < vertices.size(); i++) {
            if (i != source)
                keys[i] = Double.MAX_VALUE;
        }

        Graph newTree = new Graph(size); // Initialize an empty graph.

        // Initialize a priority queue with an item for every vertex.
        Heap pq = new Heap();
        pq.heap_ini(keys, size);

        while (!pq.isEmpty()) {
            // Extract the vertex with minimum current weight.
            Heap.HeapElement vertexInfo = pq.delete_min();

            // Add vertex and edge to new Tree.
            int index = vertexInfo.getId();
            Vertex.Edge newE = vertexInfo.getEdge();

            // TODO DEBUG EDGES NOT CONNECTING/ALWAYS NULL
            if (newE == null) {
                newTree.add(vertices.get(index).getItem());

                System.out.println(vertices.get(index).getItem() + " null");
            }
            else {
                newTree.add(vertices.get(index).getItem(), newE);
                //newTree.add(vertices.get(index).getItem(), newE.getEndVertex().getItem(), newE.getWeight());

                System.out.println(vertices.get(index).getItem() + " " + newE.getEndVertex().getItem() + " " + newE.getWeight());
            }

            // Traverse through all adjacent vertices of u (the
            // extracted vertex) and update their weights if necessary.
            ArrayList<Vertex.Edge> edges = vertices.get(index).getEdges();
            for(Vertex.Edge e: edges) {
                // Check if adj vertex is in priority queue.
                int adjVIndex = e.getEndVertex().getId();
                if (!pq.in_heap(adjVIndex))
                    continue;

                // Perform relaxation procedure / decrease key on edge. i.e: If weight of
                // vertex - endVertex (v) is less than curr weight of v, then update weight of v.
                if (e.getWeight() < pq.key(adjVIndex)) {
                    // System.out.println("BEFORE - " + e.getEndVertex().getItem() + " - key: " + pq.key(adjVIndex));
                    double newKey = e.getWeight();
                    pq.decrease_key(adjVIndex, newKey);
                    // System.out.println("AFTER - " + e.getEndVertex().getItem() + " - key: " + pq.key(adjVIndex));
                    pq.setEdge(index, e);
                }
            }
        }

        return newTree;
    }
}
