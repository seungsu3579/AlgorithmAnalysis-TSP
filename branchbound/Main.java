public class Main {

    public static void main(String[] args) {

        RoadMap map = new RoadMap("./10.tsp", 10);

        Solver sv = new Solver();
        sv.branchAndBound(map.getMap());

    }
}