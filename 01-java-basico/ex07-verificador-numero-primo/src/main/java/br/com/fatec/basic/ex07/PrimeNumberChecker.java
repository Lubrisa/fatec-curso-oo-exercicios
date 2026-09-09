package br.com.fatec.basic.ex07;

/**
 * Utilitário de alta performance para verificação de primalidade e operações sobre números primos.
 */
public final class PrimeNumberChecker {

    private static final String ERROR_NEGATIVE_START = "O início do intervalo não pode ser negativo";
    private static final String ERROR_INVALID_RANGE = "O início do intervalo não pode ser maior que o fim";

    private PrimeNumberChecker() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Verifica se um número inteiro é primo utilizando algoritmo otimizado O(sqrt(N)).
     *
     * @param n número a ser avaliado
     * @return true se o número for primo, false caso contrário
     */
    public static boolean isPrime(long n) {
        // TODO: Retornar false para números <= 1
        // TODO: Retornar true para n == 2
        // TODO: Retornar false para números pares maiores que 2 (n % 2 == 0)
        // TODO: Iterar com divisores ímpares a partir de 3 com passo 2 (d += 2) até d * d <= n
        // TODO: Se encontrar divisor (n % d == 0), retornar false imediatamente
        // TODO: Se terminar o laço sem divisores, retornar true
        throw new UnsupportedOperationException("Método isPrime ainda não implementado");
    }

    /**
     * Retorna o menor número primo estritamente maior que n.
     *
     * @param n valor de referência
     * @return o próximo número primo > n
     */
    public static long nextPrime(long n) {
        // TODO: Se n < 2, retornar 2
        // TODO: Começar a busca a partir de candidate = n + 1
        // TODO: Incrementar candidate até encontrar o primeiro que satisfaça isPrime(candidate)
        throw new UnsupportedOperationException("Método nextPrime ainda não implementado");
    }

    /**
     * Conta a quantidade de números primos em um intervalo fechado [start, end].
     *
     * @param start limite inferior do intervalo (inclusivo, deve ser >= 0)
     * @param end   limite superior do intervalo (inclusivo, deve ser >= start)
     * @return total de números primos no intervalo
     * @throws IllegalArgumentException se start < 0 ou start > end
     */
    public static int countPrimes(long start, long end) {
        // TODO: Validar se start < 0 e lançar IllegalArgumentException com ERROR_NEGATIVE_START
        // TODO: Validar se start > end e lançar IllegalArgumentException com ERROR_INVALID_RANGE
        // TODO: Iterar de start até end acumulando a quantidade de números primos
        throw new UnsupportedOperationException("Método countPrimes ainda não implementado");
    }

    /**
     * Localiza todos os números primos em um intervalo fechado [start, end].
     *
     * @param start limite inferior do intervalo (inclusivo, deve ser >= 0)
     * @param end   limite superior do intervalo (inclusivo, deve ser >= start)
     * @return array primitivo com os números primos encontrados no tamanho exato
     * @throws IllegalArgumentException se start < 0 ou start > end
     */
    public static long[] findPrimesInRange(long start, long end) {
        // TODO: Validar se start < 0 e lançar IllegalArgumentException com ERROR_NEGATIVE_START
        // TODO: Validar se start > end e lançar IllegalArgumentException com ERROR_INVALID_RANGE
        // TODO: Obter a quantidade exata de primos no intervalo usando countPrimes
        // TODO: Alocar o array de long com esse tamanho exato
        // TODO: Preencher o array com os primos encontrados e retornar
        throw new UnsupportedOperationException("Método findPrimesInRange ainda não implementado");
    }
}
