package common;

import java.math.BigDecimal;

import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class Number {

    public static long concat(long left, long right) {
        return left * pow10(digits(right)) + right;
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
