

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class List_Interpolation extends Interpolator{
    private List<Point2D> data = null;
    
    public List_Interpolation(List<Point2D> data) { this.data = data; }

    public List_Interpolation() { data = new ArrayList<Point2D>(); }

    public List_Interpolation(Point2D[] data) {
        this();
        for (Point2D point : data)
            this.data.add(point);
    }

    @Override
    public void clear() { data.clear(); }

    @Override
    public int points_num() { return data.size(); }

    @Override
    public void add_point(Point2D point) { data.add(point); }

    @Override
    public Point2D get_point(int value) { return data.get(value); }

    @Override
    public void set_point(int value, Point2D point) { data.set(value, point); }

    @Override
    public void remove_last_point() { data.remove(data.size() - 1); }

    @Override
    public void sort() { Collections.sort(data); }
}
