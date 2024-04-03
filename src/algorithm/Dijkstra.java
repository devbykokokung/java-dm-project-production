package src.algorithm;

import src.algorithm.graph.Edge;
import src.algorithm.graph.Graph;
import src.algorithm.graph.Vertex;
import src.controller.Controller;

import java.util.ArrayList;
import java.util.Arrays;

public class Dijkstra implements Algorithm {
    private Graph graph;
    private String StartPoint;
    private Controller ctrl;
    private distInfo[] dist;

    public Dijkstra(Graph g) {
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
        this.run(this.StartPoint);
        return true;
    }

    @Override
    public void result() {
        String[][] resultString = new String[this.dist.length][3];
        int totalCost = 0;
        for (int i = 0; i < this.dist.length; i++) {
            distInfo x = this.dist[i];
            distInfo tmp = x;
            ArrayList<String> path = new ArrayList<>();
            path.add(tmp.name);
            while (tmp.getSrc() != null) {
                path.add(0, tmp.getSrc().getName());
                tmp = this.dist[this.graph.getVertexPos().indexOf(tmp.getSrc().getName())];
            }
            totalCost += x.getCost();
            resultString[i] = new String[] { x.name, String.valueOf(x.getCost()), String.join(" -> ", path) };
        }

        System.out.println("Result of Dijkstra`s Algorithm" + "\n");
        this.ctrl.getTerminal().printTable(new String[] { "Vertex", "Cost", "Path" }, resultString);
        System.out.println("\n" + "Total Cost: " + totalCost);
    }

    // public int indexOfMinDistance(int[] dist, boolean[] sptSet) {
    // int min = Integer.MAX_VALUE;
    // int minIndex = -1;

    // for (int i = 0; i < this.graph.getVertexLength(); i++) {
    // if (!sptSet[i] && dist[i] < min) {
    // min = dist[i];
    // minIndex = i;
    // }
    // }

    // return minIndex;
    // }

    // public void run(String startPoint) {
    // int startPos = this.graph.getVertexPos().indexOf(startPoint);
    // String[][] am = this.graph.getAdjacencyMatrix();

    // int dist[] = new int[this.graph.getVertexLength()];
    // boolean[] srcOut = new boolean[this.graph.getVertexLength()];

    // for (int i = 0; i < this.graph.getVertexLength(); i++) {
    // dist[i] = Integer.MAX_VALUE;
    // srcOut[i] = false;
    // }

    // dist[startPos] = 0;

    // for (int i = 0; i < this.graph.getVertexLength(); i++) {
    // int srcIndex = indexOfMinDistance(dist, srcOut);
    // srcOut[srcIndex] = true;

    // for (int dest = 0; dest < this.graph.getVertexLength(); dest++) {
    // if (am[srcIndex][dest] != null) {
    // Integer destCost = this.graph.getEdge(am[srcIndex][dest]).getData();
    // if (!srcOut[dest] && dist[srcIndex] != Integer.MAX_VALUE
    // && dist[srcIndex] + destCost < dist[dest]) {
    // dist[dest] = dist[srcIndex] + destCost;
    // }
    // }
    // }
    // }
    // }
    public int indexOfMinDistance(distInfo[] dist, boolean[] sptSet) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < this.graph.getVertexLength(); i++) {
            if (!sptSet[i] && dist[i].getCost() < min) {
                min = dist[i].getCost();
                minIndex = i;
            }
        }

        return minIndex;
    }

    public void run(String startPoint) {
        int startPos = this.graph.getVertexPos().indexOf(startPoint);
        String[][] am = this.graph.getAdjacencyMatrix();

        distInfo dist[] = new distInfo[this.graph.getVertexLength()];
        boolean[] srcOut = new boolean[this.graph.getVertexLength()];

        for (int i = 0; i < this.graph.getVertexLength(); i++) {
            dist[i] = new distInfo(this.graph.getVertexPos().get(i));
            dist[i].setCost(Integer.MAX_VALUE);
            srcOut[i] = false;
        }

        dist[startPos].setCost(0);

        for (int i = 0; i < this.graph.getVertexLength(); i++) {
            int srcIndex = indexOfMinDistance(dist, srcOut);
            srcOut[srcIndex] = true;

            for (int destIndex = 0; destIndex < this.graph.getVertexLength(); destIndex++) {
                boolean isLessThan = false;
                if (am[srcIndex][destIndex] != null) {
                    Edge<Integer> dest = this.graph.getEdge(am[srcIndex][destIndex]);
                    if (!srcOut[destIndex] && dist[srcIndex].getCost() != Integer.MAX_VALUE
                            && dist[srcIndex].getCost() + dest.getData() < dist[destIndex].getCost()) {
                        dist[destIndex].setCost(dist[srcIndex].getCost() + ((int) dest.getData()));
                        dist[destIndex].setSrc(this.graph.getVertexs(this.graph.getVertexPos().get(srcIndex)));
                        isLessThan = true;
                    }
                }
                try {
                    System.out.println("Running ==> Dijkstra`s Algorithm" + "\n");
                    System.out.println("Round ==> " + ((i * this.graph.getVertexLength()) + destIndex + 1));
                    System.out.println("dist: " + Arrays.toString(dist));
                    System.out.println("Source Vertex: " + this.graph.getVertexPos().get(srcIndex));
                    System.out.println("Destinaton Vertex: " + this.graph.getVertexPos().get(destIndex));
                    System.out.println("Is less than current cost: " + isLessThan);
                    System.out.println((isLessThan ? "New" : "Current") + " Cost: " + dist[destIndex].getCost());
                    Thread.sleep(900);
                    this.ctrl.getTerminal().clearScreen();
                } catch (Exception e) {
                    System.out.println("Error");
                }
            }
        }

        this.dist = dist;
    }

    private class distInfo {
        private String name;
        private Vertex<Integer> src;
        private int cost;

        public distInfo(String name) {
            this.name = name;
        }

        public void setSrc(Vertex<Integer> src) {
            this.src = src;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        public Vertex<Integer> getSrc() {
            return src;
        }

        @Override
        public String toString() {
            return "[cost=" + cost + ", name=" + name + "]";
        }
    }
}