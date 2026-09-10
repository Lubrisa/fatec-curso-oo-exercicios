package br.com.fatec.basic.ex11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

@DisplayName("ex11 - Suíte de Testes do Analisador de Vetores")
class VectorAnalyzerTest {

    private static final double EPSILON = 0.0001;

    @Nested
    @DisplayName("1. Testes de Cálculo de Soma")
    class SumTests {

        @Test
        @DisplayName("Deve calcular corretamente a soma de valores positivos")
        void shouldCalculateSumOfPositiveValues() {
            double[] values = {10.0, 20.5, 30.5};
            double sum = VectorAnalyzer.calculateSum(values);
            assertThat(sum).isCloseTo(61.0, within(EPSILON));
        }

        @Test
        @DisplayName("Deve calcular corretamente a soma com números negativos")
        void shouldCalculateSumWithNegativeValues() {
            double[] values = {-5.0, 10.0, -2.5};
            double sum = VectorAnalyzer.calculateSum(values);
            assertThat(sum).isCloseTo(2.5, within(EPSILON));
        }

        @Test
        @DisplayName("Deve retornar o próprio valor para vetor unitário")
        void shouldReturnSameValueForSingleElementArray() {
            double[] values = {42.75};
            double sum = VectorAnalyzer.calculateSum(values);
            assertThat(sum).isCloseTo(42.75, within(EPSILON));
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para vetor nulo")
        void shouldThrowExceptionWhenArrayIsNull() {
            assertThatThrownBy(() -> VectorAnalyzer.calculateSum(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_NULL_ARRAY);
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para vetor vazio")
        void shouldThrowExceptionWhenArrayIsEmpty() {
            assertThatThrownBy(() -> VectorAnalyzer.calculateSum(new double[0]))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_EMPTY_ARRAY);
        }
    }

    @Nested
    @DisplayName("2. Testes de Cálculo de Média Aritmética")
    class AverageTests {

        @Test
        @DisplayName("Deve calcular a média de valores inteiros e decimais")
        void shouldCalculateAverageAccurately() {
            double[] values = {7.5, 8.5, 9.5, 6.5};
            double average = VectorAnalyzer.calculateAverage(values);
            assertThat(average).isCloseTo(8.0, within(EPSILON));
        }

        @Test
        @DisplayName("Deve calcular média com valores que resultam em dízima periódica")
        void shouldHandlePeriodicDecimalsInAverage() {
            double[] values = {10.0, 10.0, 20.0};
            double average = VectorAnalyzer.calculateAverage(values);
            assertThat(average).isCloseTo(13.333333333333334, within(EPSILON));
        }

        @Test
        @DisplayName("Deve lançar exceção para vetor nulo ou vazio ao calcular média")
        void shouldThrowExceptionForInvalidArraysInAverage() {
            assertThatThrownBy(() -> VectorAnalyzer.calculateAverage(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_NULL_ARRAY);

            assertThatThrownBy(() -> VectorAnalyzer.calculateAverage(new double[0]))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_EMPTY_ARRAY);
        }
    }

    @Nested
    @DisplayName("3. Testes de Menor e Maior Valor")
    class MinMaxTests {

        @Test
        @DisplayName("Deve identificar min e max em conjunto de valores mistos")
        void shouldFindMinAndMaxWithMixedValues() {
            double[] values = {15.0, -3.5, 42.0, 0.0, -10.5};
            assertThat(VectorAnalyzer.findMin(values)).isCloseTo(-10.5, within(EPSILON));
            assertThat(VectorAnalyzer.findMax(values)).isCloseTo(42.0, within(EPSILON));
        }

        @Test
        @DisplayName("Deve encontrar min e max em vetor com apenas valores negativos")
        void shouldFindMinAndMaxWithOnlyNegativeValues() {
            double[] values = {-50.0, -20.0, -80.0, -10.0};
            assertThat(VectorAnalyzer.findMin(values)).isCloseTo(-80.0, within(EPSILON));
            assertThat(VectorAnalyzer.findMax(values)).isCloseTo(-10.0, within(EPSILON));
        }

        @Test
        @DisplayName("Deve retornar o mesmo valor para vetor com elementos idênticos")
        void shouldReturnSameValueWhenAllElementsAreEqual() {
            double[] values = {5.5, 5.5, 5.5};
            assertThat(VectorAnalyzer.findMin(values)).isCloseTo(5.5, within(EPSILON));
            assertThat(VectorAnalyzer.findMax(values)).isCloseTo(5.5, within(EPSILON));
        }

        @Test
        @DisplayName("Deve lançar exceções para nulo e vazio em min e max")
        void shouldThrowExceptionsForInvalidInputsInMinMax() {
            assertThatThrownBy(() -> VectorAnalyzer.findMin(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_NULL_ARRAY);

            assertThatThrownBy(() -> VectorAnalyzer.findMin(new double[0]))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_EMPTY_ARRAY);

            assertThatThrownBy(() -> VectorAnalyzer.findMax(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_NULL_ARRAY);

            assertThatThrownBy(() -> VectorAnalyzer.findMax(new double[0]))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_EMPTY_ARRAY);
        }
    }

    @Nested
    @DisplayName("4. Testes do Relatório Consolidado (VectorStatistics)")
    class StatisticsTests {

        @Test
        @DisplayName("Deve calcular todas as estatísticas consolidadas corretamente")
        void shouldCalculateFullStatisticsReport() {
            double[] values = {12.0, 4.0, 5.0, 15.0, 9.0};
            VectorStatistics stats = VectorAnalyzer.calculateStatistics(values);

            assertThat(stats.count()).isEqualTo(5);
            assertThat(stats.sum()).isCloseTo(45.0, within(EPSILON));
            assertThat(stats.average()).isCloseTo(9.0, within(EPSILON));
            assertThat(stats.min()).isCloseTo(4.0, within(EPSILON));
            assertThat(stats.max()).isCloseTo(15.0, within(EPSILON));
            assertThat(stats.amplitude()).isCloseTo(11.0, within(EPSILON));
        }

        @Test
        @DisplayName("Deve gerar estatísticas válidas para vetor unitário")
        void shouldGenerateValidStatsForSingleElement() {
            double[] values = {100.0};
            VectorStatistics stats = VectorAnalyzer.calculateStatistics(values);

            assertThat(stats.count()).isEqualTo(1);
            assertThat(stats.sum()).isCloseTo(100.0, within(EPSILON));
            assertThat(stats.average()).isCloseTo(100.0, within(EPSILON));
            assertThat(stats.min()).isCloseTo(100.0, within(EPSILON));
            assertThat(stats.max()).isCloseTo(100.0, within(EPSILON));
            assertThat(stats.amplitude()).isCloseTo(0.0, within(EPSILON));
        }

        @Test
        @DisplayName("Deve calcular corretamente com números negativos e amplitude térmica")
        void shouldCalculateStatsWithNegativeRange() {
            double[] values = {-3.5, 0.0, 4.5, 11.0, -2.0};
            VectorStatistics stats = VectorAnalyzer.calculateStatistics(values);

            assertThat(stats.count()).isEqualTo(5);
            assertThat(stats.sum()).isCloseTo(10.0, within(EPSILON));
            assertThat(stats.average()).isCloseTo(2.0, within(EPSILON));
            assertThat(stats.min()).isCloseTo(-3.5, within(EPSILON));
            assertThat(stats.max()).isCloseTo(11.0, within(EPSILON));
            assertThat(stats.amplitude()).isCloseTo(14.5, within(EPSILON));
        }

        @Test
        @DisplayName("Deve validar o Record VectorStatistics quanto à contagem inválida")
        void shouldRejectInvalidCountInRecord() {
            assertThatThrownBy(() -> new VectorStatistics(0, 0.0, 0.0, 0.0, 0.0, 0.0))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> new VectorStatistics(-1, 0.0, 0.0, 0.0, 0.0, 0.0))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Deve lançar exceções para nulo e vazio ao calcular estatísticas")
        void shouldThrowExceptionsForInvalidInputsInStatistics() {
            assertThatThrownBy(() -> VectorAnalyzer.calculateStatistics(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_NULL_ARRAY);

            assertThatThrownBy(() -> VectorAnalyzer.calculateStatistics(new double[0]))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_EMPTY_ARRAY);
        }
    }

    @Nested
    @DisplayName("5. Testes de Contagem de Elementos Acima da Média")
    class AboveAverageTests {

        @Test
        @DisplayName("Deve contar quantos elementos são estritamente maiores que a média")
        void shouldCountElementsAboveAverage() {
            // Valores: 10, 20, 30, 40, 50 -> Média = 30.0 -> Elementos > 30: 40 e 50 (total 2)
            double[] values = {10.0, 20.0, 30.0, 40.0, 50.0};
            int count = VectorAnalyzer.countAboveAverage(values);
            assertThat(count).isEqualTo(2);
        }

        @Test
        @DisplayName("Deve retornar 0 quando todos os elementos forem iguais à média")
        void shouldReturnZeroWhenAllElementsEqualAverage() {
            double[] values = {5.0, 5.0, 5.0};
            int count = VectorAnalyzer.countAboveAverage(values);
            assertThat(count).isZero();
        }

        @Test
        @DisplayName("Deve calcular corretamente quando apenas um valor supera a média")
        void shouldHandleSingleOutlierAboveAverage() {
            // Valores: 1, 1, 10 -> Média = 4.0 -> Apenas 10 supera a média (total 1)
            double[] values = {1.0, 1.0, 10.0};
            int count = VectorAnalyzer.countAboveAverage(values);
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("Deve lançar exceções para nulo e vazio em countAboveAverage")
        void shouldThrowExceptionsForInvalidInputsInCountAboveAverage() {
            assertThatThrownBy(() -> VectorAnalyzer.countAboveAverage(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_NULL_ARRAY);

            assertThatThrownBy(() -> VectorAnalyzer.countAboveAverage(new double[0]))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(VectorAnalyzer.ERROR_EMPTY_ARRAY);
        }
    }
}
