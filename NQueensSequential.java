import java.util.Scanner;

public class NQueensSequential {

    private int[] board;
    private int size;

    public NQueensSequential(int size) {
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

    // Método para resolver o problema recursivamente e medir a memória
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

    // Método para imprimir o tabuleiro com labels
    private void printSolution() {
        // Imprime os rótulos das colunas
        System.out.print(" \t");
        for (int i = 0; i < size; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        // Imprime o tabuleiro com rótulos de linha
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

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter board size: ");
        int size = reader.nextInt(); // Tamanho do tabuleiro NxN
        reader.close();

        NQueensSequential nQueens = new NQueensSequential(size);
        
        // Captura o tempo inicial e a memória usada
        long startTime = System.currentTimeMillis();

        if (nQueens.solve(0)) {
            nQueens.printSolution();
        } else {
            System.out.println("No solutions found.");
        }

        // Captura o tempo final e a memória usada
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
