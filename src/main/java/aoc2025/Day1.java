package aoc2025;

import java.util.Arrays;
import java.util.List;

import static common.Number.modulo;
import static java.lang.Math.abs;

public class Day1 {

    public static long part1(String input) {
        List<Long> numbers = parse(input);

        return part1(numbers);
    }

    public static int part1(List<Long> numbers) {
        int result = 0;
        int currentCode = 50;
        for (Long number : numbers) {
            currentCode += number;
            currentCode = modulo(currentCode, 100);

            if (currentCode == 0) {
                result++;
            }
        }

        return result;
    }

    public static long part2(String input) {
        List<Long> numbers = parse(input);

        return part2(numbers);
    }

    public static long part2(List<Long> numbers) {
        long result = 0;
        int currentCode = 50;
        for (Long number : numbers) {

            int previousCode = currentCode;
            currentCode += number;
            if (previousCode != 0 && currentCode < 0) {
                result++;
            }

            result += abs(currentCode / 100L);

            if (currentCode == 0) {
                result++;
            }

            currentCode = modulo(currentCode, 100);
        }

        return result;
    }

    public static List<Long> parse(String input) {
        String[] lines = input.split("\\n");
        return Arrays.stream(lines).map(
                it -> {
                    int signum = 1;
                    if (it.charAt(0) == 'L') {
                        signum = -1;
                    }

                    long number = Integer.parseInt(it.substring(1));
                    return signum * number;
                }
        ).toList();
    }
}
