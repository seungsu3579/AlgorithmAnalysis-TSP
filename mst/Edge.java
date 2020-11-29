class Edge implements Comparable<Edge> {
    private int src;
    private int dst;
    private double weight;

    public Edge(int src, int dst, double weight) {
        this.src = src;
        this.dst = dst;
        this.weight = weight;
    }

    public int getDst() {
        return dst;
    }

    public int getSrc() {
        return src;
    }

    public double getWeight() {
        return weight;
    }

    public void setDst(int dst) {
        this.dst = dst;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String toString() {
        return String.format("(%d : %.2f)", dst, weight);
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}