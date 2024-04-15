/**
 * @author Kene, Skylar, Isaiah.
 *
 *
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

        // TODO Debug
        // Prim's Algorithm, output edges (and weights) of the minimum spanning tree.
        Graph primMST = PrimMST.getPrimMST(graph, numVertices);
        System.out.print("\nPrim's algorithm - ");
        primMST.printGraph();
    }
}