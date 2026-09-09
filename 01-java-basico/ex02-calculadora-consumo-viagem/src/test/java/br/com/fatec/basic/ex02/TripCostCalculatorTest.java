package br.com.fatec.basic.ex02;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ex02 — Testes da classe TripCostCalculator")
class TripCostCalculatorTest {

    private static final Offset<Double> PRECISION = Offset.offset(0.001);

    private static final String ERROR_DISTANCE = "A distância deve ser maior que zero";
    private static final String ERROR_EFFICIENCY = "A eficiência de combustível deve ser maior que zero";
    private static final String ERROR_PRICE = "O preço do combustível deve ser maior que zero";

    @Nested
    @DisplayName("Cálculo de Litros Necessários")
    class LitersNeededTests {

        @Test
        @DisplayName("Deve calcular litros com valores inteiros e exatos: 100 km a 10 km/l = 10 L")
        void shouldCalculateLitersExact() {
            double liters = TripCostCalculator.calculateLitersNeeded(100, 10.0);
            assertThat(liters).isCloseTo(10.0, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular litros com valores fracionários: 250 km a 12.5 km/l = 20 L")
        void shouldCalculateLitersFractionalEfficiency() {
            double liters = TripCostCalculator.calculateLitersNeeded(250, 12.5);
            assertThat(liters).isCloseTo(20.0, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular litros exigindo precisão decimal: 100 km a 13 km/l ≈ 7.692 L")
        void shouldCalculateLitersDecimal() {
            double liters = TripCostCalculator.calculateLitersNeeded(100, 13.0);
            assertThat(liters).isCloseTo(100.0 / 13.0, PRECISION);
        }

        @ParameterizedTest(name = "Distância inválida: {0} km")
        @ValueSource(ints = {0, -1, -50, -1000})
        @DisplayName("Deve rejeitar distância menor ou igual a zero")
        void shouldRejectInvalidDistance(int invalidDistance) {
            assertThatThrownBy(() -> TripCostCalculator.calculateLitersNeeded(invalidDistance, 12.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_DISTANCE);
        }

        @ParameterizedTest(name = "Eficiência inválida: {0} km/l")
        @ValueSource(doubles = {0.0, -0.1, -10.0})
        @DisplayName("Deve rejeitar eficiência menor ou igual a zero")
        void shouldRejectInvalidEfficiency(double invalidEfficiency) {
            assertThatThrownBy(() -> TripCostCalculator.calculateLitersNeeded(100, invalidEfficiency))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_EFFICIENCY);
        }
    }

    @Nested
    @DisplayName("Cálculo de Custo Total")
    class TotalCostTests {

        @Test
        @DisplayName("Deve calcular custo total: 100 km a 10 km/l com R$ 5.50/L = R$ 55.00")
        void shouldCalculateTotalCost() {
            double totalCost = TripCostCalculator.calculateTotalCost(100, 10.0, 5.50);
            assertThat(totalCost).isCloseTo(55.0, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular custo total com dízima na litragem: 150 km a 14.0 km/l com R$ 5.89/L")
        void shouldCalculateTotalCostFractional() {
            double expected = (150.0 / 14.0) * 5.89;
            double totalCost = TripCostCalculator.calculateTotalCost(150, 14.0, 5.89);
            assertThat(totalCost).isCloseTo(expected, PRECISION);
        }

        @ParameterizedTest(name = "Preço inválido: R$ {0}")
        @ValueSource(doubles = {0.0, -0.01, -5.89})
        @DisplayName("Deve rejeitar preço por litro menor ou igual a zero")
        void shouldRejectInvalidPrice(double invalidPrice) {
            assertThatThrownBy(() -> TripCostCalculator.calculateTotalCost(100, 10.0, invalidPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_PRICE);
        }
    }

    @Nested
    @DisplayName("Cálculo de Custo por Quilômetro")
    class CostPerKmTests {

        @Test
        @DisplayName("Deve calcular custo por km: 100 km com gasto total de R$ 55.00 = R$ 0.55/km")
        void shouldCalculateCostPerKm() {
            double costPerKm = TripCostCalculator.calculateCostPerKm(100, 10.0, 5.50);
            assertThat(costPerKm).isCloseTo(0.55, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular custo por km em percurso longo: 800 km a 16 km/l com R$ 6.00/L")
        void shouldCalculateLongTripCostPerKm() {
            // 800 / 16 = 50 litros * 6.00 = R$ 300.00 total / 800 km = 0.375 R$/km
            double costPerKm = TripCostCalculator.calculateCostPerKm(800, 16.0, 6.00);
            assertThat(costPerKm).isCloseTo(0.375, PRECISION);
        }
    }

    @Nested
    @DisplayName("Geração do Resumo Consolidado (TripSummary)")
    class SummaryTests {

        @Test
        @DisplayName("Deve gerar resumo consolidado corretamente para viagem padrão")
        void shouldGenerateSummaryCorrectly() {
            int distance = 400;
            double efficiency = 16.0;
            double price = 6.00;

            TripSummary summary = TripCostCalculator.calculateSummary(distance, efficiency, price);

            assertThat(summary).isNotNull();
            assertThat(summary.distanceInKm()).isEqualTo(400);
            assertThat(summary.litersNeeded()).isCloseTo(25.0, PRECISION);
            assertThat(summary.totalCost()).isCloseTo(150.0, PRECISION);
            assertThat(summary.costPerKm()).isCloseTo(0.375, PRECISION);
        }

        @Test
        @DisplayName("Deve lançar exceção no resumo quando qualquer parâmetro for inválido")
        void shouldPropagateExceptionsInSummary() {
            assertThatThrownBy(() -> TripCostCalculator.calculateSummary(0, 10.0, 5.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_DISTANCE);

            assertThatThrownBy(() -> TripCostCalculator.calculateSummary(100, -2.0, 5.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_EFFICIENCY);

            assertThatThrownBy(() -> TripCostCalculator.calculateSummary(100, 10.0, 0.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_PRICE);
        }
    }
}
