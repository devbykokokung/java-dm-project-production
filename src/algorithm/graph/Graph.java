package src.algorithm.graph;

import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;

public class Graph {
    private int vertexLength = 0;
    private int edgeLength = 0;
    private String adjacencyMatrix[][];
    private boolean isMerge = false;
    private HashMap<String, Vertex<Integer>> vertexs = new HashMap<String, Vertex<Integer>>();
    private HashMap<String, Edge<Integer>> edge = new HashMap<String, Edge<Integer>>();
    private ArrayList<String> vertexPos = new ArrayList<String>();

    public boolean addVertex(String name, int number) {
        if (this.isMerge)
            return false;
        if (this.vertexs.containsKey(name))
            return false;
        this.vertexs.put(name, new Vertex<Integer>(name, number));
        vertexPos.add(name);
        this.vertexLength++;
        return true;
    }

    public boolean addEdge(String name, int number, String startPoint, String endPoint) {
        if (this.isMerge)
            return false;
        Vertex<Integer> start = this.vertexs.get(startPoint);
        Vertex<Integer> end = this.vertexs.get(endPoint);
        if (this.edge.containsKey(name))
            return false;
        Edge<Integer> edge = new Edge<Integer>(name, number, start, end);
        if (start == null || end == null || start.getEdges().stream()
                .filter((e) -> e.getStartPoint().equals(end) || e.getEndPoint().equals(end)).count() > 0)
            return false;
        start.addEdge(edge);
        end.addEdge(edge);
        this.edge.put(name, edge);
        this.edgeLength++;
        return true;
    }

    public boolean isAllConnected() {
        ArrayList<String> thisVertex = new ArrayList<String>(this.vertexs.keySet());
        Stack<String> stack = new Stack<String>();
        if (thisVertex.size() == 0)
            return false;
        stack.push(thisVertex.remove(0));

        while (!stack.isEmpty()) {
            Vertex<Integer> tmp = vertexs.get(stack.pop());
            tmp.getEdges().stream().map((e) -> !e.getStartPoint().getName().equals(tmp.getName())
                    && thisVertex.contains(e.getStartPoint().getName()) ? e.getStartPoint().getName()
                            : !e.getEndPoint().getName().equals(tmp.getName())
                                    && thisVertex.contains(e.getEndPoint().getName()) ? e.getEndPoint().getName()
                                            : null)
                    .forEach((e) -> {
                        if (e != null && thisVertex.contains(e)) {
                            stack.push(e);
                            thisVertex.remove(e);
                        }
                    });
            // break;
        }

        return thisVertex.size() == 0;
    }

    public String[][] toAdjacencyMatrix() { // พีง
        int n = this.vertexs.size();
        String adjacencyMatrix[][] = new String[n][n];
        this.edge.forEach((k, v) -> {
            adjacencyMatrix[this.vertexPos.indexOf(v.getStartPoint().getName())][this.vertexPos
                    .indexOf(v.getEndPoint().getName())] = v.getName();
            adjacencyMatrix[this.vertexPos.indexOf(v.getEndPoint().getName())][this.vertexPos
                    .indexOf(v.getStartPoint().getName())] = v.getName();
        });

        return adjacencyMatrix;
    }

    public boolean mergeGraph() {
        if (isAllConnected()) {
            this.adjacencyMatrix = this.toAdjacencyMatrix();
            isMerge = true;
            return true;
        }
        return false;
    }

    public HashMap<String, Edge<Integer>> getEdge() {
        return edge;
    }

    public HashMap<String, Vertex<Integer>> getVertexs() {
        return vertexs;
    }

    public Edge<Integer> getEdge(String name) {
        return edge.get(name);
    }

    public Vertex<Integer> getVertexs(String name) {
        return vertexs.get(name);
    }

    public String[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public ArrayList<String> getVertexPos() {
        return vertexPos;
    }

    public int getEdgeLength() {
        return edgeLength;
    }

    public int getVertexLength() {
        return vertexLength;
    }
}