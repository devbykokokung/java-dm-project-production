package src.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

import src.algorithm.graph.Graph;
import src.controller.Controller;

public class Kruskal implements Algorithm {
    private Graph graph;
    private Controller ctrl;
    private KruskalEdge edge[];
    private KruskalEdge[] result;

    public Kruskal(Graph g) {
        this.graph = g;
        this.graph.getEdgeLength();
    }

    @Override
    public boolean input(Controller c) {
        this.ctrl = c;
        return true;
    }

    @Override
    public boolean run() {
        this.result = this.KruskalMST();
        return true;
    }

    @Override
    public void result() {
        System.out.println("Result of Kruskal`s Algorithm\n");
        // String[][] resultString = new String[this.result.length][3];
        ArrayList<String[]> resultString = new ArrayList<>();
        int totalCost = 0;
        for (int i = 0; i < this.result.length; i++) {
            KruskalEdge x = this.result[i];
            if (x.start != x.end) {
                resultString.add(new String[] { this.graph.getVertexPos().get(x.start),
                        this.graph.getVertexPos().get(x.end),
                        String.valueOf(x.weight) });
                totalCost += x.weight;
            }
        }

        this.ctrl.getTerminal().printTable(new String[] { "Start", "End", "Weight" },
                resultString.toArray(String[][]::new));
        System.out.println("\n" + "Total Cost: " + totalCost);
    }

    public KruskalEdge[] KruskalMST() {
        String am[][] = this.graph.getAdjacencyMatrix();

        int V = this.graph.getVertexLength();
        int E = this.graph.getEdgeLength();
        edge = new KruskalEdge[E];
        for (int i = 0; i < this.graph.getEdgeLength(); ++i)
            edge[i] = new KruskalEdge();

        int k = 0;
        for (int i = 0; i < this.graph.getVertexLength(); i++) {
            for (int j = i + 1; j < this.graph.getVertexLength(); j++) {
                if (am[i][j] != null && this.graph.getEdge(am[i][j]).getData() != 0) {
                    this.edge[k].start = i;
                    this.edge[k].end = j;
                    this.edge[k].weight = this.graph.getEdge(am[i][j]).getData();
                    k++;
                }
            }
        }

        KruskalEdge result[] = new KruskalEdge[V];
        int e = 0;
        int i = 0;
        for (i = 0; i < V; ++i)
            result[i] = new KruskalEdge();

        Arrays.sort(edge);

        KruskalSubset subsets[] = new KruskalSubset[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new KruskalSubset();

        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0;
        while (e < V - 1) {
            KruskalEdge next_edge = new KruskalEdge();
            next_edge = edge[i++];

            int x = find(subsets, next_edge.start);
            int y = find(subsets, next_edge.end);

            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }

        return result;

    }

    private int find(KruskalSubset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    private void Union(KruskalSubset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    private class KruskalSubset {
        int parent, rank;
    };

    private class KruskalEdge implements Comparable<KruskalEdge> {
        int start, end, weight;

        @Override
        public int compareTo(KruskalEdge compare) {
            return this.weight - compare.weight;
        }

    };

}