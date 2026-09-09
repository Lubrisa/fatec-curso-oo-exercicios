package br.com.fatec.basic.ex06;

/**
 * Implementação de referência para geração de tabuadas e cálculo de fatoriais com laços iterativos.
 */
public final class TableAndFactorialGenerator {

    public static final int MAX_SAFE_FACTORIAL_INPUT = 20;

    private static final String ERROR_INVALID_TABLE_LIMIT = "O limite superior da tabuada deve ser maior ou igual a 1";
    private static final String ERROR_NEGATIVE_FACTORIAL = "Não é possível calcular fatorial de número negativo";
    private static final String ERROR_FACTORIAL_OVERFLOW = "O valor excede o limite representável pelo tipo long (máximo 20)";
    private static final String ERROR_NEGATIVE_START = "O valor inicial não pode ser negativo";
    private static final String ERROR_INVALID_RANGE = "O valor inicial não pode ser maior que o valor final";

    private TableAndFactorialGenerator() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Gera os produtos de uma base inteira multiplicada de 1 até upTo.
     *
     * @param base fator fixo da tabuada
     * @param upTo limite superior multiplicador (inclusivo, deve ser >= 1)
     * @return array de inteiros com os resultados [base * 1, ..., base * upTo]
     * @throws IllegalArgumentException se upTo for menor que 1
     */
    public static int[] generateMultiplicationTable(int base, int upTo) {
        if (upTo < 1) {
            throw new IllegalArgumentException(ERROR_INVALID_TABLE_LIMIT);
        }

        int[] table = new int[upTo];
        for (int i = 0; i < upTo; i++) {
            table[i] = base * (i + 1);
        }
        return table;
    }

    /**
     * Calcula o fatorial de um número inteiro não-negativo de forma iterativa.
     *
     * @param n número inteiro cujo fatorial será apurado (0 <= n <= 20)
     * @return valor de n! como inteiro de 64 bits (long)
     * @throws IllegalArgumentException se n < 0 ou n > 20
     */
    public static long calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_FACTORIAL);
        }
        if (n > MAX_SAFE_FACTORIAL_INPUT) {
            throw new IllegalArgumentException(ERROR_FACTORIAL_OVERFLOW);
        }

        long product = 1L;
        for (int i = 2; i <= n; i++) {
            product *= i;
        }
        return product;
    }

    /**
     * Calcula os fatoriais de uma faixa fechada de números inteiros [start, end].
     *
     * @param start valor inicial da faixa (deve ser >= 0)
     * @param end   valor final da faixa (deve ser <= 20 e >= start)
     * @return array contendo os fatoriais de cada número no intervalo
     * @throws IllegalArgumentException se start < 0, end > 20 ou start > end
     */
    public static long[] calculateFactorialsInRange(int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_START);
        }
        if (end > MAX_SAFE_FACTORIAL_INPUT) {
            throw new IllegalArgumentException(ERROR_FACTORIAL_OVERFLOW);
        }
        if (start > end) {
            throw new IllegalArgumentException(ERROR_INVALID_RANGE);
        }

        int size = end - start + 1;
        long[] factorials = new long[size];
        for (int i = 0; i < size; i++) {
            factorials[i] = calculateFactorial(start + i);
        }
        return factorials;
    }
}
