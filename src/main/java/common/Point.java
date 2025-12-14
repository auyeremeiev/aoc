package common;

import java.util.List;

import static java.lang.Math.pow;

public class Point extends Pair<Integer, Integer> {
    public Point(Integer left, Integer right) {
        super(left, right);
    }

    public <T> boolean insideMatrix(List<List<T>> matrix) {
        return getLeft() < matrix.size() && getRight() < matrix.get(0).size() && getLeft() >= 0 && getRight() >= 0;
    }

    public static Point of(Integer left, Integer right) {
        return new Point(left, right);
    }

    public static long distance(Point point1, Point point2) {
        return (long) Math.sqrt(
                (pow(point2.getLeft() - point1.getLeft(), 2) +
                        pow(point2.getRight() - point1.getRight(), 2)
                ));
    }
}
