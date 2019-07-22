import edu.princeton.cs.algs4.Picture;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    // sum of energy of all pixels traversed until the given pixel in the current least-energy path.
    private double[][] distTo;

    private enum Direction {
        left,
        top,
        right;

        int intValue() {
            return ordinal() - 1;
        }
    }
    private Direction[][] parentDirection; // direction of previous pixel in the current least-energy path.

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!isInRangeX(x)) {
            throw new IllegalArgumentException("X not in range.");
        } else if (!isInRangeY(y)) {
            throw new IllegalArgumentException("Y not in range.");
        }
        // Border pixels given energy of 1000 so that it is strictly larger than any interior pixel.
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }

        int xGradientSquared = gradientSquared(picture.getRGB(x - 1, y), picture.getRGB(x + 1, y));
        int yGradientSquared = gradientSquared(picture.getRGB(x, y - 1), picture.getRGB(x, y + 1));
        return Math.sqrt(xGradientSquared + yGradientSquared);
    }

    private int gradientSquared(int color1, int color2) {
        int rDiff = ((color1 >> 16) & 0xFF) - ((color2 >> 16) & 0xFF);
        int gDiff = ((color1 >> 8) & 0xFF) - ((color2 >> 8) & 0xFF);
        int bDiff = (color1 & 0xFF) - (color2 & 0xFF);
        return rDiff * rDiff + gDiff * gDiff + bDiff * bDiff;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    private void transpose() {
        Picture transposed = new Picture(picture.height(), picture.width());
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                transposed.set(j, i, picture.get(i, j));
            }
        }
        picture = transposed;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        energy = new double[picture.height()][picture.width()];
        distTo = new double[picture.height()][picture.width()];
        parentDirection = new Direction[picture.height()][picture.width()];
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                energy[row][col] = energy(col, row);
                distTo[row][col] = Double.POSITIVE_INFINITY;
                parentDirection[row][col] = null;
            }
        }
        System.arraycopy(energy[0], 0, distTo[0], 0, picture.width());

        // The digraph is acyclic, where there is a downward edge from pixel
        // (x, y) to pixels (x − 1, y + 1), (x, y + 1), and (x + 1, y + 1),
        // assuming that the coordinates are in the prescribed range.
        // Because of this simple pattern, one possible topological ordering is to
        // examine all the rows from top to bottom (examine each row from left to right).
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                relaxEdges(col, row);
            }
        }

        // Find the pixel on the bottom row that has the lowest distTo value,
        // and trace that pixel up to the top row to get the seam.
        int seamBottomPixelIndex = IntStream.range(0, picture.width()).boxed()
                .min(Comparator.comparingDouble((Integer i) -> distTo[picture.height() - 1][i]))
                .get();
        int[] seam = new int[picture.height()];
        for (int i = picture.height() - 1; i >= 1; i--) {
            seam[i] = seamBottomPixelIndex;
            seamBottomPixelIndex += parentDirection[i][seamBottomPixelIndex].intValue();
        }
        seam[0] = seamBottomPixelIndex;
        return seam;
    }

    private void relaxEdges(int x, int y) {
        // There are at most three downward edges from pixel (x, y)
        // to pixels (x − 1, y + 1), (x, y + 1), and (x + 1, y + 1).
        for (Direction direction : Direction.values()) {
            relaxEdge(x, y, x + direction.intValue(), y + 1);
        }
    }

    private void relaxEdge(int xFrom, int yFrom, int xTo, int yTo) {
        if (isInRange(xTo, yTo) && distTo[yTo][xTo] > distTo[yFrom][xFrom] + energy[yTo][xTo]) {
            distTo[yTo][xTo] = distTo[yFrom][xFrom] + energy[yTo][xTo];
            if (xTo < xFrom) {
                parentDirection[yTo][xTo] = Direction.right;
            } else if (xTo > xFrom) {
                parentDirection[yTo][xTo] = Direction.left;
            } else {
                parentDirection[yTo][xTo] = Direction.top;
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }

    private boolean isInRangeX(int x) {
        return 0 <= x && x <= width() - 1;
    }

    private boolean isInRangeY(int y) {
        return 0 <= y && y <= height() - 1;
    }

    private boolean isInRange(int x, int y) {
        return isInRangeX(x) && isInRangeY(y);
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }
}