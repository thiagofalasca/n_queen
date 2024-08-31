import java.util.BitSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

// time complexity O(n!) in worst case
public class NQueensOptimized {

    private static AtomicBoolean solutionFound = new AtomicBoolean(false);

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter board size: ");
        int n = reader.nextInt();
        reader.close();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long startTime = System.currentTimeMillis();
        try {
            Future<?>[] futures = new Future<?>[n];

            // parallelize the search for the first queen
            for (int col = 0; col < n; col++) {
                final int initialCol = col;
                futures[col] = executor.submit(() -> solve(new int[n], new BitSet(n), new BitSet(2 * n), new BitSet(2 * n), 0, initialCol, n, startTime));
            }

            // wait for first solution
            for (Future<?> future : futures) {
                future.get(); // wait until a solution is found
                if (solutionFound.get()) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static void solve(int[] board, BitSet cols, BitSet d1, BitSet d2, int row, int initialCol, int n, long startTime) {
        if (solutionFound.get()) return; // if solutions found, stop searching
        
        if (row == 0) {
            board[row] = initialCol;
            cols.set(initialCol);
            d1.set(row - initialCol + n);
            d2.set(row + initialCol);
            solve(board, cols, d1, d2, row + 1, initialCol, n, startTime);
            return;
        }

        if (row == n) {
            solutionFound.set(true);
            printBoard(board);
            long endTime = System.currentTimeMillis();
            System.out.println("Execution time: " + (endTime - startTime) + "ms");
            return;
        }

        for (int col = 0; col < n; col++) {
            if (!cols.get(col) && !d1.get(row - col + n) && !d2.get(row + col)) {
                board[row] = col;
                cols.set(col);
                d1.set(row - col + n);
                d2.set(row + col);

                solve(board, cols, d1, d2, row + 1, initialCol, n, startTime);

                cols.clear(col);
                d1.clear(row - col + n);
                d2.clear(row + col);
            }
        }
    }

    private static void printBoard(int[] board) {
        int n = board.length;

        // print column labels
        System.out.print(" \t");
        for (int i = 0; i < n; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        int i = 0;

        for (int row : board) {
            System.out.print((i + 1) + "\t");
            i++;
            for (int col = 0; col < n; col++) {
                if (col == row) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
