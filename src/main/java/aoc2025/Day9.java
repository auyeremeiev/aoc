package aoc2025;

import common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static common.Rectangle.area;

public class Day9 {

    private static final Logger log = LogManager.getLogger(Day9.class);

    public static long part1(String input) {
        List<Point> parsedInput = parse(input);

        return part1(parsedInput);
    }

    public static long part1(List<Point> input) {
        long maxArea = Integer.MIN_VALUE;

        for (int i = 0; i < input.size(); i++) {
            Point currentFirstPoint = input.get(i);
            for (int j = i + 1; j < input.size(); j++) {
                maxArea = Math.max(maxArea, area(currentFirstPoint, input.get(j)));
            }
        }

        return maxArea;
    }

    public static Long part2(String input) {
        List<Point> parse = parse(input);
        return part2(parse);
    }

    public static Long part2(List<Point> input) {
        Polygon polygon = new Polygon(input);

        long maxArea = Integer.MIN_VALUE;
        Rectangle maxRectangle = null;

        for (int i = 0; i < input.size(); i++) {
            Point currentFirstPoint = input.get(i);

            for (int j = i + 1; j < input.size(); j++) {
                Rectangle rectangle = new Rectangle(currentFirstPoint, input.get(j));
                if (polygon.isRectangleInsidePolygon(rectangle)){
                    maxArea = Math.max(maxArea, area(currentFirstPoint, input.get(j)));
                    maxRectangle = rectangle;
                }
            }
        }

        return maxArea;
    }

    public static List<Point> parse(String input) {
        String[] lines = input.split("\n");
        return Arrays.stream(lines).map(it -> {
            String[] coordinates = it.split(",");
            return Point.of(
                    Integer.parseInt(coordinates[1]),
                    Integer.parseInt(coordinates[0]));
        }).toList();
    }

}
