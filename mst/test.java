import java.util.List;

public class test {

    public static void main(String[] args) {

        int node_num = 100;
        String filename = String.format("./%d.tsp", node_num);

        Graph graph = new Graph(filename, node_num);

        MSTSolver sv = new MSTSolver(graph);

        // solve by christofides algorithm (1.5)
        sv.christofides();
        double distance = sv.getDistance();
        List<Integer> route = sv.getRoute();

        System.out.printf("Distance : %.5f\n", distance);
        System.out.printf("Finded route : %s\n", route);

    }
}
