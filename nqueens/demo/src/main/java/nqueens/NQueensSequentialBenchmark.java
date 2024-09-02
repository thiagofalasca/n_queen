package nqueens;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NQueensSequentialBenchmark {

    private NQueensSequential nQueensSequential;

    @Param({"8", "12", "16", "20", "24", "28", "32"})
    public int boardSize;

    @Setup
    public void setUp() {
        nQueensSequential = new NQueensSequential(boardSize);
        nQueensSequential.setBenchmarkMode(true); // Define modo de benchmark
    }

    @Benchmark
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS) 
    @Fork(1) // reduzindo forks para 1
    public void testNQueensSequential() {
        nQueensSequential.solveAndPrint();
    }
}
