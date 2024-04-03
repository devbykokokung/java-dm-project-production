package src.algorithm.graph;

public class Edge<T> {
    private String name;
    private T data;
    private Vertex<?> startPoint;
    private Vertex<?> endPoint;

    public Edge(String name, T data, Vertex<?> startPoint, Vertex<?> endPoint) {
        this.name = name;
        this.data = data;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }

    public Vertex<?> getStartPoint() {
        return startPoint;
    }

    public Vertex<?> getEndPoint() {
        return endPoint;
    }

    // @Override
    // public String toString() {
    //     return "Edge [name=" + name + ", data=" + ((String) data) + ", startPoint=" + startPoint + ", endPoint="
    //             + endPoint + "]";
    // }
}
