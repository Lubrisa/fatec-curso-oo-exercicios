package br.com.fatec.basic.ex06;

/**
 * Utilitário para geração de tabuadas aritméticas e cálculo de fatoriais seguros contra overflow.
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
        // TODO: Validar se upTo < 1 e lançar IllegalArgumentException com ERROR_INVALID_TABLE_LIMIT
        // TODO: Instanciar um array de int com tamanho upTo
        // TODO: Iterar com um laço for/while preenchendo o array com base * (i + 1)
        throw new UnsupportedOperationException("Método generateMultiplicationTable ainda não implementado");
    }

    /**
     * Calcula o fatorial de um número inteiro não-negativo de forma iterativa.
     *
     * <p>Utiliza o tipo {@code long} como acumulador para suportar valores até 20! sem overflow.</p>
     *
     * @param n número inteiro cujo fatorial será apurado (0 <= n <= 20)
     * @return valor de n! como inteiro de 64 bits (long)
     * @throws IllegalArgumentException se n < 0 ou n > 20
     */
    public static long calculateFactorial(int n) {
        // TODO: Validar se n < 0 e lançar IllegalArgumentException com ERROR_NEGATIVE_FACTORIAL
        // TODO: Validar se n > MAX_SAFE_FACTORIAL_INPUT e lançar IllegalArgumentException com ERROR_FACTORIAL_OVERFLOW
        // TODO: Inicializar acumulador long em 1L
        // TODO: Executar laço multiplicando os valores de 2 até n
        // TODO: Retornar o acumulador
        throw new UnsupportedOperationException("Método calculateFactorial ainda não implementado");
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
        // TODO: Validar se start < 0 e lançar IllegalArgumentException com ERROR_NEGATIVE_START
        // TODO: Validar se end > MAX_SAFE_FACTORIAL_INPUT e lançar IllegalArgumentException com ERROR_FACTORIAL_OVERFLOW
        // TODO: Validar se start > end e lançar IllegalArgumentException com ERROR_INVALID_RANGE
        // TODO: Instanciar array de long com tamanho (end - start + 1)
        // TODO: Iterar preenchendo cada posição chamando calculateFactorial
        throw new UnsupportedOperationException("Método calculateFactorialsInRange ainda não implementado");
    }
}
