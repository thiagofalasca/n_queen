# Projeto NQueens

## Repositorio com o codigo fonte do projeto da disciplina ES45A

O grupo desenvolveu as duas soluções exigidas, uma solução no modelo sequencial e uma utilizando threads
- Sequencial --> NQueensSequencial.java
- Threads --> NQueensOptimized.java

Os testes foram realizados utilizando o auxilio de um benchmark e se encontra dentro da seguinte estrutura:
```bash
  nqueens/demo/
  ├── src/
  │   ├── main/java/nqueens/
  │   │   ├── Main.java
  │   │   ├── NQueensOptimized.java
  │   │   ├── NQueensOptimizedBenchmark.java
  │   │   ├── NQueensSequential.java
  │   │   └── NQueensSequentialBenchmark.java
  │   ├── test-optimized.txt   <--- teste com threads  
  │   └── test-sequential.txt   <--- teste modelo sequencial
  ├── optimized_benchmark_results.csv   <--- resultado do benchmark com threads
  ├── pom.xml
  └── sequential_benchmark_results.csv   <--- resultado do benchmark modelo sequencial
```
