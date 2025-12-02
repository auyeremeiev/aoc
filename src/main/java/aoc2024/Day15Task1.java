package aoc2024;

import common.Direction;
import common.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15Task1 {
    private static final Character BOX_CHAR = 'O';
    private static final Character ROBOT_CHAR = '@';
    private static final Character WALL_CHAR = '#';

    private final Set<Point> boxes = new HashSet<>();
    private final Set<Point> walls = new HashSet<>();
    private final List<Direction> moves = new ArrayList<>();
    private Point startingPosition;

    public Day15Task1(String rawInput) {
        String[] sections = rawInput.split("\\n\\n");
        parseField(sections[0]);
        parseMoves(sections[1]);
    }

    public long getTaskResult() {
        doAllMoves();
        return calculateBoxesSum();
    }

    private long calculateBoxesSum() {
        return boxes.stream()
                .map(box -> box.getLeft() * 100L + box.getRight())
                .reduce(0L, Long::sum);
    }

    private void doAllMoves() {
        Point currentPosition = startingPosition;
        for (Direction move : moves) {
            currentPosition = makeMove(currentPosition, move);
        }
    }

    private Point makeMove(Point currentPosition, Direction direction) {
        Point nextPoint = direction.nextPoint(currentPosition);

        if (walls.contains(nextPoint)) {
            return currentPosition;
        }

        if (boxes.contains(nextPoint)) {
            if (moveBoxes(nextPoint, direction)) {
                return nextPoint;
            } else {
                return currentPosition;
            }
        }

        return nextPoint;
    }

    private boolean moveBoxes(Point startingBox, Direction direction) {
        List<Point> boxesInRow = new ArrayList<>();
        boxesInRow.add(startingBox);

        Point currentBox = startingBox;

        while (boxes.contains(direction.nextPoint(currentBox))) {
            currentBox = direction.nextPoint(currentBox);
            boxesInRow.add(currentBox);
        }

        Point lastBox = boxesInRow.get(boxesInRow.size() - 1);
        if (walls.contains(direction.nextPoint(lastBox))) {
            return false;
        } else {
            boxesInRow.forEach(boxes::remove);
            List<Point> newPointsPositions = boxesInRow.stream().map(direction::nextPoint).toList();
            boxes.addAll(newPointsPositions);
            return true;
        }
    }

    private void parseField(String rawField) {
        List<List<Character>> lines = rawField.lines()
                .map(it -> it.chars().mapToObj(charc -> (char) charc).toList()).toList();

        for (int i = 0; i < lines.size(); i++) {
            List<Character> line = lines.get(i);
            for (int j = 0; j < line.size(); j++) {
                Character curChar = line.get(j);
                Point currentPoint = new Point(i, j);
                if (curChar.equals(BOX_CHAR)) {
                    boxes.add(currentPoint);
                } else if (curChar.equals(ROBOT_CHAR)) {
                    startingPosition = currentPoint;
                } else if (curChar.equals(WALL_CHAR)) {
                    walls.add(currentPoint);
                }
            }
        }

        if (startingPosition == null) {
            throw new IllegalStateException("Starting position was not found");
        }
    }

    private void parseMoves(String movesSection) {
         moves.addAll(movesSection.replace("\n", "").trim().chars()
                .mapToObj(it -> (char) it)
                .map(rawMove -> switch (rawMove) {
                    case '<' -> Direction.LEFT;
                    case '^' -> Direction.UP;
                    case 'v' -> Direction.DOWN;
                    case '>' -> Direction.RIGHT;
                    default -> throw new IllegalStateException("Illegal move direction");
                }).toList());
    }
}
