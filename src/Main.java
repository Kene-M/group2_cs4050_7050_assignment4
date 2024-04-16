/**
 * @author Kene, Skylar, Isaiah.
 * Implemented Prim's algorithm on a graph represented
 * by an adjacency list using a binary heap priority queue.
 */

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Open file for reading.
        File inputFile = new File("graph.txt");
        Scanner scnr = new Scanner(inputFile);

        // Read the number of vertices.
        int numVertices = scnr.nextInt();
        scnr.nextLine();

        // Create graph with specified numVertices.
        Graph graph = new Graph(numVertices);
        graph.initGraph(inputFile, scnr);

        // Output graph in adjacency list format (with weight).
        System.out.print("Text file - ");
        graph.printGraph();
        System.out.println();

        // Prim's Algorithm, output edges (and weights) of the minimum spanning tree.

        // Graph primMST = PrimMST.getPrimMST(graph, numVertices);
        int root = 7; // The index of the root in the graph/tree.
        int[] parent = PrimMST.getPrimMST(graph, root, numVertices);
        PrimMST.printMST(graph, parent, root, numVertices);
    }
}