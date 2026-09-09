package br.com.fatec.basic.ex04;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ex04 — Testes da classe TriangleClassifier")
class TriangleClassifierTest {

    private static final String ERROR_INVALID = "Os lados fornecidos não formam um triângulo válido";
    private static final Offset<Double> PRECISION = Offset.offset(0.001);

    @Nested
    @DisplayName("Validação da Condição de Existência (isValidTriangle)")
    class ExistenceTests {

        @ParameterizedTest(name = "Lados ({0}, {1}, {2}) devem ser válidos")
        @CsvSource({
                "3.0, 4.0, 5.0",
                "5.0, 5.0, 5.0",
                "5.0, 5.0, 8.0",
                "6.0, 7.0, 8.0",
                "10.0, 15.0, 20.0"
        })
        @DisplayName("Deve retornar true para comprimentos que satisfazem a desigualdade triangular")
        void shouldReturnTrueForValidTriangles(double a, double b, double c) {
            assertThat(TriangleClassifier.isValidTriangle(a, b, c)).isTrue();
        }

        @ParameterizedTest(name = "Lados ({0}, {1}, {2}) são degenerados ou impossíveis")
        @CsvSource({
                "1.0, 2.0, 3.0",   // Soma de dois lados igual ao terceiro (linha reta)
                "1.0, 2.0, 5.0",   // Soma menor que o terceiro
                "10.0, 2.0, 3.0",  // Lado desproporcional
                "7.0, 3.0, 4.0"
        })
        @DisplayName("Deve retornar false para comprimentos que violam a desigualdade triangular")
        void shouldReturnFalseForViolatedInequality(double a, double b, double c) {
            assertThat(TriangleClassifier.isValidTriangle(a, b, c)).isFalse();
        }

        @ParameterizedTest(name = "Lados com zero ou negativo: ({0}, {1}, {2})")
        @CsvSource({
                "0.0, 4.0, 5.0",
                "3.0, 0.0, 5.0",
                "3.0, 4.0, 0.0",
                "-1.0, 4.0, 5.0",
                "3.0, -2.0, 5.0",
                "3.0, 4.0, -3.0",
                "-5.0, -5.0, -5.0"
        })
        @DisplayName("Deve retornar false para qualquer lado menor ou igual a zero")
        void shouldReturnFalseForNonPositiveSides(double a, double b, double c) {
            assertThat(TriangleClassifier.isValidTriangle(a, b, c)).isFalse();
        }
    }

    @Nested
    @DisplayName("Classificação de Triângulos (classify)")
    class ClassificationTests {

        @Test
        @DisplayName("Deve classificar triângulo equilátero (três lados iguais)")
        void shouldClassifyEquilateralTriangle() {
            assertThat(TriangleClassifier.classify(5.0, 5.0, 5.0))
                    .isEqualTo(TriangleType.EQUILATERAL);

            assertThat(TriangleClassifier.classify(12.75, 12.75, 12.75))
                    .isEqualTo(TriangleType.EQUILATERAL);
        }

        @Test
        @DisplayName("Deve classificar triângulo isósceles em qualquer permutação dos lados")
        void shouldClassifyIsoscelesTriangleInAllPermutations() {
            // Lados A e B iguais
            assertThat(TriangleClassifier.classify(5.0, 5.0, 8.0))
                    .isEqualTo(TriangleType.ISOSCELES);

            // Lados A e C iguais
            assertThat(TriangleClassifier.classify(5.0, 8.0, 5.0))
                    .isEqualTo(TriangleType.ISOSCELES);

            // Lados B e C iguais
            assertThat(TriangleClassifier.classify(8.0, 5.0, 5.0))
                    .isEqualTo(TriangleType.ISOSCELES);
        }

        @Test
        @DisplayName("Deve classificar triângulo escaleno (três lados distintos)")
        void shouldClassifyScaleneTriangle() {
            assertThat(TriangleClassifier.classify(3.0, 4.0, 5.0))
                    .isEqualTo(TriangleType.SCALENE);

            assertThat(TriangleClassifier.classify(6.0, 7.0, 8.0))
                    .isEqualTo(TriangleType.SCALENE);
        }

        @ParameterizedTest(name = "Triângulo inválido: ({0}, {1}, {2})")
        @CsvSource({
                "1.0, 2.0, 3.0",
                "1.0, 2.0, 10.0",
                "0.0, 5.0, 5.0",
                "-2.0, 4.0, 4.0"
        })
        @DisplayName("Deve lançar IllegalArgumentException ao classificar triângulo inválido")
        void shouldThrowExceptionWhenClassifyingInvalidTriangle(double a, double b, double c) {
            assertThatThrownBy(() -> TriangleClassifier.classify(a, b, c))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_INVALID);
        }
    }

    @Nested
    @DisplayName("Cálculo do Perímetro (calculatePerimeter)")
    class PerimeterTests {

        @Test
        @DisplayName("Deve calcular o perímetro de triângulo válido")
        void shouldCalculatePerimeterForValidTriangle() {
            double perimeter = TriangleClassifier.calculatePerimeter(3.0, 4.0, 5.0);
            assertThat(perimeter).isCloseTo(12.0, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular o perímetro com valores decimais")
        void shouldCalculatePerimeterWithDecimals() {
            double perimeter = TriangleClassifier.calculatePerimeter(2.5, 3.5, 4.0);
            assertThat(perimeter).isCloseTo(10.0, PRECISION);
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException ao calcular perímetro de triângulo inválido")
        void shouldThrowExceptionWhenCalculatingPerimeterOfInvalidTriangle() {
            assertThatThrownBy(() -> TriangleClassifier.calculatePerimeter(1.0, 2.0, 5.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_INVALID);
        }
    }
}
