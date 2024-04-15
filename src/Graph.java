import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Graph {
    private ArrayList<Vertex> vertices;

    /*
        Initializes the list of vertices.
     */
    public Graph (int numVertices) {
        vertices = new ArrayList<>(numVertices);
    }

    public void initGraph(File inputFile, Scanner scnr) {
        // Check for end of file.
        while(scnr.hasNextLine()) {
            String line = scnr.nextLine();

            // Trim whitespaces then split the line by whitespace into multiple elements.
            String[] numbers = line.trim().split("\\s+");
            int num1 = Integer.parseInt(numbers[0]);
            int num2 = Integer.parseInt(numbers[1]);
            double weight = Double.parseDouble(numbers[2]);

            // Add the edge to the graph.
            add(num1, num2, weight);
        }

        scnr.close();
    }

    // This function adds a new vertex.
    public void add(int vertex) {
        boolean vertexFound = false;
        int vertexIndex = -1;

        // Find the vertex and its index, if it exists
        for (int i = 0; (i < vertices.size()) && !vertexFound; i++) {
            if (vertices.get(i).getItem() == vertex) {
                vertexFound = true;
                vertexIndex = i;
            }
        }
        // Create vertex if it doesn't exist.
        if (!vertexFound) {
            Vertex newVertex = new Vertex(vertex);
            vertices.add(newVertex);
            vertexIndex = vertices.size() - 1;
            newVertex.setId(vertexIndex);
        }
    }

    /*
        This function adds a new edge to the vertices in both directions.
        It first checks to see if the vertices exist or not.
     */
    public void add(int vertex1, int vertex2, double weight) {
        if (vertex1 == vertex2)
            return;

        boolean vertex1Found = false;
        boolean vertex2Found = false;
        int vertex1Index = -1;
        int vertex2Index = -1;

        // Find the vertices and their index, if they exist
        for (int i = 0; (i < vertices.size()) && (!vertex1Found || !vertex2Found); i++) {
            if (vertices.get(i).getItem() == vertex1) {
                vertex1Found = true;
                vertex1Index = i;;
            }
            else if (vertices.get(i).getItem() == vertex2) {
                vertex2Found = true;
                vertex2Index = i;
            }
        }
        // Create vertex1 if it doesn't exist.
        if (!vertex1Found) {
            Vertex newVertex1 = new Vertex(vertex1);
            vertices.add(newVertex1);
            vertex1Index = vertices.size() - 1;
            newVertex1.setId(vertex1Index);
        }
        // Create vertex2 if it doesn't exist.
        if (!vertex2Found) {
            Vertex newVertex2 = new Vertex(vertex2);
            vertices.add(newVertex2);
            vertex2Index = vertices.size() - 1;
            newVertex2.setId(vertex2Index);
        }

        // Add edges both ways.
        vertices.get(vertex1Index).connect(vertices.get(vertex2Index), weight);
        vertices.get(vertex2Index).connect(vertices.get(vertex1Index), weight);
    }

    /*
        This function adds an already created edge to the vertices in both directions.
        It first checks to see if the vertices exist or not.
     */
    public void add(int vertex1, Vertex.Edge e) {
        int vertex2 = e.getEndVertex().getItem();

        if (vertex1 == vertex2)
            return;

        boolean vertex1Found = false;
        boolean vertex2Found = false;
        int vertex1Index = -1;
        int vertex2Index = -1;

        // Find the vertices and their index, if they exist
        for (int i = 0; (i < vertices.size()) && (!vertex1Found || !vertex2Found); i++) {
            if (vertices.get(i).getItem() == vertex1) {
                vertex1Found = true;
                vertex1Index = i;
            }
            else if (vertices.get(i).getItem() == vertex2) {
                vertex2Found = true;
                vertex2Index = i;
            }
        }
        // Create vertex1 if it doesn't exist.
        if (!vertex1Found) {
            Vertex newVertex1 = new Vertex(vertex1);
            vertices.add(newVertex1);
            vertex1Index = vertices.size() - 1;
            newVertex1.setId(vertex1Index);
        }
        // Create vertex2 if it doesn't exist.
        if (!vertex2Found) {
            Vertex newVertex2 = new Vertex(vertex2);
            vertices.add(newVertex2);
            vertex2Index = vertices.size() - 1;
            newVertex2.setId(vertex2Index);
        }

        // Add edges both ways.
        vertices.get(vertex1Index).connect(e);
        vertices.get(vertex2Index).connect(e);
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    // Prints the graph in adjacency list representation
    // format listing each edge with its weight.
    public void printGraph() {
        System.out.println("Adjacency edge list graph representation:");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i).getItem() + " ->");
            vertices.get(i).printEdges();
            System.out.println();
        }
    }
}
