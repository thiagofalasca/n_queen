package nqueens;

public class NQueensSequential {

    private int[] board;
    private int size;
    private boolean benchmarkMode = false; // Flag para modo de benchmark

    public NQueensSequential(int size) {
        this.size = size;
        this.board = new int[size];
    }

    // Método para definir se está em modo de benchmark
    public void setBenchmarkMode(boolean benchmarkMode) {
        this.benchmarkMode = benchmarkMode;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || board[i] - i == col - row || board[i] + i == col + row) {
                return false;
            }
        }
        return true;
    }

    private boolean solve(int row) {
        if (row == size) {
            return true; // Todas as rainhas foram colocadas
        }
        for (int col = 0; col < size; col++) {
            if (isSafe(row, col)) {
                board[row] = col; // Coloca a rainha na posição segura

                if (solve(row + 1)) {
                    return true; // Solução encontrada
                }
                // Se não encontrar solução, remove a rainha e tenta a próxima coluna
            }
        }
        return false; // Nenhuma posição é segura
    }

    private void printSolution() {
        if (benchmarkMode) return; // Suprime a impressão se estiver em modo de benchmark

        System.out.print(" \t");
        for (int i = 0; i < size; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < size; j++) {
                if (board[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public void solveAndPrint() {
        long startTime = System.currentTimeMillis();

        if (solve(0)) {
            printSolution();
        } else {
            if (!benchmarkMode) {
                System.out.println("No solutions found.");
            }
        }

        long endTime = System.currentTimeMillis();

        if (!benchmarkMode) {
            System.out.println("Execution time: " + (endTime - startTime) + " ms");
        }
    }
}
