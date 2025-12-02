package common;

import java.util.List;
import java.util.Set;

public class PointsUtils {

    private static final Character DEFAULT_EMPTY_CHAR = '.';
    private static final Character DEFAULT_POINT_CHAR = '#';

    public static String print(Set<Point> points) {
        int width = rightMostPointColumn(points) + 1;
        int height = lowermostMostPointColumn(points) + 1;

        List<List<Character>> matrix = ListUtils.createMatrix(height, width, () -> DEFAULT_EMPTY_CHAR);

        points.forEach(it -> {
            matrix.get(it.getLeft()).set(it.getRight(), DEFAULT_POINT_CHAR);
        });

        return ListUtils.printMatrix(matrix);
    }

    public static int rightMostPointColumn(Set<Point> points) {
        int rightmostColumn = 0;

        for (Point point : points) {
            if (point.getRight() > rightmostColumn) {
                rightmostColumn = point.getRight();
            }
        }

        return rightmostColumn;
    }

    public static int lowermostMostPointColumn(Set<Point> points) {
        int lowermostRow = 0;

        for (Point point : points) {
            if (point.getLeft() > lowermostRow) {
                lowermostRow = point.getLeft();
            }
        }

        return lowermostRow;
    }
}
