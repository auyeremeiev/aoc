package common;

import java.util.List;

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
}
