package nqueens;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NQueensOptimizedBenchmark {

    private NQueensOptimized nQueensOptimized = new NQueensOptimized();

    @Param({"8", "12", "16", "20", "24", "28", "32"})
    public int boardSize;

    @Setup
    public void setUp() {
        nQueensOptimized.setBenchmarkMode(true);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS) 
    @Fork(1)
    public void testNQueensOptimized() {
        nQueensOptimized.solveNQueens(boardSize);
    }
}
