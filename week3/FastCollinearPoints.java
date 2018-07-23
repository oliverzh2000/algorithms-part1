import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class FastCollinearPoints {
    private List<LineSegment> foundSegments = new ArrayList<>();
//    private HashMap<Double, List<LineSegment>> slopesToSegments = new HashMap<>();

    // finds all line foundSegments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(points);
        for (Point startPoint : points) {
            Arrays.sort(pointsCopy, startPoint.slopeOrder());
            List<Point> pointsOnSlope = new ArrayList<>();
            double currentSlope = 0;
            double prevSlope = Double.NEGATIVE_INFINITY;
            for (int i = 1; i < pointsCopy.length; i++) {
                Point currentPoint = pointsCopy[i];
                currentSlope = startPoint.slopeTo(currentPoint);
                if (currentSlope == prevSlope) {
                    pointsOnSlope.add(currentPoint);
                } else {
                    if (pointsOnSlope.size() >= 3) {
                        pointsOnSlope.add(startPoint);
                        addSegmentIfNew(prevSlope, createSegment(pointsOnSlope));
                    }
                    pointsOnSlope.clear();
                    pointsOnSlope.add(currentPoint);
                }
                prevSlope = currentSlope;
            }
            if (pointsOnSlope.size() >= 3) {
                pointsOnSlope.add(startPoint);
                addSegmentIfNew(currentSlope, createSegment(pointsOnSlope));
            }
        }
    }

    private LineSegment createSegment(List<Point> points) {
        return new LineSegment(Collections.min(points), Collections.max(points));
    }

    private void addSegmentIfNew(Double slope, LineSegment segment) {
        if (!foundSegments.contains(segment)) {
            foundSegments.add(segment);
        }
//        if (!slopesToSegments.containsKey(slope)) {
//            foundSegments.add(segment);
//        } else if (!slopesToSegments.get(slope).contains(segment)) {
//            foundSegments.add(segment);
//        }
//        slopesToSegments.putIfAbsent(slope, new ArrayList<>());
//        slopesToSegments.get(slope).add(segment);
    }

    // the number of line foundSegments
    public int numberOfSegments() {
        return foundSegments.size();
    }

    // the line foundSegments
    public LineSegment[] segments() {
        return foundSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        In in = new In("collinear/input8_test.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 30);
        StdDraw.setYscale(0, 30);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints f = new FastCollinearPoints(points);
        System.out.println(f.numberOfSegments() + " foundSegments");
        for (LineSegment segment : f.segments()) {
            segment.draw();
            System.out.println(segment);
        }
        StdDraw.show();
    }

    
}