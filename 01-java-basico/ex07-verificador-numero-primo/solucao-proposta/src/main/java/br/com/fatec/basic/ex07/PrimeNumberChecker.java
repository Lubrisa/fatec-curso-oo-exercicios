package br.com.fatec.basic.ex07;

/**
 * Implementação de referência para verificação de primalidade com otimização assintótica O(sqrt(N)).
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
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        // Itera apenas sobre números ímpares até sqrt(n)
        for (long d = 3; d * d <= n; d += 2) {
            if (n % d == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retorna o menor número primo estritamente maior que n.
     *
     * @param n valor de referência
     * @return o próximo número primo > n
     */
    public static long nextPrime(long n) {
        if (n < 2) {
            return 2;
        }

        long candidate = n + 1;
        // Se candidate for par e > 2, pula para o próximo ímpar imediatamente
        if (candidate > 2 && candidate % 2 == 0) {
            candidate++;
        }

        while (!isPrime(candidate)) {
            candidate += 2;
        }

        return candidate;
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
        validateRange(start, end);

        int count = 0;
        for (long current = start; current <= end; current++) {
            if (isPrime(current)) {
                count++;
            }
        }
        return count;
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
        validateRange(start, end);

        int count = countPrimes(start, end);
        long[] primes = new long[count];

        int index = 0;
        for (long current = start; current <= end; current++) {
            if (isPrime(current)) {
                primes[index++] = current;
            }
        }

        return primes;
    }

    private static void validateRange(long start, long end) {
        if (start < 0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_START);
        }
        if (start > end) {
            throw new IllegalArgumentException(ERROR_INVALID_RANGE);
        }
    }
}
