import java.util.ArrayList;

public class Vertex {
    private int item;
    private ArrayList<Edge> edges; // List of adjacent edges.
    private int id; // Position of vertex in adjacency list.
    private boolean isMarked;

    // Receives the data item for this vertex, and initializes all the fields.
    public Vertex(int item) {
        this.item = item;
        edges = new ArrayList<>();
        isMarked = false;
    }

    // Edge inner class.
    public static class Edge {
        // private Vertex startVertex; // Not needed due to Vertex outer class.
        private Vertex endVertex;
        private double weight;

        public Edge(Vertex endVertex, double weight) {
            this.endVertex = endVertex;
            this.weight = weight;
        }
        public Vertex getEndVertex() {
            return endVertex;
        }

        public double getWeight() {
            return weight;
        }
    }

    public int getItem() {
        return item;
    }

    /** Connects this vertex and a given vertex with a weighted edge.
     * The two vertices cannot be the same, and must not already
     * have this edge between them. */
    public boolean connect(Vertex endVertex, double edgeWeight) {
        // Check if the vertex provided is the same as this vertex.
        if (endVertex.getItem() == this.getItem())
            return false;

        // Ensure that this edge does not already exist.
        for (int i = 0; i < edges.size(); i++) {
            if ((endVertex.getItem() == edges.get(i).endVertex.getItem()) &&
                    (edges.get(i).weight == edgeWeight))
                return false;
        }

        // Add the new edge
        edges.add(new Edge(endVertex, edgeWeight));
        return true;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Prints the edges in adjacency list representation
    // format listing each edge with its weight.
    public double printEdges() {
        double cost = 0.0;
        for (int i = 0; i < edges.size(); i++) {
            if (i != 0)
                System.out.print(",");
            System.out.print(" (" + item + ", " +
                    edges.get(i).endVertex.getItem() + ", " + edges.get(i).weight + ")");
            cost += edges.get(i).weight;
        }
        return cost;
    }

    public void mark() {
        isMarked = true;
    }

    public void unMark() {
        isMarked = false;
    }

    public boolean isVisited() {
        return isMarked;
    }
}
