package br.com.fatec.basic.ex08;

/**
 * Implementação de referência para cálculo e manipulação da Sequência de Fibonacci.
 */
public final class FibonacciSequenceGenerator {

    public static final int MAX_SAFE_INDEX = 92;
    public static final int MAX_SAFE_COUNT = 93;

    private static final String ERROR_INVALID_COUNT = "A quantidade de termos deve ser maior ou igual a 1";
    private static final String ERROR_COUNT_OVERFLOW = "A quantidade de termos excede o limite representável pelo tipo long (máximo 93 termos, até F(92))";
    private static final String ERROR_NEGATIVE_INDEX = "O índice do termo não pode ser negativo";
    private static final String ERROR_INDEX_OVERFLOW = "O índice excede o limite representável pelo tipo long (máximo 92)";

    private FibonacciSequenceGenerator() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Gera os primeiros 'count' termos da Sequência de Fibonacci em um array de inteiros de 64 bits.
     *
     * @param count quantidade de termos desejados (1 <= count <= 93)
     * @return array contendo os primeiros termos [F(0), F(1), ..., F(count - 1)]
     * @throws IllegalArgumentException se count < 1 ou count > 93
     */
    public static long[] generateSequence(int count) {
        if (count < 1) {
            throw new IllegalArgumentException(ERROR_INVALID_COUNT);
        }
        if (count > MAX_SAFE_COUNT) {
            throw new IllegalArgumentException(ERROR_COUNT_OVERFLOW);
        }

        long[] sequence = new long[count];
        sequence[0] = 0L;
        if (count >= 2) {
            sequence[1] = 1L;
        }

        for (int i = 2; i < count; i++) {
            sequence[i] = sequence[i - 1] + sequence[i - 2];
        }

        return sequence;
    }

    /**
     * Retorna o n-ésimo termo da Sequência de Fibonacci (0-indexado) com complexidade O(N) e espaço O(1).
     *
     * @param n índice do termo desejado (0 <= n <= 92)
     * @return o valor de F(n) como long
     * @throws IllegalArgumentException se n < 0 ou n > 92
     */
    public static long getNthTerm(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_INDEX);
        }
        if (n > MAX_SAFE_INDEX) {
            throw new IllegalArgumentException(ERROR_INDEX_OVERFLOW);
        }

        if (n == 0) {
            return 0L;
        }
        if (n == 1) {
            return 1L;
        }

        long a = 0L;
        long b = 1L;
        for (int i = 2; i <= n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }

        return b;
    }

    /**
     * Determina se um determinado número pertence à Sequência de Fibonacci.
     *
     * @param number valor a ser verificado
     * @return true se o número pertence à sucessão de Fibonacci, false caso contrário
     */
    public static boolean isFibonacciNumber(long number) {
        if (number < 0) {
            return false;
        }
        if (number == 0L || number == 1L) {
            return true;
        }

        long a = 0L;
        long b = 1L;

        while (b < number) {
            long next = a + b;
            a = b;
            b = next;
        }

        return b == number;
    }
}
