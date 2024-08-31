package nqueens;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NQueensOptimizedBenchmark {

    private NQueensOptimized nQueensOptimized = new NQueensOptimized();

    @Param({"4", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"})
    public int boardSize;

    @Setup
    public void setUp() {
        nQueensOptimized.setBenchmarkMode(true); // Define modo de benchmark
    }

    @Benchmark
    @Warmup(iterations = 1, time = 3, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    public void testNQueensOptimized() {
        nQueensOptimized.solveNQueens(boardSize);
    }
}
