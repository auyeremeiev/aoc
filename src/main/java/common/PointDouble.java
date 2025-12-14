package common;

import java.util.List;

public class PointDouble extends Pair<Double, Double> {
    public PointDouble(Double left, Double right) {
        super(left, right);
    }

    public <T> boolean insideMatrix(List<List<T>> matrix) {
        return getLeft() < matrix.size() && getRight() < matrix.get(0).size() && getLeft() >= 0 && getRight() >= 0;
    }

    public static PointDouble of(Double left, Double right) {
        return new PointDouble(left, right);
    }
}
