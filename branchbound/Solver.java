import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Solver {

    private double minDistance;
    private List<Integer> optRoute;

    public Solver() {
        this.minDistance = Double.MAX_VALUE;
        this.optRoute = new ArrayList<>();
    }

    public double getMinDistance() {
        return minDistance;
    }

    public List<Integer> getOptRoute() {
        return optRoute;
    }

    public void branchAndBound(double[][] map) {

        PriorityQueue<Node> pq = new PriorityQueue<>();

        // set start point
        Node root = new Node(1);
        root.addRoute(0);
        root.setBound(bound(root, map));
        pq.add(root);

        while (!pq.isEmpty()) {

            Node cursor = pq.poll();

            // branch and bound
            if (cursor.getBound() < minDistance) {
                for (int dst = 1; dst < map.length; dst++) {
                    if (cursor.isVisited(dst))
                        continue;

                    Node next = new Node(cursor.getStage() + 1);
                    next.setParent(cursor);
                    next.copyRoutes(cursor);
                    next.addRoute(dst);

                    // when cursor is leaf
                    if (next.getStage() == map.length - 1) {
                        int id = next.findLastPoint(map.length);
                        next.addRoute(id);

                        // return back to start point
                        next.addRoute(0);

                        double d = next.totalDistance(map);
                        if (d < minDistance) {
                            minDistance = d;
                            optRoute = next.getRoutes();
                        }
                    } else {
                        next.setBound(bound(next, map));
                        if (next.getBound() < minDistance) {
                            pq.add(next);
                        }
                    }

                }
            }
        }

    }

    public double bound(Node node, double[][] map) {

        double bound = node.getParent().getBound();
        for (int src = 0; src < map.length; src++) {
            double tmp = Double.MAX_VALUE;
            if (node.isVisited(src))
                continue;
            for (int dst = 0; dst < map.length; dst++) {
                if (src == dst)
                    continue;
                else if (node.isPassed(dst))
                    continue;
                else if (dst == 1 && src == node.getLast())
                    continue;
                else if (tmp > map[src][dst])
                    tmp = map[src][dst];
            }
            bound += tmp;
        }
        return bound;
    }
}