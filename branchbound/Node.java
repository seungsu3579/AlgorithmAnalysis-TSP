import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Node implements Comparable<Node> {

    private int stage;
    private double bound;
    private Node parent;
    private Stack<Integer> routes;
    private List<Node> children;

    public Node() {
        this.routes = new Stack<Integer>();
        this.children = new ArrayList<>();
        this.bound = 0;
    }

    public Node(int stage) {
        this.stage = stage;
        this.routes = new Stack<Integer>();
        this.children = new ArrayList<>();
        this.bound = 0;
    }

    public int getStage() {
        return stage;
    }

    public Node getParent() {
        if (this.parent == null) {
            Node tmp = new Node();
            tmp.setBound(0);
            return tmp;
        }
        return parent;
    }

    @Override
    public String toString() {
        return String.format("%s", this.routes)
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.bound, other.bound);
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setRoutes(Stack<Integer> routes) {
        this.routes = routes;
    }

    public Stack<Integer> getRoutes() {
        return routes;
    }

    public void addRoute(int id) {
        this.routes.push(id);
    }

    public void rmRoute(int id) {
        this.routes.remove(id);
    }

    public double getBound() {
        return bound;
    }

    public void setBound(double bound) {
        this.bound = bound;
    }

    public void addBound(double bound) {
        this.bound += bound;
    }

    public int getLast() {
        return this.routes.peek();
    }

    public boolean isPassed(int id) {
        return this.routes.contains(id);
    }

    public boolean isVisited(int id) {
        if (this.routes.peek() == id)
            return false;
        return this.routes.contains(id);
    }

    // input
    // int num : total number of points
    // output
    // -1 : when the left node is not only one.
    // node_id : node that not visited yet
    // time complexity : O(N)
    public int findLastPoint(int num) {
        int id = -1;
        int count = 0;
        if (this.routes.size() == num - 1) {
            for (int i = 0; i < num; i++) {
                count += i;
            }
            for (int tmp : this.routes) {
                count -= tmp;
            }
            id = count;
        }
        return id;
    }

    public Node copyRoutes(Node node) {
        for (int i = 0; i < node.routes.size(); i++) {
            this.addRoute(node.routes.get(i));
        }
        return node;
    }

    public double totalDistance(double[][] map) {
        double distance = 0;
        for (int i = 0; i < this.routes.size() - 1; i++) {
            double tmp = map[this.routes.get(i)][this.routes.get(i + 1)];
            distance += tmp;
        }
        return distance;
    }

}