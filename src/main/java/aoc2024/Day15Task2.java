package aoc2024;

import common.Direction;
import common.Point;
import aoc2024.helpers.Day15WideBox;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static common.Direction.RIGHT;
import static aoc2024.helpers.Day15WideBox.boxWithLeftBase;
import static aoc2024.helpers.Day15WideBox.boxWithRightBase;

public class Day15Task2 {
    private static final Character RAW_BOX_CHAR = 'O';
    private static final Character BOX_CHAR_1 = '[';
    private static final Character BOX_CHAR_2 = ']';
    private static final Character ROBOT_CHAR = '@';
    private static final Character WALL_CHAR = '#';

    private final Set<Day15WideBox> boxes = new HashSet<>();
    private final Set<Point> walls = new HashSet<>();
    private final List<Direction> moves = new ArrayList<>();
    private Point startingPosition;

    public Day15Task2(String rawInput) {
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
                .map(box -> box.left().getLeft() * 100L + box.left().getRight())
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

        if (boxes.contains(boxWithLeftBase(nextPoint)) ||
                boxes.contains(boxWithRightBase(nextPoint))) {
            if (direction.isVertical()) {
                if (moveBoxesVertically(nextPoint, direction)) {
                    return nextPoint;
                } else {
                    return currentPosition;
                }
            } else {
                if (moveBoxesHorizontally(nextPoint, direction)) {
                    return nextPoint;
                } else {
                    return currentPosition;
                }
            }
        }

        return nextPoint;
    }

    private boolean moveBoxesVertically(Point startingPoint, Direction direction) {
        Day15WideBox startingBox = getBox(startingPoint).orElseThrow();

        Set<Day15WideBox> currentAllBoxes = new HashSet<>();

        Set<Day15WideBox> currentBoxesInLine = new HashSet<>();
        currentBoxesInLine.add(startingBox);

        if (hasAdjacentWallVertically(currentBoxesInLine, direction)) {
            return false;
        }

        while (!currentBoxesInLine.isEmpty()) {
            currentAllBoxes.addAll(currentBoxesInLine);
            currentBoxesInLine = adjacentBoxesVertically(currentBoxesInLine, direction);
            if (hasAdjacentWallVertically(currentBoxesInLine, direction)) {
                return false;
            }
        }

        currentAllBoxes.forEach(boxes::remove);
        List<Day15WideBox> newBoxesPositions = currentAllBoxes.stream()
                .map(it -> new Day15WideBox(direction.nextPoint(it.left()), direction.nextPoint(it.right())))
                .toList();
        boxes.addAll(newBoxesPositions);
        return true;
    }


    private boolean moveBoxesHorizontally(Point startingPoint, Direction direction) {
        Day15WideBox startingBox = getBox(startingPoint).orElseThrow();

        Set<Day15WideBox> currentAllBoxes = new HashSet<>();
        currentAllBoxes.add(startingBox);
        Point nextPoint = direction.nextPoint(direction.nextPoint(startingPoint));
        Optional<Day15WideBox> nextBoxMaybe = getBox(nextPoint);

        while (nextBoxMaybe.isPresent()) {
            Day15WideBox nextBox = nextBoxMaybe.get();
            currentAllBoxes.add(nextBox);
            nextPoint = direction.nextPoint(direction.nextPoint(nextPoint));
            nextBoxMaybe = getBox(nextPoint);
        }

        if (walls.contains(nextPoint)) {
            return false;
        }

        currentAllBoxes.forEach(boxes::remove);
        List<Day15WideBox> newBoxesPositions = currentAllBoxes.stream()
                .map(it -> new Day15WideBox(direction.nextPoint(it.left()), direction.nextPoint(it.right())))
                .toList();
        boxes.addAll(newBoxesPositions);
        return true;
    }

    private Optional<Day15WideBox> getBox(Point basePoint) {
        Day15WideBox boxWithLeftBase = boxWithLeftBase(basePoint);
        Day15WideBox boxWithRightBase = boxWithRightBase(basePoint);
        if (boxes.contains(boxWithLeftBase)) {
            return Optional.of(boxWithLeftBase);
        } else if (boxes.contains(boxWithRightBase)) {
            return Optional.of(boxWithRightBase);
        } else {
            return Optional.empty();
        }
    }

    private Set<Day15WideBox> adjacentBoxesVertically(Set<Day15WideBox> currentBoxes, Direction direction) {
        Set<Day15WideBox> result = new HashSet<>();
        for (Day15WideBox currentBox : currentBoxes) {
            result.addAll(adjacentBoxesVertically(currentBox, direction));
        }

        return result;
    }

    private boolean hasAdjacentWallVertically(Set<Day15WideBox> currentBoxes, Direction direction) {
        for (Day15WideBox currentBox : currentBoxes) {
            if (walls.contains(direction.nextPoint(currentBox.left())) || walls.contains(direction.nextPoint(currentBox.right()))) {
                return true;
            }
        }

        return false;
    }

    private Set<Day15WideBox> adjacentBoxesVertically(Day15WideBox currentBox, Direction direction) {
        Set<Day15WideBox> result = new HashSet<>();

        Optional<Day15WideBox> maybeBox = getBox(direction.nextPoint(currentBox.left()));
        Optional<Day15WideBox> maybeBox2 = getBox(direction.nextPoint(currentBox.right()));

        maybeBox.ifPresent(result::add);
        maybeBox2.ifPresent(result::add);
        return result;
    }

    private void parseField(String rawField) {
        List<List<Character>> lines = rawField.lines()
                .map(it -> it.chars().mapToObj(charc -> (char) charc).toList()).toList();

        for (int i = 0; i < lines.size(); i++) {
            List<Character> line = lines.get(i);
            for (int j = 0; j < line.size(); j++) {
                Character curChar = line.get(j);
                Point currentPoint = new Point(i, j);
                if (curChar.equals(BOX_CHAR_1)) {
                    if (j + 1 < line.size() && line.get(j + 1).equals(BOX_CHAR_2)) {
                        boxes.add(new Day15WideBox(currentPoint, RIGHT.nextPoint(currentPoint)));
                    }
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
                    case '>' -> RIGHT;
                    default -> throw new IllegalStateException("Illegal move direction");
                }).toList());
    }

    public static String transformRaw(String raw) {
        String[] sections = raw.split("\\n\\n");
        String transformedRawSection = transformRawFieldSection(sections[0]);
        return String.join("\n\n", transformedRawSection, sections[1]);
    }

    private static String transformRawFieldSection(String rawInput) {
        List<List<String>> lines = rawInput.lines()
                .map(it -> it.chars()
                        .mapToObj(charc -> (char) charc)
                        .map(currentChar -> {
                            if (currentChar.equals(RAW_BOX_CHAR)) {
                                return "[]";
                            } else if (currentChar.equals(ROBOT_CHAR)) {
                                return "@.";
                            } else if (currentChar.equals(WALL_CHAR)) {
                                return "##";
                            } else {
                                return "..";
                            }
                        })
                        .toList())
                .toList();

        return String.join("\n", lines.stream().map(it -> String.join("", it)).toList());
    }
}
