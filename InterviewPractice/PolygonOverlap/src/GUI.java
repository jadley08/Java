import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int STROKE_WIDTH = 3;

    ArrayList<Polygon> polygons;

    public GUI(ArrayList<Polygon> polygons) {
        super("Polygon Intersection");

        this.polygons = polygons;

        setContentPane(new DrawPane());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);

        setVisible(true);
    }

    class DrawPane extends  JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(STROKE_WIDTH));

            // draw all of the polygons
            g2.setColor(Color.BLACK);
            for (Polygon p : polygons) {
                g2.drawPolygon(p.getxPoints(), p.getyPoints(), p.getNumPoints());
            }

            // mark all intersecting lines
            g2.setColor(Color.RED);
            for (int i = 0; i < polygons.size(); i++) {
                for (int j = 0; j < polygons.size(); j++) {
                    if (i != j) {
                        for (Polygon.Tuple<Polygon.Line, Polygon.Line> tup : polygons.get(i).overlaps(polygons.get(j))) {
                            Polygon.Line first_line = tup.getFirst();
                            Polygon.Line second_line = tup.getSecond();
                            g2.drawLine(first_line.getP1().getX(), tup.getFirst().getP1().getY(),
                                    first_line.getP2().getX(), tup.getFirst().getP2().getY());
                            g2.drawLine(second_line.getP1().getX(), tup.getSecond().getP1().getY(),
                                    second_line.getP2().getX(), tup.getSecond().getP2().getY());
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Polygon> polygons = new ArrayList<>();
        int scale = 50;

        Polygon p1 = new Polygon(new Polygon.Point(0, 4), new Polygon.Point(2, 0), new Polygon.Point(0, -2), new Polygon.Point(-2, 0));
        Polygon p2 = new Polygon(new Polygon.Point(3, 1), new Polygon.Point(3, -1), new Polygon.Point(-3, -1), new Polygon.Point(-3, 1));
        Polygon p3 = new Polygon(new Polygon.Point(0, 3), new Polygon.Point(1, 5), new Polygon.Point(1, 6), new Polygon.Point(4, 7),
                new Polygon.Point(-3, 6), new Polygon.Point(-4, 6), new Polygon.Point(-4, 2), new Polygon.Point(-2, 4));
        polygons.add(p1);
        polygons.add(p2);
        polygons.add(p3);

        for (Polygon p : polygons) {
            p.scalePoints(scale);
            p.offsetPoints(WIDTH, HEIGHT);
        }

        new GUI(polygons);
    }
}
