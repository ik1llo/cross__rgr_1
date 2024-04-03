
public abstract class Point {
    private double[] coords = null;

    public Point(int number) { this.coords = new double[number]; }

    public double get_coord(int number) { return coords[number - 1]; }

    public void set_coord(int number, double x) { coords[number - 1] = x; }

    @Override
    public String toString() {
        String res = "(";
        for (double x : coords){ res += x + ", "; }
        return res.substring(0, res.length() - 2) + ")";
    }
}
