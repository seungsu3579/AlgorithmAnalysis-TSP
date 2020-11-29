import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;

public class Graph {

    private double[][] graph;
    private int nodeNum;

    public Graph(String filename, int pointNum) {
        graph = drawGraph(readPoints(filename, pointNum));
        this.nodeNum = pointNum;
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

    public static double[][] drawGraph(Point[] points) {
        double[][] tmpMap = new double[points.length][points.length];

        for (int i = 0; i < points.length; i++) {
            for (int j = i; j < points.length; j++) {
                double dist = points[i].distance(points[j]);
                tmpMap[i][j] = dist;
                tmpMap[j][i] = dist;
            }
        }
        return tmpMap;
    }

    public double[][] getGraph() {
        return graph;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public double getDistance(int src, int dst) {
        return graph[src][dst];
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
