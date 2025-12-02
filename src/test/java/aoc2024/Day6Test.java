package aoc2024;

import common.Direction;
import common.Pair;
import aoc2024.helpers.Day6Data;
import org.junit.jupiter.api.Test;

import static aoc2024.inputs.Day6Inputs.IS_LOOP_1;
import static aoc2024.inputs.Day6Inputs.IS_LOOP_2;
import static aoc2024.inputs.Day6Inputs.IS_LOOP_3;
import static aoc2024.inputs.Day6Inputs.IS_LOOP_6;
import static aoc2024.inputs.Day6Inputs.IS_LOOP_7;
import static aoc2024.inputs.Day6Inputs.TASK1_FULL;
import static aoc2024.inputs.Day6Inputs.TASK1_SHORT;
import static aoc2024.inputs.Day6Inputs.TASK2_FULL;
import static aoc2024.inputs.Day6Inputs.TASK2_VERY_SHORT;
import static aoc2024.inputs.Day6Inputs.TASK2_VERY_SHORT_2;
import static aoc2024.inputs.Day6Inputs.TASK2_VERY_SHORT_3;
import static aoc2024.inputs.Day6Inputs.TASK2_VERY_SHORT_4;
import static aoc2024.inputs.Day6Inputs.TASK2_VERY_SHORT_5;
import static aoc2024.inputs.Day6Inputs.TASK2_VERY_SHORT_6;
import static aoc2024.inputs.Day6Inputs.TASK2_VERY_SHORT_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day6Test {

    @Test
    public void testTask1Short() {
        assertEquals(41, Day6.getTask1Result(TASK1_SHORT));
    }

    @Test
    public void testTask1Full() {
        assertEquals(4967, Day6.getTask1Result(TASK1_FULL));
    }

    @Test
    public void testTask1SimpleSolutionShort() {
        assertEquals(41, Day6.getTask1ResultSimpleSolution(TASK1_SHORT));
    }

    @Test
    public void testTask1SimpleSolutionFull() {
        assertEquals(4967, Day6.getTask1ResultSimpleSolution(TASK1_FULL));
    }

    @Test
    public void testTask2Short() {
        assertEquals(6, Day6.getTask2ResultJumpingSolution(TASK1_SHORT));
    }

    @Test
    public void testTask2VeryShort() {
        assertEquals(1, Day6.getTask2ResultJumpingSolution(TASK2_VERY_SHORT));
        assertEquals(2, Day6.getTask2ResultJumpingSolution(TASK2_VERY_SHORT_2));
        assertEquals(0, Day6.getTask2ResultJumpingSolution(TASK2_VERY_SHORT_3));
        assertEquals(4, Day6.getTask2ResultJumpingSolution(TASK2_VERY_SHORT_4));
        assertEquals(0, Day6.getTask2ResultJumpingSolution(TASK2_VERY_SHORT_5));
        assertEquals(0, Day6.getTask2ResultJumpingSolution(TASK2_VERY_SHORT_6));
        assertEquals(3, Day6.getTask2ResultJumpingSolution(TASK2_VERY_SHORT_8));
    }

    @Test
    public void testTask2Full() {
        assertEquals(1789, Day6.getTask2ResultJumpingSolution(TASK2_FULL));
    }

    @Test
    public void testTask2LoopFunction() {
        assertTrue(Day6.willCreateLoop(
                new Pair<>(6, 4),
                null,
                Direction.UP,
                Day6.parseInput(IS_LOOP_1)));
        assertFalse(Day6.willCreateLoop(
                new Pair<>(1, 4),
                null,
                Direction.RIGHT,
                Day6.parseInput(IS_LOOP_2)));

        assertTrue(Day6.willCreateLoop(
                new Pair<>(1, 4),
                null,
                Direction.RIGHT,
                Day6.parseInput(IS_LOOP_3)));

        Day6Data day6Data = Day6.parseInput(IS_LOOP_6);
        assertTrue(Day6.willCreateLoop(
                day6Data.getStartingPoint(),
                null,
                Direction.UP, day6Data
        ));

        day6Data = Day6.parseInput(IS_LOOP_7);
        assertTrue(Day6.willCreateLoop(
                day6Data.getStartingPoint(),
                null,
                Direction.UP, day6Data
        ));

        day6Data = Day6.parseInput(TASK2_VERY_SHORT_3);
        assertFalse(Day6.willCreateLoop(
                day6Data.getStartingPoint(),
                null,
                Direction.UP, day6Data
        ));
    }
}