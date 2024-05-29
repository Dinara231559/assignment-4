import java.util.*;

public class DijkstraSearch<V> implements Search<V> {
    private Map<V, Double> distTo;
    private Map<V, V> edgeTo;
    private PriorityQueue<VertexDistance<V>> pq;

    public DijkstraSearch(WeightedGraph<V> graph, V source) {
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        pq = new PriorityQueue<>(Comparator.comparingDouble(VertexDistance::getDistance));

        for (V v : graph.getVertices().keySet()) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
        distTo.put(source, 0.0);

        pq.add(new VertexDistance<>(source, 0.0));
        while (!pq.isEmpty()) {
            relax(graph, pq.poll().getVertex());
        }
    }

    private void relax(WeightedGraph<V> graph, V v) {
        for (Map.Entry<Vertex<V>, Double> entry : graph.getVertex(v).getAdjacentVertices().entrySet()) {
            V w = entry.getKey().getData();
            double weight = entry.getValue();
            if (distTo.get(w) > distTo.get(v) + weight) {
                distTo.put(w, distTo.get(v) + weight);
                edgeTo.put(w, v);
                pq.add(new VertexDistance<>(w, distTo.get(w)));
            }
        }
    }

    @Override
    public List<V> pathTo(V target) {
        List<V> path = new LinkedList<>();
        if (!distTo.containsKey(target) || distTo.get(target) == Double.POSITIVE_INFINITY) return path;

        for (V x = target; x != null; x = edgeTo.get(x)) {
            path.add(x);
        }
        Collections.reverse(path);
        return path;
    }

    private static class VertexDistance<V> {
        private V vertex;
        private double distance;

        public VertexDistance(V vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public V getVertex() {
            return vertex;
        }

        public double getDistance() {
            return distance;
        }
    }
}