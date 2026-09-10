package br.com.fatec.basic.ex11;

/**
 * [ESTRUTURA FORNECIDA PRONTA — NÃO É NECESSÁRIO MODIFICAR ESTE ARQUIVO]
 *
 * Agrupador imutável de dados que consolida as métricas estatísticas essenciais de um vetor.
 * Utilizado como tipo de retorno do método VectorAnalyzer.calculateStatistics(...).
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
}
