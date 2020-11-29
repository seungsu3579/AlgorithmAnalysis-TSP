import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solver {

    private double[][] graph;

    public Solver(double[][] graph) {
        this.graph = graph;
    }

    public List<Edge> find_mst() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mstree = new ArrayList<>();
        UnionFind subtree = UnionFind();

        for (int i; i < this.graph.length; i++) {
            for (int j; j < this.graph.length; j++) {
                if (i != j) {
                    pq.add(new Edge(i, j, this.graph[i][j]));
                }
            }
        }

        while (!pq.isEmpty()) {
            Edge tmp = pq.poll();
            if (subtree.get(tmp.getSrc()) != subtree.get(tmp.getDst())) {
                mstree.add(tmp);
            }
            subtree.union(tmp.getSrc(), tmp.getDst());
        }

        return mstree;
    }

    public List<Integer> find_odd_nodes(List<Edge> mst) {
        List<Integer> odd_nodes = new ArrayList<>();
        Map<Integer, Integer> tmp_graph = new HashMap<>();

        for (Edge edge : mst) {
            if (!tmp_graph.containsKey(edge.getSrc())) {
                tmp_graph.put(edge.getSrc(), 0);
            }
            if (!tmp_graph.containsKey(edge.getDst())) {
                tmp_graph.put(edge.getDst(), 0);
            }

            tmp_graph.put(edge.getSrc(), tmp_graph.get(edge.getSrc()) + 1);
            tmp_graph.put(edge.getDst(), tmp_graph.get(edge.getDst()) + 1);
        }

        for (int node_id : tmp_graph.keySet()) {
            if (tmp_graph.get(node_id) % 2 == 1) {
                odd_nodes.add(node_id);
            }
        }
        return odd_nodes;
    }

    public void match_odd_nodes(List<Edge> mst, List<Integer> odd_nodes) {
        // random shuffle odd_nodes
        while (!odd_nodes.isEmpty()) {
            src = odd_nodes.get(odd_nodes.size() - 1);
            odd_nodes.remove(odd_nodes.size() - 1);

            double length = Double.MAX_VALUE;
            int closet = 0;

            for (int dst : odd_nodes) {
                if (src != dst && this.graph[src][dst] < length) {
                    length = this.graph[src][dst];
                    closet = dst;
                }
            }
            mst.add(new Edge(src, dst, length));
            odd_nodes.remove(closet);
        }
    }

    public List<Edge> remove_edge(List<Edge> prepro_mst, int node, int next) {

        for (Edge edge : prepro_mst) {
            if ((edge.getSrc() == next && edge.getDst() == node) || (edge.getSrc() == node && edge.getDst() == next)) {
                prepro_mst.remove(edge);
            }
        }
        return prepro_mst;
    }

    public List<Integer> find_eulerian_circuit(List<Edge> prepro_mst) {

        // find neighbors
        Map<Integer, List<Integer>> neighbors = new HashMap<>();
        for (Edge edge : prepro_mst) {
            if (!neighbors.containsKey(edge.getSrc())) {
                List<Integer> tmp = new ArrayList<>();
                neighbors.put(edge.getSrc(), tmp);
            }
            if (!neighbors.containsKey(edge.getDst())) {
                List<Integer> tmp = new ArrayList<>();
                neighbors.put(edge.getDst(), tmp);
            }

            neighbors.get(edge.getSrc()).add(edge.getDst());
            neighbors.get(edge.getDst()).add(edge.getSrc());
        }

        // finds the hamilton circuit
        int start = prepro_mst.get(0).getSrc();
        List<Integer> eulerian_curcuit = new ArrayList<>();

        while (!prepro_mst.isEmpty()) {
            int i = 0;
            int node;
            for (; i < eulerian_curcuit.size(); i++) {
                node = eulerian_curcuit.get(i);
                if (neighbors.get(node).size() > 0) {
                    break;
                }
            }

            while (neighbors.get(node).size() > 0) {
                next = neighbors.get(node).get(0);

                remove_edge(prepro_mst, node, next);

                neighbors.get(node).remove(next);
                neighbors.get(next).remove(node);

                i++;

                eulerian_curcuit.add(i, next);
            }
        }

        return eulerian_curcuit;
    }

}
