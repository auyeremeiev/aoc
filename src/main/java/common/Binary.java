package common;

import java.util.ArrayList;
import java.util.List;

public class Binary {
    public static int binaryToInt(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public static String intToBinary(int number) {
        return Integer.toBinaryString(number);
    }

    public static boolean hasBitSet(int number, int position) {
        if (position < 0 || position >= 32) {
            throw new IllegalArgumentException("Position must be between 0 and 31");
        }
        return ((number >> position) & 1) == 1;
    }

    public static List<Integer> oneBitPositions(int number) {
        List<Integer> result = new ArrayList<>();

        int currentNumber = number;
        int currentPosition = 0;
        while (currentNumber > 0) {
            if ((currentNumber & 1) == 1) {
                result.add(currentPosition);
            }
            currentPosition++;
            currentNumber >>= 1;
        }

        return result;
    }

    public static int convert(int[] digits) {
        int number = 0;
        for (int digit : digits) {
            if (digit != 0 && digit != 1) {
                throw new IllegalArgumentException("All elements must be 0 or 1");
            }
            number = (number << 1) | digit;
        }
        return number;
    }
}
