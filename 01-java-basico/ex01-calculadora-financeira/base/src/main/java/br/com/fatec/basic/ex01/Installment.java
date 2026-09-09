package br.com.fatec.basic.ex01;

/**
 * Immutable representation of a single loan installment schedule row.
 *
 * @param month the sequential installment month (1-indexed)
 * @param installmentAmount the total payment amount for this month (amortization + interest)
 * @param amortization the principal amount paid off in this month
 * @param interest the interest fee charged for this month
 * @param remainingBalance the remaining loan balance after this installment's amortization
 */
public record Installment(
        int month,
        double installmentAmount,
        double amortization,
        double interest,
        double remainingBalance
) {}
