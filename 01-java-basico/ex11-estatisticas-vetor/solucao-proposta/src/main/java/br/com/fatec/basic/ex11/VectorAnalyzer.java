package br.com.fatec.basic.ex11;

/**
 * Implementação de referência para análise estatística de vetores numéricos double[].
 */
public final class VectorAnalyzer {

    public static final String ERROR_NULL_ARRAY = "O vetor informado não pode ser nulo";
    public static final String ERROR_EMPTY_ARRAY = "O vetor não pode ser vazio para o cálculo estatístico";

    private VectorAnalyzer() {
        // Impede instanciação de classe utilitária
    }

    /**
     * Calcula o somatório de todos os elementos contidos no vetor.
     *
     * @param values vetor de valores numéricos
     * @return somatório total
     * @throws IllegalArgumentException se o vetor for nulo ou vazio
     */
    public static double calculateSum(double[] values) {
        validateArray(values);
        double sum = 0.0;
        for (double val : values) {
            sum += val;
        }
        return sum;
    }

    /**
     * Calcula a média aritmética simples dos elementos contidos no vetor.
     *
     * @param values vetor de valores numéricos
     * @return média aritmética simples
     * @throws IllegalArgumentException se o vetor for nulo ou vazio
     */
    public static double calculateAverage(double[] values) {
        validateArray(values);
        return calculateSum(values) / values.length;
    }

    /**
     * Localiza o menor valor numérico presente no vetor.
     *
     * @param values vetor de valores numéricos
     * @return menor valor encontrado
     * @throws IllegalArgumentException se o vetor for nulo ou vazio
     */
    public static double findMin(double[] values) {
        validateArray(values);
        double min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        return min;
    }

    /**
     * Localiza o maior valor numérico presente no vetor.
     *
     * @param values vetor de valores numéricos
     * @return maior valor encontrado
     * @throws IllegalArgumentException se o vetor for nulo ou vazio
     */
    public static double findMax(double[] values) {
        validateArray(values);
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }

    /**
     * Processa o vetor em passo único O(N) e constrói o relatório estatístico consolidado.
     *
     * @param values vetor de valores numéricos
     * @return relatório imutável VectorStatistics
     * @throws IllegalArgumentException se o vetor for nulo ou vazio
     */
    public static VectorStatistics calculateStatistics(double[] values) {
        validateArray(values);

        double sum = values[0];
        double min = values[0];
        double max = values[0];

        for (int i = 1; i < values.length; i++) {
            double current = values[i];
            sum += current;
            if (current < min) {
                min = current;
            }
            if (current > max) {
                max = current;
            }
        }

        double average = sum / values.length;
        double amplitude = max - min;

        return new VectorStatistics(values.length, sum, average, min, max, amplitude);
    }

    /**
     * Conta quantos elementos no vetor possuem valor estritamente superior à média aritmética do próprio vetor.
     *
     * @param values vetor de valores numéricos
     * @return quantidade de elementos acima da média
     * @throws IllegalArgumentException se o vetor for nulo ou vazio
     */
    public static int countAboveAverage(double[] values) {
        validateArray(values);
        double avg = calculateAverage(values);
        int count = 0;
        for (double val : values) {
            if (val > avg) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calcula o desvio padrão populacional dos elementos do vetor (método complementar do desafio).
     *
     * @param values vetor de valores numéricos
     * @return desvio padrão populacional (raiz quadrada da variância populacional)
     * @throws IllegalArgumentException se o vetor for nulo ou vazio
     */
    public static double calculateStandardDeviation(double[] values) {
        validateArray(values);
        double avg = calculateAverage(values);
        double sumOfSquaredDiffs = 0.0;
        for (double val : values) {
            double diff = val - avg;
            sumOfSquaredDiffs += diff * diff;
        }
        return Math.sqrt(sumOfSquaredDiffs / values.length);
    }

    private static void validateArray(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException(ERROR_NULL_ARRAY);
        }
        if (values.length == 0) {
            throw new IllegalArgumentException(ERROR_EMPTY_ARRAY);
        }
    }
}
