package aoc2025;

import common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static common.Binary.oneBitPositions;
import static common.ListUtils.difference;
import static common.ListUtils.intersection;
import static common.Rectangle.area;

public class Day10 {

    private static final Logger log = LogManager.getLogger(Day10.class);

    public static long part1(String input) {
        List<Pair<Integer, List<Integer>>> parsedInput = parse(input);

        return part1(parsedInput);
    }

    public static long part1(List<Pair<Integer, List<Integer>>> input) {
        return input.stream()
                .map(Day10::solveSinglePuzzle)
                .reduce(0, Integer::sum);
    }

    public static int solveSinglePuzzle(Pair<Integer, List<Integer>> puzzle) {
        Integer expectedNumber = puzzle.getLeft();
        List<Integer> options = puzzle.getRight();

        int currentNumber = expectedNumber;
        return getSmallestPath(options, new ArrayList<>(), currentNumber, 0);
    }

    public static int getSmallestPath(
            List<Integer> options,
            List<Integer> usedOptions,
            int currentNumber,
            int currentLength) {
        if (currentNumber == 0) {
            return currentLength;
        }

        List<Integer> optionsCopy = new ArrayList<>(usedOptions);

        List<Integer> pathsLengths = options.stream()
                .filter(option -> !optionsCopy.contains(option))
                .map(option -> {
                    optionsCopy.add(option);
                    return getSmallestPath(options, optionsCopy, currentNumber ^ option, currentLength + 1);
                })
                .toList();

        return ListUtils.minNumber(pathsLengths);
    }

    public static Long part2(String input) {
        List<Pair<Integer, List<Integer>>> parse = parse(input);
        return part2(parse);
    }

    public static Long part2(List<Pair<Integer, List<Integer>>> input) {
        return 0L;
    }

    public static List<Pair<Integer, List<Integer>>> parse(String input) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]|\\((.*?)\\)|\\{(.*?)\\}");

        String[] lines = input.split("\n");
        return Arrays.stream(lines).map(it -> {
            Matcher matcher = pattern.matcher(it);

            String expectedResultStr = "";
            List<String> numbers = new ArrayList<>();
            String charges;
            while (matcher.find()) {
                if (matcher.group(1) != null) { // [] match
                    expectedResultStr = matcher.group(1);
                } else if (matcher.group(2) != null) { // () match
                    numbers.add(matcher.group(2));
                } else if (matcher.group(3) != null) { // {} match
                    charges = matcher.group(3);
                }
            }

            return Pair.of(
                    convertExpectedResultString(expectedResultStr),
                    parseNumbers(numbers, expectedResultStr).toList()
            );
        }).toList();
    }

    private static Stream<Integer> parseNumbers(List<String> numbers, String expectedResultStr) {
        return numbers.stream().map(numberListStr -> convertNumbersString(numberListStr, expectedResultStr.length()));
    }

    private static int convertExpectedResultString(String expectedResult) {
        String resultWithZeros = expectedResult.replace(".", "0");
        return Binary.binaryToInt(resultWithZeros.replace("#", "1"));
    }

    public static int convertNumbersString(String numberList, int numberLength) {
        List<Integer> list = Arrays.stream(numberList.split(",")).map(Integer::parseInt).toList();
        int[] binaryDigitsList = new int[numberLength];
        list.forEach(oneBitDigitIndex -> {
            binaryDigitsList[oneBitDigitIndex] = 1;
        });

        for (int i = 0; i < binaryDigitsList.length; i++) {
            if (binaryDigitsList[i] != 1) {
                binaryDigitsList[i] = 0;
            }
        }

        return Binary.convert(binaryDigitsList);
    }
}
