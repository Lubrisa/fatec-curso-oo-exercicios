package br.com.fatec.basic.ex04;

/**
 * Classe utilitária responsável pela validação geométrica e classificação de triângulos.
 */
public final class TriangleClassifier {

    private static final String ERROR_INVALID_TRIANGLE = "Os lados fornecidos não formam um triângulo válido";

    private TriangleClassifier() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Verifica se três segmentos de reta podem formar um triângulo geometricamente realizável.
     *
     * <p>Regras:
     * 1. Todos os lados devem ser estritamente positivos (> 0).
     * 2. Desigualdade triangular: (a + b > c) && (a + c > b) && (b + c > a).</p>
     *
     * @param sideA comprimento do primeiro lado
     * @param sideB comprimento do segundo lado
     * @param sideC comprimento do terceiro lado
     * @return true se formam um triângulo válido; false caso contrário
     */
    public static boolean isValidTriangle(double sideA, double sideB, double sideC) {
        // TODO: Implementar validação da existência geométrica do triângulo
        throw new UnsupportedOperationException("Método isValidTriangle ainda não implementado");
    }

    /**
     * Classifica o triângulo quanto aos comprimentos de seus lados em EQUILATERAL, ISOSCELES ou SCALENE.
     *
     * @param sideA comprimento do primeiro lado
     * @param sideB comprimento do segundo lado
     * @param sideC comprimento do terceiro lado
     * @return o tipo do triângulo como TriangleType
     * @throws IllegalArgumentException se os lados não formarem um triângulo válido
     */
    public static TriangleType classify(double sideA, double sideB, double sideC) {
        // TODO: Validar com isValidTriangle e retornar o TriangleType correspondente
        throw new UnsupportedOperationException("Método classify ainda não implementado");
    }
}
