package mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Collections;

public class MSTSolver {

    private double[][] graph;
    private double distance;
    private List<Integer> route;

    public MSTSolver(Graph graph) {
        this.graph = graph.getGraph();
    }

    public void christofides() {

        // find minimum spanning tree
        List<Edge> mst = find_mst();

        // find nodes that has odd number edges
        List<Integer> odd_node_list = find_odd_nodes(mst);

        // add minimum weight edges to make tmp hamilton cycle
        make_tmp_eulerian_curcuit(mst, odd_node_list);

        // find hamilton cycle
        List<Integer> hamiltonian_cycle = find_hamiltonian_cycle(mst);

        // prune
        List<Integer> circuit = prune_circuit(hamiltonian_cycle);

        // make start point 0
        this.route = make_start_point_zero(circuit);

        // calculate distance of route
        this.distance = distance(route);
    }

    public double getDistance() {
        return distance;
    }

    public List<Integer> getRoute() {
        return route;
    }

    private List<Edge> find_mst() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mstree = new ArrayList<>();
        UnionFind subtree = new UnionFind();

        for (int i = 0; i < this.graph.length; i++) {
            for (int j = 0; j < this.graph.length; j++) {
                if (i != j) {
                    pq.add(new Edge(i, j, this.graph[i][j]));
                }
            }
        }

        while (!pq.isEmpty()) {
            Edge tmp = pq.poll();

            if (subtree.get(tmp.getSrc()) != subtree.get(tmp.getDst())) {
                mstree.add(tmp);
                subtree.union(tmp.getSrc(), tmp.getDst());
            }
        }

        return mstree;
    }

    private List<Integer> find_odd_nodes(List<Edge> mst) {
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

    private void make_tmp_eulerian_curcuit(List<Edge> mst, List<Integer> odd_nodes) {
        // random shuffle odd_nodes
        Collections.shuffle(odd_nodes);

        while (!odd_nodes.isEmpty()) {
            int src = odd_nodes.get(odd_nodes.size() - 1);
            odd_nodes.remove(odd_nodes.size() - 1);

            double length = Double.MAX_VALUE;
            int closet = 0;

            for (int dst : odd_nodes) {
                if (src != dst && this.graph[src][dst] < length) {
                    length = this.graph[src][dst];
                    closet = dst;
                }
            }
            mst.add(new Edge(src, closet, length));
            odd_nodes.remove(odd_nodes.indexOf(closet));
        }
    }

    private List<Edge> remove_edge(List<Edge> prepro_mst, int node, int next) {

        Iterator<Edge> iter = prepro_mst.iterator();
        while (iter.hasNext()) {
            Edge edge = iter.next();
            if ((edge.getSrc() == next && edge.getDst() == node) || (edge.getSrc() == node && edge.getDst() == next)) {
                iter.remove();
            }
        }
        return prepro_mst;
    }

    private List<Integer> find_hamiltonian_cycle(List<Edge> prepro_mst) {

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
        eulerian_curcuit.add(neighbors.get(start).get(0));

        while (!prepro_mst.isEmpty()) {
            int i = 0;
            int node = 0;
            for (; i < eulerian_curcuit.size(); i++) {
                node = eulerian_curcuit.get(i);
                if (neighbors.get(node).size() > 0) {
                    break;
                }
            }

            while (neighbors.get(node).size() > 0) {
                int next = neighbors.get(node).get(0);

                remove_edge(prepro_mst, node, next);

                neighbors.get(node).remove(neighbors.get(node).indexOf(next));
                neighbors.get(next).remove(neighbors.get(next).indexOf(node));

                i++;

                eulerian_curcuit.add(i, next);
            }
        }

        return eulerian_curcuit;
    }

    private List<Integer> prune_circuit(List<Integer> hamiltonian_cycle) {

        List<Integer> circuit = new ArrayList<>();

        int cursor = hamiltonian_cycle.get(0);
        circuit.add(cursor);
        boolean[] check_visit = new boolean[hamiltonian_cycle.size()];
        check_visit[cursor] = true;

        for (int next : hamiltonian_cycle.subList(1, hamiltonian_cycle.size())) {
            if (!check_visit[next]) {
                circuit.add(next);
                check_visit[next] = true;
                cursor = next;
            }
        }
        circuit.add(circuit.get(0));

        return circuit;
    }

    private List<Integer> make_start_point_zero(List<Integer> circuit) {

        List<Integer> route = new ArrayList<>();

        int start = circuit.indexOf(0);

        route.addAll(circuit.subList(start, circuit.size()));
        route.addAll(circuit.subList(1, start));
        route.add(0);

        return route;
    }

    private double distance(List<Integer> route) {
        double distance = 0;

        for (int i = 0; i < route.size() - 1; i++) {
            distance += this.graph[route.get(i)][route.get(i + 1)];
        }

        return distance;
    }

}
