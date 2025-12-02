package aoc2024;

import aoc2024.inputs.Day7Input;
import org.junit.jupiter.api.Test;

import static common.Number.concat;
import static common.Number.concatLog10;
import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    @Test
    public void testShort() {
        Day7 task = new Day7(Day7Input.TASK1_SHORT);
        assertEquals(3749, task.getResult());
    }

    @Test
    public void testFull() {
        Day7 task = new Day7(Day7Input.TASK1_FULL);
        assertEquals(3351424677624L, task.getResult());
    }


    @Test
    public void testShortTask2() {
        Day7 task = new Day7(Day7Input.TASK1_SHORT);
        assertEquals(11387, task.getResultTask2());
    }

    @Test
    public void testFullTask2() {
        Day7 task = new Day7(Day7Input.TASK1_FULL);
        assertEquals(204976636995111L, task.getResultTask2());
    }

    @Test
    public void testConcat() {
        assertEquals(100100, concat(100, 100));
        assertEquals(1512412L, concat(15124, 12));
        assertEquals(15127L, concat(15, 127));
        assertEquals(1127L, concat(1, 127));
        assertEquals(1671L, concat(167, 1));
        assertEquals(21, concat(2, 1));
        assertEquals(1, concat(0, 1));
        assertEquals(10, concat(1, 0));
        assertEquals(2321, concat(2, 321));
        assertEquals(4, concat(0, 4));
        assertEquals(412, concat(0, 412));
        assertEquals(61529381236L, concat(61529, 381236));
        assertEquals(6152009380001236L, concat(6152009, 380001236));
        assertEquals(214748364812345L, concat(0x7fffffffL + 1, 12345));
    }

    @Test
    public void testConcatLog10() {
        assertEquals(100100, concatLog10(100, 100));
        assertEquals(1512412L, concatLog10(15124, 12));
        assertEquals(15127L, concatLog10(15, 127));
        assertEquals(1127L, concatLog10(1, 127));
        assertEquals(1671L, concatLog10(167, 1));
        assertEquals(21, concatLog10(2, 1));
        assertEquals(1, concatLog10(0, 1));
        assertEquals(10, concatLog10(1, 0));
        assertEquals(1, concatLog10(0, 1));
        assertEquals(61529381236L, concatLog10(61529, 381236));
        assertEquals(214748364812345L, concatLog10(0x7fffffffL + 1, 12345));
    }

}