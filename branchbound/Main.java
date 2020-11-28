public class Main {

    public static void main(String[] args) {

        int node_num = 100;
        String filename = String.format("./%d.tsp", node_num);

        RoadMap map = new RoadMap(filename, node_num);

        Solver sv = new Solver();
        sv.branchAndBound(map.getMap());

        System.out.println(sv.getMinDistance());
        System.out.println(sv.getOptRoute());

    }
}