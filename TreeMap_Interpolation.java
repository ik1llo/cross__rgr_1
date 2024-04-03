
import java.util.TreeMap;

public class TreeMap_Interpolation extends Interpolator {
    private TreeMap<Integer,Point2D> map;

    public TreeMap_Interpolation() { this.map = new TreeMap<>(); }

    @Override
    public void clear() { map.clear(); }

    @Override
    public int points_num() { return map.size(); }

    @Override
    public void add_point(Point2D point) {
        if (map.isEmpty())
            map.put(0, point);
        else
            map.put(get_last_index() + 1, point);
    }

    @Override
    public Point2D get_point(int value) { return map.get(value); }

    private Integer get_last_index() { return map.lastKey(); }

    @Override
    public void set_point(int value, Point2D point) { map.replace(value, point); }

    @Override
    public void remove_last_point() { map.remove(get_last_index()); }

    @Override
    public void sort() {
        for (int k = 0; k < points_num(); k++) {
            for (int j = 0; j < points_num() - 1 - k; j++) {
                if (get_point(j).get_x() >= get_point(j + 1).get_x()){
                    var temp = get_point(j);

                    map.replace(j, get_point(j + 1));
                    map.replace(j + 1, temp);
                }
            }
        }
    }
}