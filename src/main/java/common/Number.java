package common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class Number {

    public static long concat(long left, long right) {
        return left * pow10(digits(right)) + right;
    }

    public static int concat(int left, int right) {
        return (int) (left * pow10(digits(right)) + right);
    }

    public static BigDecimal number(String number) {
        return new BigDecimal(number);
    }

    public static BigDecimal number(Long number) {
        return new BigDecimal(number);
    }

    public static BigDecimal number(Integer number) {
        return new BigDecimal(number);
    }

    public static long pow10(long n) {
        if (n == 0) return 1;
        return (long) Math.pow(10, n);
    }

    public static Pair<Long, Long> split(long number) {
        int digits = digits(number);
        int newLeftSize = digits / 2;
        int newRightSize = digits - newLeftSize;

        long base = (long) pow(10L, newRightSize);
        // 123[456]
        return new Pair<>(number / base, number % base);
    }

    public static boolean allDigitsAreSame(long number) {
        int digits = digits(number);
        long firstDigit = number / pow10(digits - 1); // 123, digits = 3, 10^2 = 1000

        for (int i = 1; i < digits; i++) {
            if (digitAt(number, i) != firstDigit) {
                return false;
            }
        }

        return true;
    }

    public static long digitAt(long number, int index) {
        long adjustedNumber = number % pow10(digits(number) - index);

        return adjustedNumber / pow10(digits(number) - index - 1); // 12345, i = 1; digits(number) - index = 5 - 2 = 3, 10 ^ 3 = 1000
    }

    public static List<Long> splitIntoEqualParts(long number, long parts) {
        int digits = digits(number);

        if (digits % parts != 0) {
            throw new IllegalArgumentException("Can't produce equals" +
                    " parts from the number of length %d and parts number %d".formatted(digits, parts));
        }

        List<Long> result = new ArrayList<>();
        long eachPartLength = digits / parts;

        long remainingNumber = number;
        while (digits(remainingNumber) > eachPartLength) {
            // it misses the case with training zeros
            int skippedZeros = 0;
            int numberOfTrailinngZerosInNextPart = numberOfTrailingZerosAfterIndex(remainingNumber, eachPartLength);
            if (numberOfTrailinngZerosInNextPart >= eachPartLength) {
                skippedZeros = (int) (numberOfTrailinngZerosInNextPart / eachPartLength);
            }

            Pair<Long, Long> currentSplit = splitWithSize(remainingNumber, eachPartLength);
            result.add(currentSplit.getLeft());
            remainingNumber = currentSplit.getRight();
            if (skippedZeros > 0) {
                for (int i = 0; i < skippedZeros; i++) {
                    result.add(0L);
                }
            }
        }
        result.add(remainingNumber);

        return result;
    }

    private static int numberOfTrailingZerosAfterIndex(long number, long i) {
        int digits = digits(number);

        int j = 0;
        while (j + i < digits && digitAt(number, (int) (j + i)) == 0) {
            j++;
        }
        return j;
    }

    public static List<Long> splitIntoEqualPartsUsingString(long number, long parts) {
        String n = String.valueOf(number);

        if (n.length() % parts != 0) {
            throw new IllegalArgumentException("Can't produce equals" +
                    " parts from the number of length %d and parts number %d".formatted(n.length(), parts));
        }

        List<Long> result = new ArrayList<>();
        long eachPartLength = n.length() / parts;

        String remainingNumber = n;
        while (remainingNumber.length() > eachPartLength) {
            // it misses the case with training zeros
            Pair<String, String> currentSplit = Pair.of(
                    remainingNumber.substring(0, (int) eachPartLength),
                    remainingNumber.substring((int) eachPartLength)
            );
            result.add(Long.valueOf(currentSplit.getLeft()));
            remainingNumber = currentSplit.getRight();
        }
        result.add(Long.valueOf(remainingNumber));

        return result;
    }

    public static Pair<Long, Long> splitWithSize(long number, long leftSize) {
        long base = (long) pow(10L, digits(number) - leftSize);
        // 123[456]
        return new Pair<>(number / base, number % base);
    }

    public static Pair<Long, Long> splitLeftBigger(long number) {
        int digits = digits(number);
        int newLeftSize = digits % 2 == 0 ? digits / 2 : digits / 2 + 1;

        int newRightSize = digits - newLeftSize;

        long base = (long) pow(10L, newRightSize);
        // 123[456]
        return new Pair<>(number / base, number % base);
    }

    public static Pair<Long, Long> split(long number, int numberOfDigits) {
        int newLeftSize = numberOfDigits / 2;
        int newRightSize = numberOfDigits - newLeftSize;

        long base = (long) pow(10L, newRightSize);
        // 123[456]
        return new Pair<>(number % base, number / base);
    }

    public static List<Long> findAllDivisors(long number) {
        List<Long> small = new ArrayList<>();
        List<Long> large = new ArrayList<>();

        for (long i = 1; i * i <= number; i++) {
            if (number % i == 0) {
                small.add(i);

                if (number / i != i) {
                    large.add(0, number / i);
                }
            }
        }

        small.addAll(large);
        return small;
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

    public static long convertToLong(int[] number) {
        StringBuilder sb = new StringBuilder(number.length);

        for (int d : number) {
            if (d < 0 || d > 9)
                throw new IllegalArgumentException("All elements must be digits 0–9");

            sb.append(d);
        }

        return Long.parseLong(sb.toString());
    }

    public static int[] convertToDigits(String number) {
        return number.chars()
                .map(c -> c - '0')
                .toArray();
    }
}
