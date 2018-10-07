import java.util.ArrayList;

public class Polygon {

    private static int max(int m, int n) {
        if (m >= n) {
            return m;
        }
        else {
            return m;
        }
    }

    private static int min(int m, int n) {
        if (m <= n) {
            return m;
        }
        else {
            return n;
        }
    }

    public static class Point {
        private int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        @Override
        public String toString() {
            return "(" + this.getX() + ", " + this.getY() + ")";
        }

        public boolean equals(Point other) {
            return this.getX() == other.getX() && this.getY() == other.getY();
        }
    }

    public class Tuple<X, Y> {
        private final X x;
        private final Y y;

        public Tuple (X x, Y y) {
            this.x = x;
            this.y = y;
        }

        public X getFirst() {
            return this.x;
        }

        public Y getSecond() {
            return this.y;
        }

        @Override
        public String toString() {
            return "<" + x + ", " + y + ">";
        }
    }

    public static class Line {
        private Point p1, p2;

        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public Point getP1() {
            return this.p1;
        }

        public Point getP2() {
            return this.p2;
        }

        public boolean equals(Line other) {
            return (this.p1 == other.p1 && this.p2 == other.p2) || (this.p1 == other.p2 && this.p2 == other.p1);
        }

        @Override
        public String toString() {
            return "{(" + p1.getX() + ", " + p1.getY() + "), (" + p2.getX() + ", " + p2.getY() + ")}";
        }

        public boolean onSegment(Point p) {
            return (p.getX() <= max(this.p1.getX(), this.p2.getX()) && p.getX() >= min(this.p1.getX(), this.p2.getX()) &&
                    p.getY() <= max(this.p1.getY(), this.p2.getY()) && p.getY() >= min(this.p1.getY(), this.p2.getY()));
        }

        // 0 : co-linear
        // 1 : clockwise
        // 2 : counter-clockwise
        public int orientation(Point p) {
            int val = ((p.getY() - this.p1.getY()) * (this.p2.getX() - p.getX())) -
                    ((p.getX() - this.p1.getX()) * (this.p2.getY() - p.getY()));

            if (val == 0) {
                return 0;
            }
            else if (val > 0){
                return 1;
            }
            else {
                return 2;
            }
        }

        public boolean intersects(Line other) {
            int orientation1 = this.orientation(other.p1);
            int orientation2 = this.orientation(other.p2);
            int orientation3 = other.orientation(this.p1);
            int orientation4 = other.orientation(this.p2);

            if (orientation1 != orientation2 && orientation3 != orientation4) {
                return true;
            }

            if (orientation1 == 0 && this.onSegment(other.p1)) {
                return true;
            }

            if (orientation2 == 0 && this.onSegment(other.p2)) {
                return true;
            }

            if (orientation3 == 0 && other.onSegment(this.p1)) {
                return true;
            }

            if (orientation4 == 0 && other.onSegment(this.p2)) {
                return true;
            }

            return false;
        }
    }

    private ArrayList<Point> points;
    private ArrayList<Line> lines;
    private int[] xPoints;
    private int[] yPoints;

    public Polygon(Point ...points) {
        this.points = new ArrayList<>();
        for (Point p : points) {
            this.points.add(p);
        }

        int n = this.getNumPoints();
        this.xPoints = new int[n];
        this.yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            Point cur_point = this.points.get(i);
            this.xPoints[i] = cur_point.getX();
            this.yPoints[i] = cur_point.getY();
        }
    }

    public ArrayList<Point> getPoints() {
        this.points = new ArrayList<>();
        for (int i = 0; i < getNumPoints(); i++) {
            this.points.add(new Point(this.xPoints[i], this.yPoints[i]));
        }
        return this.points;
    }

    public ArrayList<Line> getLines() {
        this.lines = new ArrayList<>();
        int n = getNumPoints();
        for (int i = 0; i < n; i++) {
            if (i > 0 && i < n - 1) {
                lines.add(new Line(new Point(this.xPoints[i - 1], this.yPoints[i - 1]), new Point(this.xPoints[i], this.yPoints[i])));
            }
            else if (i == n - 1) {
                lines.add(new Line(new Point(this.xPoints[i], this.yPoints[i]), new Point(this.xPoints[0], this.yPoints[0])));
            }
        }
        return this.lines;
    }

    public int[] getxPoints() {
        return this.xPoints;
    }

    public int[] getyPoints() {
        return this.yPoints;
    }

    public int getNumPoints() {
        return this.points.size();
    }

    @Override
    public String toString() {
        ArrayList<Point> pts = getPoints();
        StringBuilder sb = new StringBuilder();
        int n = pts.size();
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                sb.append("[");
            }
            sb.append(pts.get(i));
            if (i < n - 1) {
                sb.append(", ");
            }
            else {
                sb.append("]");
            }
        }
        return sb.toString();
    }

    public void offsetPoints(int frame_width, int frame_height) {
        for (int i = 0; i < getNumPoints(); i++) {
            this.xPoints[i] = this.xPoints[i] + (frame_width / 2);
            this.yPoints[i] = this.yPoints[i] + (frame_height / 2);
        }
    }

    public void scalePoints(int scale) {
        for (int i = 0; i < getNumPoints(); i++) {
            this.xPoints[i] = this.xPoints[i] * scale;
            this.yPoints[i] = this.yPoints[i] * scale;
        }
    }

    public ArrayList<Tuple<Line, Line>> overlaps(Polygon other) {
        ArrayList<Tuple<Line, Line>> overlapping_lines = new ArrayList<>();
        ArrayList<Line> this_lines = this.getLines();
        ArrayList<Line> other_lines = other.getLines();

        for (Line this_line : this_lines) {
            for (Line other_line : other_lines) {
                if (this_line.intersects(other_line)) {
                    overlapping_lines.add(new Tuple<>(this_line, other_line));
                }
            }
        }

        return overlapping_lines;
    }

}
