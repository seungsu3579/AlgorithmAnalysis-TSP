import basic_algorithm.*;
import mst.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // test brute force in TSP
        // test_bruteforce();

        // test branch and bound in TSP
        // test_branch_and_bound();

        // test Christopides algorithm in TSP
        test_christofides();

    }

    public static void test_bruteforce() {

        int node_num = 100;
        String filename = String.format("./%d.tsp", node_num);

        RoadMap map = new RoadMap(filename, node_num);

        Solver sv = new Solver();

        sv.bruteforce(map.getMap());
        System.out.println("+++++ brute force +++++");
        System.out.println(sv.getMinDistance());
        System.out.println(sv.getOptRoute());

    }

    public static void test_branch_and_bound() {

        int node_num = 100;
        String filename = String.format("./%d.tsp", node_num);

        RoadMap map = new RoadMap(filename, node_num);

        Solver sv = new Solver();

        sv.branchAndBound(map.getMap());

        System.out.println("+++++ branch & bound +++++");
        System.out.println(sv.getMinDistance());
        System.out.println(sv.getOptRoute());
        System.out.println();
    }

    public static void test_christofides() {

        int node_num = 2000;
        String filename = String.format("./%d.tsp", node_num);

        Graph graph = new Graph(filename, node_num);

        MSTSolver sv = new MSTSolver(graph);

        // solve by christofides algorithm (1.5)
        sv.christofides();
        double distance = sv.getDistance();
        List<Integer> route = sv.getRoute();

        System.out.println("+++++ Christofides +++++");
        System.out.printf("%.5f\n", distance);
        System.out.printf("%s\n", route);

    }
}