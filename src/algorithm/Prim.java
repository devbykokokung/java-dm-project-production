package src.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import src.algorithm.graph.Edge;
import src.algorithm.graph.Graph;
import src.controller.Controller;

public class Prim implements Algorithm {
    private Graph graph;
    private String StartPoint;
    private Controller ctrl;
    private List<PrimEdge> result;

    public Prim(Graph g) {
        this.graph = g;
    }

    @Override
    public boolean input(Controller c) {
        this.ctrl = c;
        while (true) {
            System.out.print("Enter the start point: ");
            this.StartPoint = c.getScanner().nextLine();
            this.ctrl.getTerminal().clearScreen();
            if (this.graph.getVertexPos().contains(this.StartPoint))
                break;
            System.out.println(this.ctrl.getTerminal().color("red", "Invalid input, try again!"));
        }
        return true;
    }

    @Override
    public boolean run() {
        this.result = this.prims(this.StartPoint);
        return true;
    }

    @Override
    public void result() {
        System.out.println("Result of Prim`s Algorithm\n");
        String[][] resultString = new String[this.result.size()][3];
        int totalCost = 0;
        for (int i = 0; i < this.result.size(); i++) {
            PrimEdge x = this.result.get(i);
            resultString[i] = new String[] { x.getN1(), x.getN2(),
                    String.valueOf(x.getCost()) };
            totalCost += x.getCost();
        }

        this.ctrl.getTerminal().printTable(new String[] { "Start", "End", "Weight" }, resultString);
        System.out.println("\n" + "Total Cost: " + totalCost);
    }

    private class PrimEdge implements Comparable<PrimEdge> {
        int cost;
        String n1;
        String n2;

        public PrimEdge(int cost, String n1, String n2) {
            this.cost = cost;
            this.n1 = n1;
            this.n2 = n2;
        }

        @Override
        public int compareTo(PrimEdge other) {
            return Integer.compare(this.cost, other.cost);
        }

        @Override
        public String toString() {
            return "(" + n1 + ", " + n2 + ", " + cost + ")";
        }

        public int getCost() {
            return cost;
        }

        public String getN1() {
            return n1;
        }

        public String getN2() {
            return n2;
        }
    }

    public List<PrimEdge> prims(String startPoint) {
        String[][] am = this.graph.getAdjacencyMatrix();
        List<PrimEdge> minimumSpanningTree = new ArrayList<>();
        int totalCost = 0;

        Set<String> visited = new HashSet<>();
        visited.add(startPoint);

        PriorityQueue<PrimEdge> priorityQueue = new PriorityQueue<>();

        int startIndex = this.graph.getVertexPos().indexOf(startPoint);

        for (int i = 0; i < this.graph.getVertexLength(); i++) {
            if (am[startIndex][i] != null && this.graph.getEdge(am[startIndex][i]).getData() != 0) {
                priorityQueue.offer(
                        new PrimEdge(this.graph.getEdge(am[startIndex][i]).getData(), startPoint,
                                this.graph.getVertexPos().get(i)));
            }
        }

        System.out.println("Starting Prim's algorithm from node " + startPoint + ":");

        while (!priorityQueue.isEmpty()) {
            PrimEdge edge = priorityQueue.poll();
            String n1 = edge.n1;
            String n2 = edge.n2;
            int cost = edge.cost;

            String newNode = visited.contains(n1) ? n2 : n1;

            if (!visited.contains(newNode)) {
                visited.add(newNode);
                minimumSpanningTree.add(edge);
                totalCost += cost;

                try {
                    // int newNodeIndex = newNode - 'A'; // Convert char to index
                    int newNodeIndex = this.graph.getVertexPos().indexOf(newNode);
                    System.out.println("Added edge: " + edge + ", Total cost: " + totalCost);

                    for (int i = 0; i < this.graph.getVertexLength(); i++) {
                        if (am[newNodeIndex][i] != null && this.graph.getEdge(am[newNodeIndex][i]).getData() != 0
                                && !visited.contains(this.graph.getVertexPos().get(i))) {
                            priorityQueue.offer(new PrimEdge(this.graph.getEdge(am[newNodeIndex][i]).getData(), newNode,
                                    this.graph.getVertexPos().get(i)));

                            System.out.println(" - Added adjacent edge to queue: " + this.graph.getVertexPos().get(i));

                        }
                    }
                    Thread.sleep(900);
                    this.ctrl.getTerminal().clearScreen();
                    
                } catch (InterruptedException e) {

                }
            }
        }
        // System.out.println("Total cost: " + totalCost);
        return minimumSpanningTree;
    }
}
