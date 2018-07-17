import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k]) &&
                                pointsCopy[i].slopeTo(pointsCopy[k]) == pointsCopy[i].slopeTo(pointsCopy[l])) {
                            segments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                        }
                    }
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

    public static void main(String[] args) {
        BruteCollinearPoints b = new BruteCollinearPoints(new Point[]{new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)});
        System.out.println(b.numberOfSegments());
    }
}
