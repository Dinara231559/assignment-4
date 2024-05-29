import java.util.*;

public class BreadthFirstSearch<V> implements Search<V> {
    private Map<V, V> edgeTo;
    private Set<V> marked;
    private V source;

    public BreadthFirstSearch(MyGraph<V> graph, V source) {
        this.source = source;
        edgeTo = new HashMap<>();
        marked = new HashSet<>();
        bfs(graph, source);
    }

    private void bfs(MyGraph<V> graph, V start) {
        Queue<V> queue = new LinkedList<>();
        marked.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            V v = queue.poll();
            for (V w : graph.adjacencyList(v)) {
                if (!marked.contains(w)) {
                    queue.add(w);
                    marked.add(w);
                    edgeTo.put(w, v);
                }
            }
        }
    }

    @Override
    public List<V> pathTo(V target) {
        List<V> path = new LinkedList<>();
        if (!marked.contains(target)) return path;

        for (V x = target; !x.equals(source); x = edgeTo.get(x)) {
            path.add(x);
        }
        path.add(source);
        Collections.reverse(path);
        return path;
    }
}