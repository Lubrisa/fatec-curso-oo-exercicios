package br.com.fatec.basic.ex01;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference implementation of the financial calculator.
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
        validateInputs(principal, monthlyRate, months);

        double accumulated = principal * Math.pow(1.0 + monthlyRate, months);
        return roundToTwoDecimals(accumulated);
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
        validateInputs(principal, monthlyRate, months);
        if (system == null) {
            throw new IllegalArgumentException("Amortization system cannot be null");
        }

        List<Installment> installments = new ArrayList<>(months);
        double remainingBalance = principal;
        double totalInterest = 0.0;
        double totalPaid = 0.0;

        if (system == AmortizationSystem.SAC) {
            // Constant amortization
            double constantAmortization = roundToTwoDecimals(principal / months);

            for (int month = 1; month <= months; month++) {
                double interest = roundToTwoDecimals(remainingBalance * monthlyRate);
                double amortization = (month == months) ? remainingBalance : constantAmortization;
                double installmentAmount = roundToTwoDecimals(amortization + interest);

                remainingBalance = (month == months) ? 0.0 : roundToTwoDecimals(remainingBalance - amortization);

                totalInterest += interest;
                totalPaid += installmentAmount;

                installments.add(new Installment(
                        month,
                        installmentAmount,
                        amortization,
                        interest,
                        remainingBalance
                ));
            }
        } else {
            // Price (French) system: constant installment
            double factor = Math.pow(1.0 + monthlyRate, months);
            double fixedInstallment = roundToTwoDecimals(principal * (monthlyRate * factor) / (factor - 1.0));

            for (int month = 1; month <= months; month++) {
                double interest = roundToTwoDecimals(remainingBalance * monthlyRate);
                double amortization = (month == months) ? remainingBalance : roundToTwoDecimals(fixedInstallment - interest);
                double installmentAmount = (month == months) ? roundToTwoDecimals(amortization + interest) : fixedInstallment;

                remainingBalance = (month == months) ? 0.0 : roundToTwoDecimals(remainingBalance - amortization);

                totalInterest += interest;
                totalPaid += installmentAmount;

                installments.add(new Installment(
                        month,
                        installmentAmount,
                        amortization,
                        interest,
                        remainingBalance
                ));
            }
        }

        return new FinancialReport(
                roundToTwoDecimals(totalInterest),
                roundToTwoDecimals(totalPaid),
                installments
        );
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

    private static void validateInputs(double principal, double monthlyRate, int months) {
        if (principal <= 0.0) {
            throw new IllegalArgumentException("Principal must be strictly positive");
        }
        if (monthlyRate < 0.0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (months <= 0) {
            throw new IllegalArgumentException("Number of months must be strictly positive");
        }
    }
}
