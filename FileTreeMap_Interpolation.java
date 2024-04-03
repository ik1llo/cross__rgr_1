

import java.io.*;
import java.util.StringTokenizer;

public class FileTreeMap_Interpolation extends TreeMap_Interpolation {
    public FileTreeMap_Interpolation() { super(); }

    public void read_from_file(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s = in.readLine();

        clear();

        while ((s = in.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);

            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());

            add_point(new Point2D(x, y));
        }

        in.close();
    }

    public void write_to_file(String filename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        out.printf("%9s%25s\n", "x", "y");

        for (int value = 0; value < points_num(); value++) {
            out.println(get_point(value).get_x() + "\t" + get_point(value).get_y());
        }

        out.close();
    }
}