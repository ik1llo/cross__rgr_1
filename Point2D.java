

import java.util.*;

public class Point2D extends Point implements Comparable<Point2D> {
    public Point2D(double x, double y) {
        super(2);

        set_coord(1, x);
        set_coord(2, y);
    }

    public Point2D(){ this(0, 0); }

    public double get_x() { return get_coord(1); }

    public double get_y() { return get_coord(2); }

    public void set_x(double x) { set_coord(1, x); }

    public void set_y(double y) { set_coord(2, y); }

    @Override
    public int compareTo(Point2D point) { return Double.compare(get_x(), point.get_x()); }

    public static void main(String[] args) {
        List<Point2D> data = new ArrayList<Point2D>();

        int number;
        double x;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("[points amount]: ");
            number = in.nextInt();
        } while (number <= 0);

        for (int k = 0; k < number; k++) {
            x = 1.0 + (5.0 - 1.0) * Math.random();
            data.add(new Point2D(x, Math.sin(x)));
        }

        System.out.println("[unsorted data]: ");
        for (Point2D point : data)
            System.out.println(point);

        System.out.println("\n[sorted data]: ");
        Collections.sort(data);
        for (Point2D point : data)
            System.out.println("x = " + point.get_x() + "\ty = " + point.get_y());

        in.close();
    }
}
