package br.com.fatec.basic.ex01;

import java.util.List;

/**
 * Utility calculator for compound interest and loan amortization schedules (SAC and Price).
 */
public final class FinancialCalculator {

    private FinancialCalculator() {
        // Utility class: prevent instantiation
    }

    /**
     * Calculates the future value of a principal under compound interest.
     * Formula: M = P * (1 + i)^n
     *
     * @param principal the initial amount invested, must be strictly positive
     * @param monthlyRate the monthly interest rate in decimal form (e.g. 0.01 for 1%)
     * @param months the investment duration in months, must be strictly positive
     * @return the total accumulated amount rounded to two decimal places
     * @throws IllegalArgumentException if any input violates the domain constraints
     */
    public static double calculateCompoundInterest(double principal, double monthlyRate, int months) {
        // TODO: Validate arguments and implement compound interest formula
        throw new UnsupportedOperationException("Method calculateCompoundInterest not implemented yet");
    }

    /**
     * Generates a detailed monthly amortization schedule for either SAC or Price system.
     *
     * @param principal the loan amount to be amortized, must be strictly positive
     * @param monthlyRate the monthly interest rate in decimal form (e.g. 0.015 for 1.5%)
     * @param months the loan period in months, must be strictly positive
     * @param system the desired amortization system (SAC or PRICE), cannot be null
     * @return a complete {@link FinancialReport} containing summary totals and the monthly schedule
     * @throws IllegalArgumentException if any input violates domain constraints
     */
    public static FinancialReport generateAmortizationSchedule(
            double principal,
            double monthlyRate,
            int months,
            AmortizationSystem system
    ) {
        // TODO: Validate arguments and generate the corresponding amortization schedule
        throw new UnsupportedOperationException("Method generateAmortizationSchedule not implemented yet");
    }

    /**
     * Rounds a floating-point value to exactly two decimal places (half-up cents rounding).
     *
     * @param value the raw double value
     * @return the value rounded to 2 decimal places
     */
    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
