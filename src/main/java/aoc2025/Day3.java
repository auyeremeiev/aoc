package aoc2025;

import aoc2024.Day4;
import common.ArraysUtils;
import common.Number;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static common.Number.concat;
import static common.Number.convertToDigits;

public class Day3 {

    private static final Logger log = LogManager.getLogger(Day4.class);

    public static long part1(String input) {
        List<int[]> numbers = parse(input);

        return part1(numbers);
    }

    public static long part1(List<int[]> lines) {
        return lines.stream().map(line -> {
                    int value = part1LineProcessor(line);
                    return value;
                })
                .reduce(0, Integer::sum);
    }

    public static int part1LineProcessor(String line) {
        return part1LineProcessor(line.chars()
                .map(c -> c - '0')
                .toArray());
    }

    public static int part1LineProcessor(int... line) {
        int[] biggestToRight = new int[line.length];
        int[] biggestToLeft = new int[line.length];

        // 987 - 999
        // 787 - 788
        int currentBiggest = line[0];
        biggestToLeft[0] = currentBiggest;
        for (int i = 1; i < line.length; i++) {
            if (line[i] > currentBiggest) {
                currentBiggest = line[i];
            }
            biggestToLeft[i] = currentBiggest;
        }

        currentBiggest = line[line.length - 1];
        biggestToRight[line.length - 1] = currentBiggest;
        for (int i = line.length - 1; i >= 0; i--) {
            if (line[i] > currentBiggest) {
                currentBiggest = line[i];
            }
            biggestToRight[i] = currentBiggest;
        }

        int i = 0;
        currentBiggest = concat(biggestToLeft[i], biggestToRight[i + 1]);
        //453353 -> 455555 55553
        while (i < line.length - 2 && biggestToLeft[i] <= biggestToRight[i + 1]) {
            int currentNumber = concat(biggestToLeft[i], biggestToRight[i + 1]);
            if (currentNumber > currentBiggest) {
                currentBiggest = currentNumber;
            }
            i++;
        }

        if (concat(biggestToLeft[i], biggestToRight[i + 1]) > currentBiggest) {
            currentBiggest = concat(biggestToLeft[i], biggestToRight[i + 1]);
        }

        return currentBiggest;
    }

    // 23423[4]234[63227896785]67
    // 234234234[632278967856]7567596
    // 234234234[6322789678]56756[7]5[9]6 // wrong
    // 234234234[6]322[7123967856756]7596
    // 2342342346322[7896785675675]96
    // 23[433423453427]8567478596
    // 23433[423453427856]7478596 // wrong
    // 23[4]33[4]23[453427856]7478596
    // 234334234[512378567478]596
    // 234334234[5]1[23785674785]96
    // 234334234[5]12[37856747859]6567459
    // 234334234[5]123[78567478596]567459
    // 2343342345123[785674785965]67459

    //
    // 234334234[532178567478]596
    // 234334234[532178567478]596
    // 234334234[532178567478]596

    // 234334234[5321|2|78567478]596
    // 234334234[532]1[2785674785]96

    // 234334234[53210|2|78567478]596
    // 234334234[5321]0[|2|785674785]96
    // 234334234[532]10[|2|7856747859]6

    // 234334234[532]10[2|7|856747859]6678959465
    // 234334234[532]102[|7|8567478596]678959465
    // 234334234[53]2102[|7|856747859667]8959465
    // 234334234[5]32102[|7|8567478596678]959465
    // 234334234|5 32102[|7|85674785966789]59465

    // 23433423[45342785674785]96
    // [919191919191291]919191
    // [9]1[91919191912919]19191
    // if current inner i bigger than biggest to the left, check if we can shift by innerI, if yes - shift and replace current j until bigger or reached beginning of sequence
    // if current inner i bigger than щту to the left, check if we can shift by innerI, if yes, shift
    // if current inner is smaller than previos then increment i
    // didn't think of [811111111111]11[9] yet
    // Potential simple algorithm could be:
    // 1. Check if there is any element inside current subsequence that is bigger than a pervious one
    //      if yes then previous element should be popped out and subsequence should be shifted
    //      do it until there is no such elements
    // 2. If there is no such element, go to the right until element bigger than latest one found
    //      when found, do 1
    // If I have time I can modify 1 by just popping elements in place without rechecking entire sequence
    public static Long part2(String input) {
        List<int[]> numbers = parse(input);

        return part2(numbers);
    }

    public static Long part2(List<int[]> lines) {
        return lines.stream().map(line -> {
                    long value = part2LineProcessor(line);
                    return value;
                })
                .reduce(0L, Long::sum);
    }

    public static long part2LineProcessor(String digits) {
        return part2LineProcessor(convertToDigits(digits));
    }

    private static long part2LineProcessor(int[] line) {
        int[] sequence = new int[12];
        init(sequence);

        while(canPush(sequence, line)) {
            repair(sequence, line);
            if (canPush(sequence, line)) {
                Optional<Integer> nextBiggerElementIndexThanLastInSeq = getNextBiggerElementIndexThanLastInSeq(sequence, line);
                if (nextBiggerElementIndexThanLastInSeq.isPresent()) {
                    push(sequence, sequence.length - 1, line, nextBiggerElementIndexThanLastInSeq.get());
                } else {
                    break;
                }
            }
        }

        int[] finalNumberArray = Arrays.stream(sequence).map(it -> line[it]).toArray();
        return Number.convertToLong(finalNumberArray);
    }

    private static Optional<Integer> getNextBiggerElementIndexThanLastInSeq(int[] sequence, int[] line) {
        if (!canPush(sequence, line)) {
            return Optional.empty();
        }

        int lastSequenceElement = line[lastIndex(sequence)];
        int currentLineIndex = lastIndex(sequence) + 1;
        while(currentLineIndex < line.length) {
            if (line[currentLineIndex] > lastSequenceElement) {
                return Optional.of(currentLineIndex);
            }

            currentLineIndex++;
        }

        if (currentLineIndex == line.length) {
            return Optional.empty();
        }

        return Optional.of(currentLineIndex);
    }

    private static void repair(int[] sequence, int[] line) {
        Optional<Integer> iOpt = Optional.empty();
        while ((iOpt = getElementSmallerThanNext(sequence, line)).isPresent()
                && canPush(sequence, line)) {
            int i = iOpt.get();
            push(sequence, i, line);
        }
    }

    private static Optional<Integer> getElementSmallerThanNext(int[] sequence, int[] line) {
        for (int i = 1; i < sequence.length; i++) {
            int currentElement = line[sequence[i]];
            int previousElement = line[sequence[i - 1]];

            if (currentElement > previousElement) {
                return Optional.of(i - 1);
            }
        }

        return Optional.empty();
    }

    // 234334234[532]10[2|7|856747859]6678959465
    // 234334234[532]102[|7|8567478596]678959465
    // 8, 9, 10, [13], 14, 15, 16.. 22
    // 8, 9, 10, 14, 15, 16, 17.. 23
    private static void push(int[] sequence, int sequenceIndexToPopOut, int[] line) {
        push(sequence, sequenceIndexToPopOut, line, lastIndex(sequence) + 1);
    }

    private static void push(int[] sequence, int sequenceIndexToPopOut, int[] line, int lineIndexToTakeIn) {
        if (lineIndexToTakeIn >= line.length) {
            throw new IllegalStateException("Can't that element, it's beyond array!");
        }

        int lastIndex = lastIndex(sequence);

        ArraysUtils.removeAndShift(sequence, sequenceIndexToPopOut);
        sequence[sequence.length - 1] = lineIndexToTakeIn;
    }

    private static boolean canPush(int[] sequence, int[] line) {
        int lastIndex = lastIndex(sequence);
        return line.length - 1 > lastIndex; // li = 11; line.length = 12 -> false line.length = 13 -> true
    }

    private static int lastIndex(int[] sequence) {
        return sequence[sequence.length - 1];
    }

    private static void init(int[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = i;
        }
    }

    public static List<int[]> parse(String input) {
        String[] lines = input.split("\n");
        return Arrays.stream(lines).map(
                it -> it.chars()
                        .map(c -> c - '0')
                        .toArray()
        ).toList();
    }

    public static Long convertSequence(int[] sequence, int[] line) {
        int[] finalNumberArray = Arrays.stream(sequence).map(it -> line[it]).toArray();
        return Number.convertToLong(finalNumberArray);
    }
}
