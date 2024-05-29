import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedGraph<V> {
    private Map<V, Vertex<V>> vertices;
    private boolean directed;

    public WeightedGraph(boolean directed) {
        this.vertices = new HashMap<>();
        this.directed = directed;
    }

    public void addVertex(V value) {
        vertices.putIfAbsent(value, new Vertex<>(value));
    }

    public void addEdge(V source, V dest, double weight) {
        Vertex<V> sourceVertex = vertices.computeIfAbsent(source, Vertex::new);
        Vertex<V> destVertex = vertices.computeIfAbsent(dest, Vertex::new);

        sourceVertex.addAdjacentVertex(destVertex, weight);
        if (!directed) {
            destVertex.addAdjacentVertex(sourceVertex, weight);
        }
    }

    public Vertex<V> getVertex(V value) {
        return vertices.get(value);
    }

    public Map<V, Vertex<V>> getVertices() {
        return vertices;
    }
}
