package common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StopWatchGauge {
    private static final Logger log = LogManager.getLogger(StopWatchGauge.class);

    public static <T> T run(Supplier<T> supplier, Task task) {
        return run(supplier, 1, task);
    }

    public static <T> T runReliably(Supplier<T> supplier, Task task) {
        return run(supplier, 100, task);
    }

    public static <T> T run(Supplier<T> supplier, int executionRepeats, Task task) {
        if (executionRepeats == 0) {
            throw new IllegalArgumentException("executionRepeats cannot be 0");
        }

        StopWatch stopWatch = new StopWatch();
        List<T> results = new ArrayList<>();

        stopWatch.start();
        for (int i = 0; i < executionRepeats; i++) {
            results.add(supplier.get());
        }
        stopWatch.stop();
        log.info(task.getTaskTextName() +
                ": time elapsed: " + stopWatch.getNanoTime() / 1000000d / executionRepeats + "ms");

        if (results.isEmpty()) {
            throw new IllegalStateException("Executions results list is empty");
        }
        validateAllResultsEqual(results);

        return results.get(0);
    }

    private static <T> void validateAllResultsEqual(List<T> results) {
        T element = results.get(0);
        results.stream()
                .filter(it -> !it.equals(element))
                .findAny()
                .ifPresent(it -> {
                    throw new IllegalStateException("Some of the results differ from the another " +
                            "Results: " + ListUtils.printList(results));
                });
    }
}
