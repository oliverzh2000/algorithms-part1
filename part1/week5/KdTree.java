import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private static final boolean DIR_X = true;
    private static final boolean DIR_Y = false;
    private Node root;
    private int size;

    private class Node {
        private Point2D point;
        private Node left, right;

        public Node(Point2D point, boolean dir) {
            this.point = point;
        }

        private int compareInDirection(boolean dir, Point2D p) {
            if (dir == DIR_X) return Point2D.X_ORDER.compare(point, p);
            return Point2D.Y_ORDER.compare(point, p);
        }
    }

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = put(root, p, DIR_X);
    }

    private Node put(Node node, Point2D p, boolean dir) {
        if (node == null) {
            size++;
            return new Node(p, dir);
        }
        if (node.point.equals(p)) return node;
        if (node.compareInDirection(dir, p) > 0) node.left = put(node.left, p, !dir);
        else node.right = put(node.right, p, !dir);
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return contains(root, p, DIR_X);
    }

    private boolean contains(Node node, Point2D p, boolean dir) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;
        if (node.compareInDirection(dir, p) > 0) return contains(node.left, p, !dir);
        else return contains(node.right, p, !dir);
    }

    // draw all points to standard draw
    public void draw() {
        drawNodes(root, DIR_X, 0, 1, 0, 1);
    }

    private void drawNodes(Node node, boolean dir, double minX, double maxX, double minY, double maxY) {
        if (node == null) return;
        StdDraw.setPenRadius();
        if (dir == DIR_X) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), minY, node.point.x(), maxY);
            for (Node child : new Node[]{node.left, node.right}) {
                if (child != null) {
                    if (child.point.x() < node.point.x()) {
                        drawNodes(child, !dir, minX, node.point.x(), minY, maxY);
                    } else {
                        drawNodes(child, !dir, node.point.x(), maxX, minY, maxY);
                    }
                }
            }
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX, node.point.y(), maxX, node.point.y());
            for (Node child : new Node[]{node.left, node.right}) {
                if (child != null) {
                    if (child.point.y() < node.point.y()) {
                        drawNodes(child, !dir, minX, maxX, minY, node.point.y());
                    } else {
                        drawNodes(child, !dir, minX, maxX, node.point.y(), maxY);
                    }
                }
            }
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.point.x(), node.point.y());
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> pointsInRange = new LinkedList<>();
        explore(root, rect, pointsInRange, DIR_X);
        return pointsInRange;
    }

    private void explore(Node node, RectHV rect, LinkedList<Point2D> pointsInRange, boolean dir) {
        if (node == null) return;
        double rangeMin, rangeMax, pointVal;
        if (dir == DIR_X) {
            rangeMin = rect.xmin();
            rangeMax = rect.xmax();
            pointVal = node.point.x();
        } else {
            rangeMin = rect.ymin();
            rangeMax = rect.ymax();
            pointVal = node.point.y();
        }
        if (Math.max(rangeMin, rangeMax) < pointVal) {
            explore(node.left, rect, pointsInRange, !dir);
        } else if (pointVal < Math.min(rangeMin, rangeMax)) {
            explore(node.right, rect, pointsInRange, !dir);
        } else {
            explore(node.left, rect, pointsInRange, !dir);
            explore(node.right, rect, pointsInRange, !dir);
        }
        if (rect.contains(node.point)) {
            pointsInRange.add(node.point);
        }
    }

    // a nearestPoint neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return nearest(root, p);
    }

    private Point2D nearest(Node node, Point2D query) {
        List<Point2D> candidatePoints = new LinkedList<>();
        candidatePoints.add(node.point);
        for (Node child : new Node[]{node.left, node.right}) {
            if (child != null) {
                candidatePoints.add(nearest(child, query));
            }
        }
        return candidatePoints.stream().min(Comparator.comparingDouble(point -> point.distanceTo(query))).orElse(node.point);
    }

    private Point2D nearest(Node node, Point2D query, boolean dir) {
        double queryVal, nodeVal;
        if (dir == DIR_X) {
            queryVal = query.x();
            nodeVal = node.point.x();
        } else {
            queryVal = query.y();
            nodeVal = node.point.y();
        }
        if (node.left == null && node.right == null) {
            return node.point;
        }
        if (node.left == null) {
            return nearestPoint(query, node.point, nearest(node.right, query, dir));
        }
        if (node.right == null) {
            return nearestPoint(query, node.point, nearest(node.left, query, dir));
        }
        Node closerChild, fartherChild;
        if (queryVal < nodeVal) {
            closerChild = node.left;
            fartherChild = node.right;
        } else {
            closerChild = node.right;
            fartherChild = node.left;
        }
        Point2D closerChildNearest = nearest(closerChild, query, !dir);
        if (Math.abs(queryVal - nodeVal) <= query.distanceTo(closerChildNearest)) {
            Point2D fartherChildNearest = nearest(fartherChild, query, !dir);
        }
        return null;
    }

    private Point2D nearestPoint(Point2D p, Point2D p1, Point2D p2) {
        if (p.distanceTo(p1) < p.distanceTo(p2)) return p1;
        return p2;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.7, 0.2));
//        tree.insert(new Point2D(0.5, 0.4));
//        tree.insert(new Point2D(0.2, 0.3));
//        tree.insert(new Point2D(0.4, 0.7));
//        tree.insert(new Point2D(0.9, 0.6));
//
//        tree.draw();
//        System.out.println(tree.range(new RectHV(0, 0, 0.51, 0.41)));
//        System.out.println(tree.nearest(new Point2D(0.51, 0.41)));

//        tree.insert(new Point2D(1.0, 0.5));
//        tree.insert(new Point2D(1.0, 0.0));
//        tree.insert(new Point2D(0.0, 1.0));
//        tree.insert(new Point2D(0.0, 0.0));
//        tree.insert(new Point2D(0.5, 0.5));
//        System.out.println(tree.range(new RectHV(0.875, 0.375, 1.0, 0.625)));
        tree.insert(new Point2D(0.0, 1.0));
        tree.insert(new Point2D(0.25, 1.0));
        tree.insert(new Point2D(0.75, 0.0));
        tree.insert(new Point2D(0.75, 0.75));
        tree.insert(new Point2D(0.25, 0.75));
        tree.insert(new Point2D(0.5, 1.0));
        tree.insert(new Point2D(0.5, 0.75));
        tree.insert(new Point2D(0.25, 0.25));
        tree.insert(new Point2D(0.0, 0.75));
        tree.insert(new Point2D(1.0, 0.25));

        System.out.println(tree.contains(new Point2D(0.75, 0)));
    }
}