
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSet_Interpolation extends Interpolator {
    private final TreeSet<Point2D> set;

    public TreeSet_Interpolation() { this.set = new TreeSet<>(); }

    public void clear() { set.clear(); }

    @Override
    public int points_num() { return set.size(); }

    public void add_point(Point2D point) { set.add(point); }

    @Override
    public Point2D get_point(int value) {
        var iterator = get_iterator();
        for (int count = 0; iterator.hasNext() && count < value; count++) { iterator.next(); }

        if (iterator.hasNext()) return iterator.next();
        else return null;
    }

    @Override
    public void set_point(int value, Point2D point) {
        var iterator = get_iterator();
        for (int count = 0; iterator.hasNext() && count < value; count++) { iterator.next(); }

        var old_point = iterator.next();
        old_point.set_x(point.get_x());
        old_point.set_y(point.get_y());
    }

    @Override
    public void remove_last_point() { set.pollLast(); }

    @Override
    public void sort() {}

    public Iterator<Point2D> get_iterator() { return set.iterator(); }
}
