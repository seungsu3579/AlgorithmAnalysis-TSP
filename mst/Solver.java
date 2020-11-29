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

    public List<Integer> find_eulerian_circuit(List<Edge> even_node_mst) {

        // find neighbors
        Map<Integer, List<Integer>> tmp_graph = new HashMap<>();

    }

}
