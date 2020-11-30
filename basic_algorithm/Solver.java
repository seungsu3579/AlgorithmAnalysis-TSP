package basic_algorithm;

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

        int p_count = 0;
        int s_count = 0;

        // set start point
        Node root = new Node(1);
        root.addRoute(0);
        root.setBound(bound(root, map));
        pq.add(root);

        while (!pq.isEmpty()) {

            Node cursor = pq.poll();

            // branch and bound
            if (cursor.getBound() < minDistance) {
                s_count++;
                for (int dst = 1; dst < map.length; dst++) {
                    if (cursor.isPassed(dst)) {
                        continue;
                    }

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
                            System.out.printf("search : %35s | bound : %.2f | opt_r : %35s | opt_l : %.2f\n",
                                    next.getRoutes(), next.getBound(), this.optRoute, this.minDistance);
                        }
                    } else {
                        next.setBound(bound(next, map));
                        if (next.getBound() < minDistance) {
                            pq.add(next);
                        }
                    }

                }
            } else {
                p_count++;
            }
            System.out.printf("pass : %d  | search : %d \r", p_count, s_count);
            // if (this.minDistance == Double.MAX_VALUE) {
            // System.out.printf("search : %35s | bound : %.2f | opt_r : not found | opt_l :
            // not found \r",
            // cursor.getRoutes(), cursor.getBound(), this.optRoute);
            // } else {
            // System.out.printf("search : %35s | bound : %.2f | opt_r : %35s | opt_l : %.2f
            // \r", cursor.getRoutes(),
            // cursor.getBound(), this.optRoute, this.minDistance);
            // }
        }
        System.out.println();

    }

    public void bruteforce(double[][] map) {

        PriorityQueue<Node> pq = new PriorityQueue<>();

        // set start point
        Node root = new Node(1);
        root.addRoute(0);
        root.setBound(bound(root, map));
        pq.add(root);

        while (!pq.isEmpty()) {

            Node cursor = pq.poll();

            for (int dst = 1; dst < map.length; dst++) {
                if (cursor.isPassed(dst))
                    continue;

                Node next = new Node(cursor.getStage() + 1);
                next.setParent(cursor);
                next.copyRoutes(cursor);
                next.addRoute(dst);

                if (next.getStage() == map.length) {
                    next.addRoute(0);
                    double d = next.totalDistance(map);
                    if (this.minDistance > d) {
                        this.minDistance = d;
                        this.optRoute = next.getRoutes();
                    }
                } else {
                    pq.add(next);
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