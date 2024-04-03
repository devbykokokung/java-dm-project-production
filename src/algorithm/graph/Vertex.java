package src.algorithm.graph;

import java.util.ArrayList;

public class Vertex<T> {
    private String name;
    private T data;
    private ArrayList<Edge<?>> edges = new ArrayList<Edge<?>>();

    public Vertex(String name, T data) {
        this.name = name;
        this.data = data;
    }

    public void addEdge(Edge<?> edge) {
        this.edges.add(edge);
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }

    public ArrayList<Edge<?>> getEdges() {
        return edges;
    }

    // @Override
    // public String toString() {
    //     return "Vertex [name=" + name + ", data=" + ((String) data) + "]";
    // }
}
