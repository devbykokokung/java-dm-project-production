// package src.alogorithm.Graph;

// import java.util.HashMap;

// public class Finite {
//     private HashMap<String, Vertex<Integer>> vertexs = new HashMap<String, Vertex<Integer>>();
//     private HashMap<String, Edge<String>> edge = new HashMap<String, Edge<String>>();

//     public void addVertex(String name, int number) {
//         this.vertexs.put(name, new Vertex<Integer>(name, number));
//     }

//     public void addEdge(String name, String number, String startPoint, String endPoint) {
//         Vertex<Integer> start = this.vertexs.get(startPoint);
//         Vertex<Integer> end = this.vertexs.get(endPoint);
//         Edge<String> edge = new Edge<String>(name, number, start, end);
//         start.addEdge(edge);
//         end.addEdge(edge);
//         this.edge.put(name, edge);
//     }

//     public HashMap<String, Edge<String>> getEdge() {
//         return edge;
//     }

//     public HashMap<String, Vertex<Integer>> getVertexs() {
//         return vertexs;
//     }
// }

package src.algorithm.graph;

import java.util.ArrayList;
import java.util.Stack;

import java.util.HashMap;

public class Finite {
    private int vertexLength = 0;
    private int edgeLength = 0;
    private String adjacencyMatrix[][];
    private boolean isMerge = false;
    private HashMap<String, Vertex<Integer>> vertexs = new HashMap<String, Vertex<Integer>>();
    private HashMap<String, Edge<String>> edge = new HashMap<String, Edge<String>>();
    private ArrayList<String> vertexPos = new ArrayList<String>();
    private ArrayList<String> accept = new ArrayList<String>();

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

    public boolean addEdge(String name, String input, String startPoint, String endPoint) {
        if (this.isMerge)
            return false;
        Vertex<Integer> start = this.vertexs.get(startPoint);
        Vertex<Integer> end = this.vertexs.get(endPoint);
        if (this.edge.containsKey(name))
            return false;
        Edge<String> edge = new Edge<String>(name, input, start, end);
        if (start == null || end == null)
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
        }

        return thisVertex.size() == 0;
    }

    public String[][] toAdjacencyMatrix() {
        int n = this.vertexs.size();
        String adjacencyMatrix[][] = new String[n][n];
        this.edge.forEach((k, v) -> {
            adjacencyMatrix[this.vertexPos.indexOf(v.getStartPoint().getName())][this.vertexPos
                    .indexOf(v.getEndPoint().getName())] = v.getName();
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

    public String[] setAccept(String[] edgeName) {
        this.accept.removeAll(accept);
        for (String name : edgeName)
            if (this.vertexs.containsKey(name))
                this.accept.add(name);

        String[] result = new String[this.accept.size()];
        result = accept.toArray(result);
        return result;
    }

    public boolean isAccept(int idx) {
        String vertexName = this.vertexPos.get(idx);
        return this.accept.contains(vertexName);
    }

    public HashMap<String, Edge<String>> getEdge() {
        return edge;
    }

    public HashMap<String, Vertex<Integer>> getVertexs() {
        return vertexs;
    }

    public Edge<String> getEdge(String name) {
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

    public ArrayList<String> getAccept() {
        return accept;
    }
}
