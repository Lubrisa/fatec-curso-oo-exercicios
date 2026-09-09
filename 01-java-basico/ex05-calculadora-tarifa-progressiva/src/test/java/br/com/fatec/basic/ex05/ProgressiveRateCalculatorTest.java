package br.com.fatec.basic.ex05;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ex05 — Testes da classe ProgressiveRateCalculator")
class ProgressiveRateCalculatorTest {

    private static final String ERROR_NEGATIVE = "O consumo não pode ser negativo";
    private static final Offset<Double> PRECISION = Offset.offset(0.001);

    @Nested
    @DisplayName("Cálculo do Custo de Energia por Faixas (calculateEnergyCost)")
    class EnergyCostTests {

        @Test
        @DisplayName("Deve faturar R$ 0,00 de energia para consumo zero")
        void shouldReturnZeroCostForZeroConsumption() {
            double cost = ProgressiveRateCalculator.calculateEnergyCost(0.0);
            assertThat(cost).isCloseTo(0.0, PRECISION);
        }

        @ParameterizedTest(name = "Consumo {0} kWh na Faixa 1 deve custar R$ {1}")
        @CsvSource({
                "10.0, 5.00",
                "50.0, 25.00",
                "80.0, 40.00",
                "100.0, 50.00" // Limite superior da Faixa 1
        })
        @DisplayName("Deve calcular corretamente consumos situados inteiramente na Faixa 1 (até 100 kWh)")
        void shouldCalculateTier1Consumption(double consumption, double expectedCost) {
            double cost = ProgressiveRateCalculator.calculateEnergyCost(consumption);
            assertThat(cost).isCloseTo(expectedCost, PRECISION);
        }

        @ParameterizedTest(name = "Consumo {0} kWh na Faixa 2 deve custar R$ {1}")
        @CsvSource({
                "120.0, 65.00",  // 100 * 0.50 + 20 * 0.75 = 50 + 15
                "150.0, 87.50",  // 100 * 0.50 + 50 * 0.75 = 50 + 37.5
                "180.0, 110.00", // 100 * 0.50 + 80 * 0.75 = 50 + 60
                "200.0, 125.00"  // Limite superior da Faixa 2: 50 + 75
        })
        @DisplayName("Deve calcular fatiamento cumulativo na Faixa 2 (100 a 200 kWh)")
        void shouldCalculateTier2Consumption(double consumption, double expectedCost) {
            double cost = ProgressiveRateCalculator.calculateEnergyCost(consumption);
            assertThat(cost).isCloseTo(expectedCost, PRECISION);
        }

        @ParameterizedTest(name = "Consumo {0} kWh na Faixa 3 deve custar R$ {1}")
        @CsvSource({
                "210.0, 135.00", // 125 + 10 * 1.00
                "250.0, 175.00", // 125 + 50 * 1.00
                "300.0, 225.00", // 125 + 100 * 1.00
                "450.0, 375.00"  // 125 + 250 * 1.00
        })
        @DisplayName("Deve calcular fatiamento cumulativo na Faixa 3 (acima de 200 kWh)")
        void shouldCalculateTier3Consumption(double consumption, double expectedCost) {
            double cost = ProgressiveRateCalculator.calculateEnergyCost(consumption);
            assertThat(cost).isCloseTo(expectedCost, PRECISION);
        }

        @ParameterizedTest(name = "Consumo inválido: {0} kWh")
        @CsvSource({
                "-0.01",
                "-1.0",
                "-50.0",
                "-500.0"
        })
        @DisplayName("Deve lançar IllegalArgumentException para qualquer consumo estritamente negativo")
        void shouldThrowExceptionForNegativeConsumption(double negativeConsumption) {
            assertThatThrownBy(() -> ProgressiveRateCalculator.calculateEnergyCost(negativeConsumption))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_NEGATIVE);
        }
    }

    @Nested
    @DisplayName("Consolidação da Fatura Residencial (calculateBill)")
    class BillReportTests {

        @Test
        @DisplayName("Deve consolidar fatura com taxa mínima de iluminação para consumo 0 kWh")
        void shouldGenerateBillForZeroConsumption() {
            EnergyBill bill = ProgressiveRateCalculator.calculateBill(0.0);

            assertThat(bill.consumptionKwh()).isCloseTo(0.0, PRECISION);
            assertThat(bill.energyCost()).isCloseTo(0.0, PRECISION);
            assertThat(bill.publicLightingFee()).isCloseTo(15.00, PRECISION);
            assertThat(bill.totalAmount()).isCloseTo(15.00, PRECISION);
        }

        @Test
        @DisplayName("Deve consolidar fatura detalhada para consumo intermediário (150 kWh)")
        void shouldGenerateBillForModerateConsumption() {
            EnergyBill bill = ProgressiveRateCalculator.calculateBill(150.0);

            assertThat(bill.consumptionKwh()).isCloseTo(150.0, PRECISION);
            assertThat(bill.energyCost()).isCloseTo(87.50, PRECISION);
            assertThat(bill.publicLightingFee()).isCloseTo(15.00, PRECISION);
            assertThat(bill.totalAmount()).isCloseTo(102.50, PRECISION);
        }

        @Test
        @DisplayName("Deve consolidar fatura detalhada para consumo elevado (250 kWh)")
        void shouldGenerateBillForHighConsumption() {
            EnergyBill bill = ProgressiveRateCalculator.calculateBill(250.0);

            assertThat(bill.consumptionKwh()).isCloseTo(250.0, PRECISION);
            assertThat(bill.energyCost()).isCloseTo(175.00, PRECISION);
            assertThat(bill.publicLightingFee()).isCloseTo(15.00, PRECISION);
            assertThat(bill.totalAmount()).isCloseTo(190.00, PRECISION);
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException ao tentar faturar consumo negativo")
        void shouldThrowExceptionWhenBillingNegativeConsumption() {
            assertThatThrownBy(() -> ProgressiveRateCalculator.calculateBill(-15.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_NEGATIVE);
        }
    }
}
