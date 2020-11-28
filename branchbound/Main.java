public class Main {

    public static void main(String[] args) {

        int node_num = 10;
        String filename = String.format("./%d.tsp", node_num);

        RoadMap map = new RoadMap(filename, node_num);

        Solver sv1 = new Solver();
        Solver sv2 = new Solver();
        // sv.branchAndBound(map.getMap());
        sv1.branchAndBound(map.getMap());
        sv2.bruteforce(map.getMap());

        System.out.println("+++++ branch & bound +++++");
        System.out.println(sv1.getMinDistance());
        System.out.println(sv1.getOptRoute());
        System.out.println();
        System.out.println("+++++ brute force +++++");
        System.out.println(sv2.getMinDistance());
        System.out.println(sv2.getOptRoute());

    }
}