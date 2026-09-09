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
            TripSummary summary = TripCostCalculator.calculateSummary(100, 10.0, 5.00);
            assertThat(summary.litersNeeded()).isCloseTo(10.0, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular litros com eficiência fracionária: 250 km a 12.5 km/l = 20 L")
        void shouldCalculateLitersFractionalEfficiency() {
            TripSummary summary = TripCostCalculator.calculateSummary(250, 12.5, 5.00);
            assertThat(summary.litersNeeded()).isCloseTo(20.0, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular litros exigindo precisão decimal: 100 km a 13 km/l ≈ 7.692 L")
        void shouldCalculateLitersDecimal() {
            TripSummary summary = TripCostCalculator.calculateSummary(100, 13.0, 5.00);
            assertThat(summary.litersNeeded()).isCloseTo(100.0 / 13.0, PRECISION);
        }
    }

    @Nested
    @DisplayName("Cálculo de Custo Total com Combustível")
    class TotalCostTests {

        @Test
        @DisplayName("Deve calcular custo total exato: 100 km a 10 km/l com R$ 5.50/L = R$ 55.00")
        void shouldCalculateTotalCostExact() {
            TripSummary summary = TripCostCalculator.calculateSummary(100, 10.0, 5.50);
            assertThat(summary.totalCost()).isCloseTo(55.0, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular custo total com dízima na litragem: 150 km a 14.0 km/l com R$ 5.89/L")
        void shouldCalculateTotalCostFractional() {
            double expectedLiters = 150.0 / 14.0;
            double expectedCost = expectedLiters * 5.89;

            TripSummary summary = TripCostCalculator.calculateSummary(150, 14.0, 5.89);
            assertThat(summary.totalCost()).isCloseTo(expectedCost, PRECISION);
        }
    }

    @Nested
    @DisplayName("Cálculo de Custo por Quilômetro")
    class CostPerKmTests {

        @Test
        @DisplayName("Deve calcular custo por km: 100 km com gasto total de R$ 55.00 = R$ 0.55/km")
        void shouldCalculateCostPerKm() {
            TripSummary summary = TripCostCalculator.calculateSummary(100, 10.0, 5.50);
            assertThat(summary.costPerKm()).isCloseTo(0.55, PRECISION);
        }

        @Test
        @DisplayName("Deve calcular custo por km em percurso longo: 800 km a 16 km/l com R$ 6.00/L = R$ 0.375/km")
        void shouldCalculateLongTripCostPerKm() {
            // 800 km / 16 km/l = 50 L * R$ 6.00/L = R$ 300.00 total / 800 km = R$ 0.375/km
            TripSummary summary = TripCostCalculator.calculateSummary(800, 16.0, 6.00);

            assertThat(summary.distanceInKm()).isEqualTo(800);
            assertThat(summary.litersNeeded()).isCloseTo(50.0, PRECISION);
            assertThat(summary.totalCost()).isCloseTo(300.0, PRECISION);
            assertThat(summary.costPerKm()).isCloseTo(0.375, PRECISION);
        }
    }

    @Nested
    @DisplayName("Validações Fail-Fast de Argumentos Inválidos")
    class ValidationTests {

        @ParameterizedTest(name = "Distância inválida: {0} km")
        @ValueSource(ints = {0, -1, -50, -1000})
        @DisplayName("Deve rejeitar distância menor ou igual a zero")
        void shouldRejectInvalidDistance(int invalidDistance) {
            assertThatThrownBy(() -> TripCostCalculator.calculateSummary(invalidDistance, 12.0, 5.50))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_DISTANCE);
        }

        @ParameterizedTest(name = "Eficiência inválida: {0} km/l")
        @ValueSource(doubles = {0.0, -0.1, -10.0})
        @DisplayName("Deve rejeitar eficiência menor ou igual a zero")
        void shouldRejectInvalidEfficiency(double invalidEfficiency) {
            assertThatThrownBy(() -> TripCostCalculator.calculateSummary(100, invalidEfficiency, 5.50))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_EFFICIENCY);
        }

        @ParameterizedTest(name = "Preço inválido: R$ {0}")
        @ValueSource(doubles = {0.0, -0.01, -5.89})
        @DisplayName("Deve rejeitar preço por litro menor ou igual a zero")
        void shouldRejectInvalidPrice(double invalidPrice) {
            assertThatThrownBy(() -> TripCostCalculator.calculateSummary(100, 10.0, invalidPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_PRICE);
        }
    }
}
