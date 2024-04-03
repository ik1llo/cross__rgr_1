
public abstract class Interpolator implements Evaluatable {
    abstract public void clear();
    abstract public int points_num();

    abstract public void add_point(Point2D point);
    abstract public Point2D get_point(int value);
    abstract public void set_point(int value, Point2D point);

    abstract public void remove_last_point();
    abstract public void sort();

    @Override
    public double evalf(double x) {
        double result = 0.0;

        int num_data = points_num();

        double numer;
        double denom;

        for (int k = 0; k < num_data; k++) {
            numer = 1.0;
            denom = 1.0;

            for (int j = 0; j < num_data; j++) {
                if (j != k) {
                    numer = numer * (x - get_point(j).get_x());
                    denom = denom * (get_point(k).get_x() - get_point(j).get_x());
                }
            }

            result = result + get_point(k).get_y() * numer / denom;
        }

        return result;
    }
}
