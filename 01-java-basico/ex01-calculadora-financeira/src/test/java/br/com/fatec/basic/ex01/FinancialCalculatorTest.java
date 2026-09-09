package br.com.fatec.basic.ex01;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ex01 — FinancialCalculator Tests")
class FinancialCalculatorTest {

    private static final Offset<Double> CENT_PRECISION = Offset.offset(0.01);

    @Nested
    @DisplayName("Compound Interest Tests")
    class CompoundInterestTests {

        @Test
        @DisplayName("Should correctly calculate compound interest for standard scenario")
        void shouldCalculateCompoundInterestForStandardScenario() {
            // Principal: R$ 1,000.00, Rate: 1% (0.01) a month, 12 months
            // Formula: 1000 * (1 + 0.01)^12 = 1,126.82503... -> Rounded to 1,126.83
            double result = FinancialCalculator.calculateCompoundInterest(1000.0, 0.01, 12);

            assertThat(result).isCloseTo(1126.83, CENT_PRECISION);
        }

        @Test
        @DisplayName("Should return the original principal when interest rate is zero")
        void shouldReturnPrincipalWhenRateIsZero() {
            double result = FinancialCalculator.calculateCompoundInterest(5000.0, 0.0, 24);

            assertThat(result).isCloseTo(5000.0, CENT_PRECISION);
        }

        @Test
        @DisplayName("Should correctly compute single month accumulation")
        void shouldCalculateSingleMonth() {
            // Principal: 200.0, Rate: 5% (0.05), 1 month -> 210.00
            double result = FinancialCalculator.calculateCompoundInterest(200.0, 0.05, 1);

            assertThat(result).isCloseTo(210.00, CENT_PRECISION);
        }
    }

    @Nested
    @DisplayName("SAC (Sistema de Amortização Constante) Tests")
    class SacAmortizationTests {

        @Test
        @DisplayName("Should generate accurate SAC schedule with constant amortization and decreasing installments")
        void shouldGenerateAccurateSacSchedule() {
            // Loan: R$ 120,000.00, 1% monthly rate, 12 months
            double principal = 120_000.00;
            double rate = 0.01;
            int months = 12;

            FinancialReport report = FinancialCalculator.generateAmortizationSchedule(
                    principal,
                    rate,
                    months,
                    AmortizationSystem.SAC
            );

            assertThat(report).isNotNull();
            List<Installment> installments = report.installments();
            assertThat(installments).hasSize(months);

            // In SAC: constant amortization = principal / months = 10,000.00
            double expectedAmortization = 10_000.00;

            double calculatedTotalInterest = 0.0;
            double previousInstallment = Double.MAX_VALUE;
            double previousInterest = Double.MAX_VALUE;

            for (Installment inst : installments) {
                // Amortization must be strictly constant
                assertThat(inst.amortization()).isCloseTo(expectedAmortization, CENT_PRECISION);

                // Installments and interest must decrease monotonically
                assertThat(inst.installmentAmount()).isLessThan(previousInstallment);
                assertThat(inst.interest()).isLessThan(previousInterest);

                previousInstallment = inst.installmentAmount();
                previousInterest = inst.interest();
                calculatedTotalInterest += inst.interest();
            }

            // First month installment: A (10,000) + J (120,000 * 0.01 = 1,200) = 11,200.00
            assertThat(installments.get(0).installmentAmount()).isCloseTo(11_200.00, CENT_PRECISION);

            // Last month remaining balance must be zero
            assertThat(installments.get(months - 1).remainingBalance()).isCloseTo(0.0, CENT_PRECISION);

            // Total interest in SAC for 120k, 1% over 12m is R$ 7,800.00
            assertThat(report.totalInterest()).isCloseTo(7800.00, CENT_PRECISION);
            assertThat(report.totalPaid()).isCloseTo(127_800.00, CENT_PRECISION);
            assertThat(report.totalInterest()).isCloseTo(calculatedTotalInterest, CENT_PRECISION);
        }
    }

    @Nested
    @DisplayName("Price (French System) Tests")
    class PriceAmortizationTests {

        @Test
        @DisplayName("Should generate accurate Price schedule with constant installment amounts")
        void shouldGenerateAccuratePriceSchedule() {
            // Loan: R$ 10,000.00, 2% monthly rate, 5 months
            // PMT = 10,000 * (0.02 * (1.02)^5) / ((1.02)^5 - 1) = 2,121.5839... -> 2,121.58
            double principal = 10_000.00;
            double rate = 0.02;
            int months = 5;

            FinancialReport report = FinancialCalculator.generateAmortizationSchedule(
                    principal,
                    rate,
                    months,
                    AmortizationSystem.PRICE
            );

            assertThat(report).isNotNull();
            List<Installment> installments = report.installments();
            assertThat(installments).hasSize(months);

            double expectedInstallment = 2121.58;

            double previousAmortization = 0.0;
            double previousInterest = Double.MAX_VALUE;

            for (Installment inst : installments) {
                // Installment payment must be constant
                assertThat(inst.installmentAmount()).isCloseTo(expectedInstallment, CENT_PRECISION);

                // Interest decreases, amortization increases
                assertThat(inst.interest()).isLessThan(previousInterest);
                assertThat(inst.amortization()).isGreaterThan(previousAmortization);

                previousInterest = inst.interest();
                previousAmortization = inst.amortization();
            }

            // Month 1 breakdown: Juros = 10,000 * 0.02 = 200.00; Amort = 2,121.58 - 200 = 1,921.58
            assertThat(installments.get(0).interest()).isCloseTo(200.00, CENT_PRECISION);
            assertThat(installments.get(0).amortization()).isCloseTo(1921.58, CENT_PRECISION);

            // Last month remaining balance must be zero
            assertThat(installments.get(months - 1).remainingBalance()).isCloseTo(0.0, CENT_PRECISION);

            // Total paid must equal sum of installments and principal + total interest
            assertThat(report.totalPaid()).isCloseTo(principal + report.totalInterest(), CENT_PRECISION);
        }
    }

    @Nested
    @DisplayName("Validation and Domain Invariant Tests")
    class ValidationTests {

        @ParameterizedTest(name = "Invalid principal: {0}")
        @ValueSource(doubles = {0.0, -1.0, -1000.50})
        @DisplayName("Should throw IllegalArgumentException when principal is not strictly positive")
        void shouldRejectInvalidPrincipal(double invalidPrincipal) {
            assertThatThrownBy(() -> FinancialCalculator.calculateCompoundInterest(invalidPrincipal, 0.01, 12))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Principal must be strictly positive");

            assertThatThrownBy(() -> FinancialCalculator.generateAmortizationSchedule(
                    invalidPrincipal, 0.01, 12, AmortizationSystem.SAC))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Principal must be strictly positive");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when interest rate is negative")
        void shouldRejectNegativeInterestRate() {
            assertThatThrownBy(() -> FinancialCalculator.calculateCompoundInterest(1000.0, -0.05, 12))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Interest rate cannot be negative");

            assertThatThrownBy(() -> FinancialCalculator.generateAmortizationSchedule(
                    1000.0, -0.01, 12, AmortizationSystem.PRICE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Interest rate cannot be negative");
        }

        @ParameterizedTest(name = "Invalid months: {0}")
        @ValueSource(ints = {0, -1, -12})
        @DisplayName("Should throw IllegalArgumentException when months count is not strictly positive")
        void shouldRejectInvalidMonths(int invalidMonths) {
            assertThatThrownBy(() -> FinancialCalculator.calculateCompoundInterest(1000.0, 0.02, invalidMonths))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Number of months must be strictly positive");

            assertThatThrownBy(() -> FinancialCalculator.generateAmortizationSchedule(
                    1000.0, 0.02, invalidMonths, AmortizationSystem.SAC))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Number of months must be strictly positive");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when amortization system is null")
        void shouldRejectNullAmortizationSystem() {
            assertThatThrownBy(() -> FinancialCalculator.generateAmortizationSchedule(1000.0, 0.02, 12, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Amortization system cannot be null");
        }

        @Test
        @DisplayName("Should enforce immutability on returned installments list")
        void shouldEnforceImmutabilityOnInstallmentsList() {
            FinancialReport report = FinancialCalculator.generateAmortizationSchedule(
                    1000.0, 0.01, 3, AmortizationSystem.SAC);

            Installment fake = new Installment(99, 100.0, 100.0, 0.0, 0.0);

            assertThatThrownBy(() -> report.installments().add(fake))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
