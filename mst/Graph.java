package mst;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Graph {

    private double[][] graph;
    private int nodeNum;

    public Graph(String filename, int pointNum) {
        graph = drawGraph(readPoints(filename, pointNum));
        this.nodeNum = pointNum;
    }

    public static Point2[] readPoints(String filename, int pointNum) {

        Point2[] points = new Point2[pointNum];

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

                Point2 tmp_node = new Point2(id, xloc, yloc);

                points[id] = tmp_node;
            }
            buffer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return points;
    }

    public static double[][] drawGraph(Point2[] points) {
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

class Point2 {

    private int id;
    private double xloc;
    private double yloc;

    public Point2(int id, double xloc, double yloc) {
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

    public double distance(Point2 p) {
        return Math.sqrt((Math.pow(this.xloc - p.getXloc(), 2) + Math.pow(this.yloc - p.getYloc(), 2)));
    }
}
