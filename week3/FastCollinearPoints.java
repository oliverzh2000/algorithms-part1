import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null || points[0] == null) {
            throw new IllegalArgumentException("points is null");
        }
        Arrays.sort(points);
        Point prevPoint = points[points.length - 1];
        for (Point point : points) {
            if (point == null || point.compareTo(prevPoint) == 0) {
                throw new IllegalArgumentException("null point or duplicate");
            }
            prevPoint = point;
        }

        for (Point startPoint : points) {
            Point[] pointsCopy = Arrays.copyOf(points, points.length);
            Arrays.sort(pointsCopy, startPoint.slopeOrder()); // Then sort by slope.

            int sameSlopeCount = 0;
            double lastSlope = pointsCopy[0].slopeTo(startPoint);
            for (Point otherPoint : pointsCopy) {
                if (otherPoint.slopeTo(startPoint) == lastSlope) {
                    sameSlopeCount++;
                }
                lastSlope = otherPoint.slopeTo(startPoint);
                if (sameSlopeCount >= 3) {
                    segments.add(new LineSegment(startPoint, otherPoint));
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }
}