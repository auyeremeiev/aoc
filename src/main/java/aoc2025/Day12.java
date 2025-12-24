package aoc2025;

import common.ListUtils;
import common.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    private static final Logger log = LogManager.getLogger(Day12.class);

    public static long part1(String input) {
        Input parsedInput = parse(input);

        return part1(parsedInput);
    }

    public static long part1(Input input) {
        List<Area> areas = input.areas;

        return areas.stream()
                .filter(it -> canFit(input.shapes, it))
                .count();
    }

    public static boolean canFit(List<Shape> shapes, Area area) {
        long areaValue = area.area();

        List<Integer> shapesNumbers = new ArrayList<>(area.shapesNumbers());
        Pair<Integer, List<Integer>> minNumberWithIndexes = ListUtils.minNumberWithIndexes(shapesNumbers, 0);
        Integer minNumber = minNumberWithIndexes.getLeft();

        int result = calculateApproxArea(shapes, shapesAllIndexes(shapesNumbers, 0), minNumber);
        reduceAllNumbersByNumber(shapesNumbers, minNumber);
        List<Integer> remainingShapeIndexes = ListUtils.allNonOccurrencesIndexes(shapesNumbers, 0);

        while (!remainingShapeIndexes.isEmpty()){
            minNumberWithIndexes = ListUtils.minNumberWithIndexes(shapesNumbers, 0);
            minNumber = minNumberWithIndexes.getLeft();
            result += calculateApproxArea(shapes, shapesAllIndexes(shapesNumbers, 0), minNumber);
            reduceAllNumbersByNumber(shapesNumbers, minNumber);
            remainingShapeIndexes = ListUtils.allNonOccurrencesIndexes(shapesNumbers, 0);
        }

        return result <= areaValue;
    }

    private static void reduceAllNumbersByNumber(List<Integer> shapesNumbers, Integer minNumber) {
        for (int i = 0; i < shapesNumbers.size(); i++) {
            Integer currentNumber = shapesNumbers.get(i);
            if (currentNumber != 0) {
                shapesNumbers.set(i, currentNumber - minNumber);
                if (currentNumber - minNumber < 0) {
                    throw  new IllegalStateException("I did something wrong I got number below 0");
                }
            }
        }
    }

    private static int calculateApproxArea(List<Shape> shapes, List<Integer> shapesIndexes, int times) {
        List<Shape> filteredShapes = shapes.stream()
                .filter(it -> shapesIndexes.contains(it.number))
                .toList();

        int result = 0;
        for (int i = 0; i < times; i++) {
            result += calculateApproxArea(filteredShapes);
        }

        return result;
    }

    private static List<Integer> shapesAllIndexes(List<Integer> shapesNumbers) {
        return shapesAllIndexes(shapesNumbers, 0);
    }

    private static List<Integer> shapesAllIndexes(List<Integer> shapesNumbers, int except) {
        return ListUtils.allNonOccurrencesIndexes(shapesNumbers, except, 0);
    }

    private static int calculateApproxArea(List<Shape> shapes) {
        double heuristicCoefficient = 1.2;
        Integer allOccupiedCells = shapes.stream().map(Shape::occupiedCells).reduce(0, Integer::sum);
        return (int) Math.ceil(allOccupiedCells * heuristicCoefficient);
    }

    public static Input parse(String input) {
        Pattern shapePattern = Pattern.compile("(\\d+:\\n)");
        Pattern areaPattern = Pattern.compile("(\\d+x\\d+:)");

        String[] sections = input.split("\n\n");
        List<Shape> shapes = Arrays.stream(sections).filter(it -> {
            Matcher matcher = shapePattern.matcher(it);
            return matcher.find();
        }).map(shapeFull -> {
            String[] shapeLines = shapeFull.split("\\n");
            int number = Integer.parseInt(shapeLines[0].replace(":", "").trim());

            List<List<Boolean>> rows = new ArrayList<>();

            for (int i = 1; i < shapeLines.length; i++) {
                char[] chars = shapeLines[i].toCharArray();
                List<Boolean> row = new ArrayList<>();

                for (char aChar : chars) {
                    row.add(aChar == '#');
                }
                rows.add(row);
            }

            return new Shape(number, rows);
        }).toList();

        List<Area> areas = Arrays.stream(sections).filter(it -> {
            Matcher matcher = areaPattern.matcher(it);
            return matcher.find();
        }).flatMap(it -> Arrays.stream(it.split("\n")))
                .map(line -> {
                    String[] parts = line.split(":");

                    // parse first two numbers
                    String[] dims = parts[0].split("x");
                    int first = Integer.parseInt(dims[0]);
                    int second = Integer.parseInt(dims[1]);

                    // parse remaining numbers
                    List<Integer> values = new ArrayList<>();
                    for (String s : parts[1].trim().split("\\s+")) {
                        values.add(Integer.parseInt(s));
                    }

                    return new Area(first, second, values);
                }).toList();


        return new Input(shapes, areas);
    }

    public record Shape(int number, List<List<Boolean>> form) {
        public int occupiedCells() {
            int result = 0;
            for (int i = 0; i < form.size(); i++) {
                for (int j = 0; j < form.get(0).size(); j++) {
                    if (form.get(i).get(j)) {
                        result++;
                    }
                }
            }
            return result;
        }
    }

    public record Area(long width, long height, List<Integer> shapesNumbers) {

        public long area() {
            return width * height;
        }
    }

    public record Input(List<Shape> shapes, List<Area> areas) {

    }
}
