import java.util.BitSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

// Complexidade de tempo O(n!) no pior caso
public class NQueensOptimized {

    // Variável para indicar se uma solução foi encontrada
    private static AtomicBoolean solutionFound = new AtomicBoolean(false);

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter board size: ");
        int n = reader.nextInt();
        reader.close();

        // Cria um pool de threads com número de threads igual ao número de processadores disponíveis
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long startTime = System.currentTimeMillis();
        try {
            // Array para armazenar os objetos Future, que representam o resultado de uma tarefa assíncrona
            Future<?>[] futures = new Future<?>[n];

            // Paraleliza a busca pela posição da primeira rainha
            for (int col = 0; col < n; col++) {
                final int initialCol = col;
                futures[col] = executor.submit(() -> solve(new int[n], new BitSet(n), new BitSet(2 * n), new BitSet(2 * n), 0, initialCol, n, startTime));
            }

            // Aguarda até que uma solução seja encontrada
            for (Future<?> future : futures) {
                future.get(); // Espera até que uma solução seja encontrada
                if (solutionFound.get()) {
                    break; // Se uma solução foi encontrada, interrompe a busca
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Finaliza o pool de threads
        }
    }

    // Método recursivo para resolver o problema das N Rainhas
    private static void solve(int[] board, BitSet cols, BitSet d1, BitSet d2, int row, int initialCol, int n, long startTime) {
        if (solutionFound.get()) return; // Se uma solução foi encontrada, interrompe a busca
        
        // Se estamos na primeira linha, coloca a rainha na coluna inicial fornecida
        if (row == 0) {
            board[row] = initialCol;
            cols.set(initialCol);
            d1.set(row - initialCol + n); // Marca a diagonal principal
            d2.set(row + initialCol); // Marca a diagonal secundária
            solve(board, cols, d1, d2, row + 1, initialCol, n, startTime);
            return;
        }

        // Se todas as rainhas foram colocadas, imprime o tabuleiro
        if (row == n) {
            solutionFound.set(true); // Marca que uma solução foi encontrada
            printBoard(board);
            long endTime = System.currentTimeMillis();
            System.out.println("Execution time: " + (endTime - startTime) + "ms");
            return;
        }

        // Tenta colocar a rainha na linha atual em todas as colunas possíveis
        for (int col = 0; col < n; col++) {
            // Verifica se a coluna e as diagonais estão livres
            if (!cols.get(col) && !d1.get(row - col + n) && !d2.get(row + col)) {
                board[row] = col;
                cols.set(col); // Marca a coluna como ocupada
                d1.set(row - col + n); // Marca a diagonal principal como ocupada
                d2.set(row + col); // Marca a diagonal secundária como ocupada

                solve(board, cols, d1, d2, row + 1, initialCol, n, startTime); // Chama recursivamente para a próxima linha

                // Desmarca a coluna e as diagonais para tentar outra posição
                cols.clear(col);
                d1.clear(row - col + n);
                d2.clear(row + col);
            }
        }
    }

    // Método para imprimir o tabuleiro no formato de saída
    private static void printBoard(int[] board) {
        int n = board.length;

        // Imprime as letras das colunas
        System.out.print(" \t");
        for (int i = 0; i < n; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        int i = 0;

        // Imprime cada linha do tabuleiro
        for (int row : board) {
            System.out.print((i + 1) + "\t");
            i++;
            for (int col = 0; col < n; col++) {
                if (col == row) {
                    System.out.print("Q "); // Coloca um "Q" na posição da rainha
                } else {
                    System.out.print(". "); // Coloca um ponto em posições vazias
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
