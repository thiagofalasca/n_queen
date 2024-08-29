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

    // Método para resolver o problema recursivamente
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

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int size = reader.nextInt(); // Tamanho do tabuleiro NxN
        reader.close();

        NQueensSequential nQueens = new NQueensSequential(size);
        long startTime = System.currentTimeMillis();

        if (nQueens.solve(0)) {
            nQueens.printSolution();
        } else {
            System.out.println("Nenhuma solução encontrada.");
        }
        
        long endTime = System.currentTimeMillis(); // Marca o fim do tempo
        System.out.println("Tempo de execução: " + (endTime - startTime) + " milissegundos");
    }
}