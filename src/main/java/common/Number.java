package common;

import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class Number {

    public static long concat(long left, long right) {
        if (right == 0) {
            return left * 10L;
        }

        long currentDigit;
        long numberToAdd = 0;
        int leadingZeros = 0;
        while (right / 10L != 0) {
            currentDigit = right % 10L;
            numberToAdd *= 10L;
            numberToAdd += currentDigit;
            if (numberToAdd == 0) {
                leadingZeros++;
            }
            right /= 10L;
        }

        numberToAdd *= 10L;
        numberToAdd += right % 10L;

        while (numberToAdd / 10L != 0) {
            currentDigit = numberToAdd % 10L;
            left *= 10L;
            left += currentDigit;
            numberToAdd /= 10L;
        }

        left *= 10L;
        left += numberToAdd % 10L;

        while (leadingZeros != 0) {
            left *= 10;
            leadingZeros--;
        }

        return left;
    }

    public static Pair<Long, Long> split(long number, int numberOfDigits) {
        int newLeftSize = numberOfDigits / 2;
        int newRightSize = numberOfDigits - newLeftSize;

        long base = (long) pow(10L, newRightSize);
        // 123[456]
        return new Pair<>(number % base, number / base);
    }

    public static long concatLog10(long left, long right) {
        if (right == 0) {
            return left * 10L;
        }

        long currentDigit;
        int trailingZeros = 0;
        while (right / 10L != 0) {
            currentDigit = (right / (long) (pow(10L, (long) (log10(right)))));
            left *= 10L;
            left += currentDigit;
            long rest = (right % (long) (pow(10L, (long) (log10(right)))));

            if (rest != 0) {
                right = (right % (long) (pow(10L, (long) (log10(right)))));
            } else {
                // spaghetti code because it's 11:43 PM
                while (right / 10 != 0) {
                    trailingZeros++;
                    right /= 10;
                }
            }
        }

        if (trailingZeros == 0) {
            left *= 10L;
            left += (right / (long) (pow(10L, (long) (log10(right)))));
        }

        while (trailingZeros != 0) {
            left *= 10;
            trailingZeros--;
        }

        return left;
    }

    public static int digits(long number) {
        return (int) log10(number) + 1;
    }

    public static int modulo(int currentCode, int mod) {
        return (currentCode % mod + mod) % mod;
    }
}
