package br.com.fatec.basic.ex11;

/**
 * Relatório imutável contendo as métricas estatísticas essenciais de um vetor numérico.
 *
 * @param count quantidade total de elementos analisados
 * @param sum somatório de todos os elementos
 * @param average média aritmética simples
 * @param min menor valor presente no vetor
 * @param max maior valor presente no vetor
 * @param amplitude diferença absoluta entre o valor máximo e o valor mínimo (max - min)
 */
public record VectorStatistics(
        int count,
        double sum,
        double average,
        double min,
        double max,
        double amplitude
) {
    public VectorStatistics {
        if (count <= 0) {
            throw new IllegalArgumentException("A quantidade de elementos para as estatísticas deve ser maior que zero");
        }
    }
}
