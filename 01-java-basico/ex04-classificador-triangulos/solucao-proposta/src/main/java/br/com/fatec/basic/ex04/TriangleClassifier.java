package br.com.fatec.basic.ex04;

/**
 * Implementação de referência para validação e classificação geométrica de triângulos.
 */
public final class TriangleClassifier {

    private static final String ERROR_INVALID_TRIANGLE = "Os lados fornecidos não formam um triângulo válido";

    private TriangleClassifier() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Verifica se três segmentos de reta podem formar um triângulo geometricamente realizável.
     *
     * @param sideA comprimento do primeiro lado
     * @param sideB comprimento do segundo lado
     * @param sideC comprimento do terceiro lado
     * @return true se os comprimentos formam um triângulo válido; false caso contrário
     */
    public static boolean isValidTriangle(double sideA, double sideB, double sideC) {
        if (sideA <= 0.0 || sideB <= 0.0 || sideC <= 0.0) {
            return false;
        }
        return (sideA + sideB > sideC)
                && (sideA + sideC > sideB)
                && (sideB + sideC > sideA);
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
        if (!isValidTriangle(sideA, sideB, sideC)) {
            throw new IllegalArgumentException(ERROR_INVALID_TRIANGLE);
        }

        if (sideA == sideB && sideB == sideC) {
            return TriangleType.EQUILATERAL;
        }

        if (sideA == sideB || sideA == sideC || sideB == sideC) {
            return TriangleType.ISOSCELES;
        }

        return TriangleType.SCALENE;
    }
}
