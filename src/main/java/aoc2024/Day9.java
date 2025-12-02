package aoc2024;

import common.Pair;
import aoc2024.helpers.Day9Block;
import aoc2024.helpers.Day9EmptyBlock;
import aoc2024.helpers.Day9FileBlock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Day9 {

    private List<Integer> line;

    public Day9(String input) {
        line = new ArrayList<>(input.chars().mapToObj(Character::getNumericValue).toList());
    }

    public long getTask1ResultSum() {
        long result = 0;
        int currentIndex = 0;
        List<Pair<Integer, Integer>> resultString = getTask1ResultNumbers();
        for (Pair<Integer, Integer> number : resultString) {
            result += getSum(currentIndex, number);
            currentIndex += number.getRight();
        }

        return result;
    }

    public List<Pair<Integer, Integer>> getTask1ResultNumbers() {
        List<Pair<Integer, Integer>> result = new ArrayList<>(); // file block and amount

        int left = 0;
        int right = line.size() - 1;

        while (left <= right) {
            while (line.get(left) == 0) {
                left++;
            }

            while ((left % 2 == 0 || line.get(left) == 0) && left <= right) {
                result.add(new Pair<>(left / 2, line.get(left)));
                left++;
            }

            while (right % 2 != 0 || (line.get(right) == 0)) {
                right--;
            }

            Integer spacesAmount = line.get(left);
            if (spacesAmount != 0 && left <= right) {
                int fileBlocksToReallocate = Math.min(spacesAmount, line.get(right));
                line.set(right, line.get(right) - fileBlocksToReallocate);
                line.set(left, spacesAmount - fileBlocksToReallocate);

                // We either used up all the space from the left
                // or we've taken all the blocks from the rigth and we need to move on
                result.add(new Pair<>(right / 2, fileBlocksToReallocate));
            }
        }

        return result;
    }

    public long getTask2ResultSum() {
        long result = 0;
        int currentIndex = 0;
        List<Day9Block> resultBlocks = getTask2ResultNumbers();
        for (Day9Block block : resultBlocks) {
            if (block instanceof Day9FileBlock fileBlock) {
                result += getSumTask2(currentIndex, fileBlock);
            }
            currentIndex += block.getLength();
        }

        return result;
    }

    public List<Day9Block> getTask2ResultNumbers() {
        // Can be optimised by using only Day9Block and to not use line at all
        List<Day9Block> blocks = convertBlocks(line); // file block and amount
        Set<Integer> attemptedFileBlocks = new HashSet<>();

        int r = blocks.size() - 1;
        while (r >= 0) {
            Day9Block block = blocks.get(r);
            if (block instanceof Day9EmptyBlock) {
                r--;
                continue;
            }

            Day9FileBlock fileBlock = (Day9FileBlock) block;

            int fileBlockSize = fileBlock.getLength();
            if (fileBlockSize == 0) {
                r--;
                continue;
            }

            int fileBlockNumber = fileBlock.getFileBlockNumber();
            if (attemptedFileBlocks.contains(fileBlockNumber)) {
                r--;
                continue;
            }

            attemptedFileBlocks.add(fileBlockNumber);
            Optional<Integer> firstSuitableEmptyBlockIndex = getFirstSuitableEmptyBlockIndex(blocks, fileBlockSize, r);
            if (firstSuitableEmptyBlockIndex.isEmpty()) {
                r--;
                continue;
            }

            int emptyBlockIndex = firstSuitableEmptyBlockIndex.get();
            int emptyBlockSize = blocks.get(emptyBlockIndex).getLength();
            int newEmptyBlockSize = emptyBlockSize - fileBlockSize;

            // 0 1 2 3 [4] 5 => 0 1 2 3 [4] 5
            blocks.set(r, new Day9EmptyBlock(fileBlock.getLength()));
            // 0 {4} 2 3 [4] 5
            blocks.set(emptyBlockIndex, fileBlock);

            if (newEmptyBlockSize != 0) {
                // 0 {4} (1-4) 2 [3] 4 5
                blocks.add(emptyBlockIndex + 1, new Day9EmptyBlock(newEmptyBlockSize));
            } else {
                r--;
            }
        }

        return blocks;
    }

    private List<Day9Block> convertBlocks(List<Integer> line) {
        List<Day9Block> result = new ArrayList<>();

        for (int i = 0; i < line.size(); i++) {
            if (i % 2 == 1) {
                // That is a very nasty corner case
                if (!result.isEmpty() && result.get(result.size() - 1) instanceof Day9EmptyBlock) {
                    Day9Block previousEmptyBlock = result.remove(result.size() - 1);
                    result.add(new Day9EmptyBlock(previousEmptyBlock.getLength() + line.get(i)));
                } else {
                    result.add(new Day9EmptyBlock(line.get(i)));
                }
            }

            if (i % 2 == 0) {
                if (line.get(i) != 0) {
                    result.add(new Day9FileBlock(i / 2, line.get(i)));
                }
            }
        }

        return result;
    }

    private static Optional<Integer> getFirstSuitableEmptyBlockIndex(
            List<Day9Block> blocks,
            int size,
            int beforeIndexExclusive) {
        for (int i = 0; i < blocks.size() && i < beforeIndexExclusive; i++) {
            Day9Block block = blocks.get(i);
            if (block instanceof Day9EmptyBlock && block.getLength() >= size) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    // Doing at late in the friday night. Probably writing crap
    private long getSum(int index, Pair<Integer, Integer> number) {
        long result = 0;
        long currentIndex = index;
        long fileBlock = number.getLeft();

        while (number.getRight() - (currentIndex - index) > 0) {
            result += currentIndex * fileBlock;
            currentIndex++;
        }
        return result;
    }

    private long getSumTask2(int index, Day9FileBlock fileBlock) {
        long result = 0;
        int currentIndex = index;
        long fileBlockNumber = fileBlock.getFileBlockNumber();

        while (fileBlock.getLength() - (currentIndex - index) > 0) {
            result += currentIndex * fileBlockNumber;
            currentIndex++;
        }
        return result;
    }
}
