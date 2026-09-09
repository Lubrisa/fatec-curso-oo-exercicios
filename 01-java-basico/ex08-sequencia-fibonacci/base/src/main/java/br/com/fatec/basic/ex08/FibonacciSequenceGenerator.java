package br.com.fatec.basic.ex08;

/**
 * Utilitário de alta performance para cálculo e manipulação da Sequência de Fibonacci.
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
        // TODO: Validar se count < 1 e lançar IllegalArgumentException com ERROR_INVALID_COUNT
        // TODO: Validar se count > MAX_SAFE_COUNT e lançar IllegalArgumentException com ERROR_COUNT_OVERFLOW
        // TODO: Alocar array de long com tamanho count
        // TODO: Inicializar a primeira posição com 0L
        // TODO: Se count >= 2, inicializar a segunda posição com 1L
        // TODO: Para i de 2 até count - 1, calcular array[i] = array[i - 1] + array[i - 2]
        throw new UnsupportedOperationException("Método generateSequence ainda não implementado");
    }

    /**
     * Retorna o n-ésimo termo da Sequência de Fibonacci (0-indexado) com complexidade O(N) e espaço O(1).
     *
     * @param n índice do termo desejado (0 <= n <= 92)
     * @return o valor de F(n) como long
     * @throws IllegalArgumentException se n < 0 ou n > 92
     */
    public static long getNthTerm(int n) {
        // TODO: Validar se n < 0 e lançar IllegalArgumentException com ERROR_NEGATIVE_INDEX
        // TODO: Validar se n > MAX_SAFE_INDEX e lançar IllegalArgumentException com ERROR_INDEX_OVERFLOW
        // TODO: Se n == 0, retornar 0L; se n == 1, retornar 1L
        // TODO: Inicializar variáveis de estado (a = 0L, b = 1L)
        // TODO: Em um laço for de 2 até n, calcular proximo = a + b, atualizar a = b, b = proximo
        // TODO: Retornar b
        throw new UnsupportedOperationException("Método getNthTerm ainda não implementado");
    }

    /**
     * Determina se um determinado número pertence à Sequência de Fibonacci.
     *
     * @param number valor a ser verificado
     * @return true se o número pertence à sucessão de Fibonacci, false caso contrário
     */
    public static boolean isFibonacciNumber(long number) {
        // TODO: Retornar false para números negativos
        // TODO: Retornar true para 0L ou 1L
        // TODO: Iterar com duas variáveis gerando os termos enquanto o termo atual for menor que number
        // TODO: Se encontrar o número exato, retornar true; se ultrapassar, retornar false
        throw new UnsupportedOperationException("Método isFibonacciNumber ainda não implementado");
    }
}
