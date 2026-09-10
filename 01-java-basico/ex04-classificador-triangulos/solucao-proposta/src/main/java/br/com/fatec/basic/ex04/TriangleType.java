package br.com.fatec.basic.ex04;

/**
 * [ESTRUTURA FORNECIDA PRONTA — NÃO É NECESSÁRIO MODIFICAR ESTE ARQUIVO]
 *
 * Enumeração que representa a classificação geométrica de um triângulo quanto aos seus lados.
 * Utilizada como tipo de retorno do método TriangleClassifier.classify(...).
 */
public enum TriangleType {
    /**
     * Triângulo com todos os três lados de comprimentos iguais.
     */
    EQUILATERAL,

    /**
     * Triângulo com pelo menos dois lados de comprimentos iguais.
     */
    ISOSCELES,

    /**
     * Triângulo com todos os três lados de comprimentos distintos.
     */
    SCALENE
}
