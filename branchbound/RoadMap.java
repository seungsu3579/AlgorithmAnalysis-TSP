import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class RoadMap {

    private double[][] map;

    public RoadMap(String filename, int pointNum) {

        map = drawMap(readPoints(filename, pointNum));

    }

    public static Point[] readPoints(String filename, int pointNum) {

        Point[] points = new Point[pointNum];

        try {
            File file = new File(filename);
            FileReader filereader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(filereader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(" ");

                int id = Integer.parseInt(data[0]);
                double xloc = Double.parseDouble(data[1]);
                double yloc = Double.parseDouble(data[2]);

                Point tmp_node = new Point(id, xloc, yloc);

                points[id] = tmp_node;
            }
            buffer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return points;
    }

    public static double[][] drawMap(Point[] points) {
        double[][] tmpMap = new double[points.length][points.length];

        for (int i = 0; i < points.length; i++) {
            for (int j = i; j < points.length; j++) {
                double dist = points[i].distance(points[j]);
                tmpMap[i][j] = dist;
                tmpMap[j][i] = dist;
            }
        }

        System.out.printf("+++++ Draw Distance Map +++++\n");
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                System.out.printf("%5.2f ", tmpMap[i][j]);
            }
            System.out.printf("\n");
        }

        return tmpMap;
    }

    public double[][] getMap() {
        return map;
    }

    public double getDistance(int src, int dst) {
        return map[src][dst];
    }

}

class Point {

    private int id;
    private double xloc;
    private double yloc;

    public Point(int id, double xloc, double yloc) {
        this.id = id;
        this.xloc = xloc;
        this.yloc = yloc;
    }

    public int getId() {
        return id;
    }

    public double getXloc() {
        return xloc;
    }

    public void setXloc(double xloc) {
        this.xloc = xloc;
    }

    public double getYloc() {
        return yloc;
    }

    public void setYloc(double yloc) {
        this.yloc = yloc;
    }

    public double distance(Point p) {
        return Math.sqrt((Math.pow(this.xloc - p.getXloc(), 2) + Math.pow(this.yloc - p.getYloc(), 2)));
    }
}
