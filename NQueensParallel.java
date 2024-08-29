import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NQueensParallel {

    private int[] board;
    private int size;
    private boolean solutionFound = false;
    private final Object lock = new Object();

    public NQueensParallel(int size) {
        this.size = size;
        this.board = new int[size];
    }

    // Método para verificar se uma posição é segura para a rainha
    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            // Verifica se há uma rainha na mesma coluna ou nas diagonais
            if (board[i] == col || board[i] - i == col - row || board[i] + i == col + row) {
                return false;
            }
        }
        return true;
    }

    // Método para imprimir o tabuleiro
    private void printSolution() {
        for (int i = 0; i < size; i++) {
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

    // Classe que representa uma thread para tentar encontrar uma solução a partir
    // de uma linha
    class NQueensThread extends Thread {
        private int startRow;

        public NQueensThread(int startRow) {
            this.startRow = startRow;
        }

        @Override
        public void run() {
            solveFromRow(startRow);
        }

        private void solveFromRow(int row) {
            if (solutionFound)
                return; // Se já encontrou uma solução, não continuar

            for (int col = 0; col < size; col++) {
                if (isSafe(row, col)) {
                    board[row] = col;
                    if (row == size - 1) {
                        synchronized (lock) {
                            if (!solutionFound) {
                                solutionFound = true;
                                printSolution();
                            }
                        }
                        return;
                    } else {
                        solveFromRow(row + 1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int size = reader.nextInt(); // Tamanho do tabuleiro NxN
        reader.close();

        NQueensParallel nQueens = new NQueensParallel(size);
        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Thread thread = nQueens.new NQueensThread(i);
            threads.add(thread);
            thread.start();
        }

        // Aguarda todas as threads terminarem
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!nQueens.solutionFound) {
            System.out.println("Nenhuma solução encontrada.");
        }

        long endTime = System.currentTimeMillis(); // Marca o fim do tempo
        System.out.println("Tempo de execução: " + (endTime - startTime) + " milissegundos");
    }
}
