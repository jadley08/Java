import org.junit.Test;

public class Testing {

    @Test
    public void basicLineValidIntersection() {
        Polygon.Line l1 = new Polygon.Line(new Polygon.Point(0, 0), new Polygon.Point(10, 10));
        Polygon.Line l2 = new Polygon.Line(new Polygon.Point(0, 10), new Polygon.Point(10, 0));

        assert l1.intersects(l2);
        assert l2.intersects(l1);
    }

    @Test
    public void lineInvalidIntersection() {
        Polygon.Line l1 = new Polygon.Line(new Polygon.Point(0, 0), new Polygon.Point(10, 10));
        Polygon.Line l2 = new Polygon.Line(new Polygon.Point(0, -10), new Polygon.Point(10, 0));

        assert !l1.intersects(l2);
        assert !l2.intersects(l1);
    }

    @Test
    public void basicPolygonOverlapNotNull() {
        Polygon p1 = new Polygon(new Polygon.Point(0, 2), new Polygon.Point(2, 0), new Polygon.Point(0, -2), new Polygon.Point(-2, 0));
        Polygon p2 = new Polygon(new Polygon.Point(3, 1), new Polygon.Point(3, -1), new Polygon.Point(-3, -1), new Polygon.Point(-3, 1));

        assert p1.overlaps(p2) != null;
    }

    @Test
    public void polygonOffsetPoints() {
        Polygon p = new Polygon(new Polygon.Point(0, 2), new Polygon.Point(2, 0), new Polygon.Point(0, -2), new Polygon.Point(-2, 0));
        p.offsetPoints(100, 200);
        int[] xs = p.getxPoints();
        int[] ys = p.getyPoints();
        assert xs[0] == 50;
        assert xs[1] == 52;
        assert xs[2] == 50;
        assert xs[3] == 48;
        assert ys[0] == 102;
        assert ys[1] == 100;
        assert ys[2] == 98;
        assert ys[3] == 100;
    }

    @Test
    public void polygonScalePoints() {
        Polygon p = new Polygon(new Polygon.Point(0, 2), new Polygon.Point(2, 0), new Polygon.Point(0, -2), new Polygon.Point(-2, 0));
        p.scalePoints(100);
        int[] xs = p.getxPoints();
        int[] ys = p.getyPoints();
        assert xs[0] == 0;
        assert xs[1] == 200;
        assert xs[2] == 0;
        assert xs[3] == -200;
        assert ys[0] == 200;
        assert ys[1] == 0;
        assert ys[2] == -200;
        assert ys[3] == 0;
    }
}
