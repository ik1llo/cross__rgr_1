import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Application extends JFrame {
    private final JTextField textField_a;
    private JTextField textField_fun;
    private final JTextField textField_start;
    private final JTextField textField_step;
    private final JTextField textField_stop;
    private final JTextField textField_derivative;

    private FFunction function;

    private XYSeries series;
    private XYSeries derivative;

    private double start = 9;
    private double stop = -9;
    private double step = 0.01;
    private double a = 1;

    private Thread calculation;

    private boolean is_calculation = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Application frame = new Application();
                frame.setVisible(true);
            } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    public Application() {
        setResizable(false);
        setTitle("RGR #1 - KS24 - Kachanov O. M.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        JPanel content_panel = new JPanel();
        content_panel.setBackground(hex_to_color("#FFE28A"));
        content_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        content_panel.setLayout(new BorderLayout(0, 0));

        setContentPane(content_panel);

        JPanel buttons_panel = new JPanel();
        buttons_panel.setBackground(hex_to_color("#EFD177"));

        content_panel.add(buttons_panel, BorderLayout.SOUTH);
        JButton newButton_plot = new JButton("Plot");
        newButton_plot.setBackground(hex_to_color("#C49C20"));

        newButton_plot.addActionListener(e -> {
            is_calculation = false;
            if(!calculation.isAlive()) {
                init_values();

                function.set_a(a);
                function.set_fun(textField_fun.getText());

                calculate_plot();
            }
        });

        buttons_panel.add(newButton_plot);

        JButton newButton_exit = new JButton("Exit");
        newButton_exit.setBackground(hex_to_color("#C49C20"));
        newButton_exit.addActionListener(e -> System.exit(0));

        buttons_panel.add(newButton_exit);

        buttons_panel.add(new JLabel("[start]:"));
        buttons_panel.add((textField_start = new JTextField("-10.0", 5)));
        buttons_panel.add(new JLabel("[stop]:"));
        buttons_panel.add((textField_stop = new JTextField("10.0", 5)));
        buttons_panel.add(new JLabel("[step]:"));
        buttons_panel.add((textField_step = new JTextField("0.01", 5)));

        JPanel panel_data = new JPanel();
        panel_data.setBackground(hex_to_color("#EFD177"));

        content_panel.add(panel_data, BorderLayout.NORTH);

        JLabel a_label = new JLabel("[a]:");
        panel_data.add(a_label);

        textField_a = new JTextField("1.0");
        textField_fun = new JTextField("cos(x)");

        panel_data.add(textField_a);

        JLabel label_fun = new JLabel("[fun]:");
        panel_data.add(label_fun);
        panel_data.add(textField_fun);

        textField_derivative = new JTextField(20);
        panel_data.add(new JLabel("[del]:"));
        panel_data.add(textField_derivative);

        JButton interpolator_button = new JButton("Interpolator");
        interpolator_button.setBackground(hex_to_color("#C49C20"));
        interpolator_button.addActionListener(e -> {

            FileList_Interpolation fun = new FileList_Interpolation();

            fun.clear();
            try { fun.read_from_file("data.dat"); }
            catch (IOException ex) {
                ex.printStackTrace();
                System.exit(-1);
            }

            fun.sort();
            interpolation(fun);
        });

        buttons_panel.add(interpolator_button);

        textField_a.setColumns(10);
        textField_fun.setColumns(10);

        JFreeChart chart = create_chart();
        ChartPanel chart_panel = new ChartPanel(chart);
        chart_panel.setBackground(hex_to_color("#FFE28A"));


        content_panel.add(chart_panel, BorderLayout.CENTER);
    }

    private void interpolation(Interpolator fun) {
        is_calculation = false;
        if (!calculation.isAlive()) {
            init_values();
            calculation = new Thread(() -> {
                is_calculation = true;

                series.clear();
                derivative.clear();

                for (double x = start; x < stop && is_calculation; x += step) { series.add(x, fun.evalf(x)); }

                is_calculation = false;
            });

            calculation.start();
        }
    }

    private JFreeChart create_chart() {
        FileList_Interpolation fun = new FileList_Interpolation();

        double x;
        for (int k = -9; k < 10; k += 1) {
            x = Math.random() * 20 - 10;
            System.out.println(Math.sin(x));
            fun.add_point(new Point2D(x, Math.sin(x)));
        }

        try { fun.write_to_file("data.dat"); } 
        catch (IOException ex) { ex.printStackTrace(); }

        series = new XYSeries("Function");
        derivative = new XYSeries("Derivative");

        init_values();

        function = new FFunction(textField_fun.getText(), a);

        calculate_plot();
        
        XYSeriesCollection dataset = new XYSeriesCollection();

        dataset.addSeries(series);
        dataset.addSeries(derivative);

        return ChartFactory.createXYLineChart("function graphs", "x", "y", dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    private void init_values() {
        try {
            start = Double.parseDouble(textField_start.getText());
            stop = Double.parseDouble(textField_stop.getText());
            step = Double.parseDouble(textField_step.getText());
            a = Double.parseDouble(textField_a.getText());
        } catch (NumberFormatException ex) {}
    }

    private void calculate_plot() {
        textField_derivative.setText(function.get_der());
        calculation = new Thread(() -> {
            is_calculation = true;

            series.clear();
            derivative.clear();

            for (double x = start; x < stop && is_calculation; x += step) {
                derivative.add(x, function.derivative(x));
                series.add(x, function.evalf(x));
            }

            is_calculation = false;
        });

        calculation.start();
    }

    private static Color hex_to_color(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16)
        );
    }

}