package common;

import org.junit.jupiter.api.Test;

import static common.Number.splitIntoEqualParts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    public void testSplitIntoEqualParts() {
//        assertThat(splitIntoEqualParts(123456789L, 3))
//                .containsExactly(123L, 456L, 789L);
//
//        assertThat(splitIntoEqualParts(123456789012L, 4))
//                .containsExactly(123L, 456L, 789L, 12L);
//
//        assertThat(splitIntoEqualParts(123456789012L, 1))
//                .containsExactly(123456789012L);
//
//        assertThat(splitIntoEqualParts(123456789012L, 12))
//                .containsExactly(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 0L, 1L, 2L);
//
//        assertThat(splitIntoEqualParts(123456789012L, 2))
//                .containsExactly(123456L, 789012L);
//
//        assertThat(splitIntoEqualParts(870087L, 3))
//                .containsExactly(87L, 0L, 87L);

        assertThat(splitIntoEqualParts(23000023L, 4))
                .containsExactly(23L, 0L, 0L, 23L);
    }

}