package br.com.fatec.basic.ex01;

import java.util.List;
import java.util.Objects;

/**
 * Encapsulates the complete summary and monthly schedule of a loan simulation.
 *
 * @param totalInterest the total interest amount paid over the life of the loan
 * @param totalPaid the grand total paid (principal + total interest)
 * @param installments the unmodifiable chronological list of monthly installments
 */
public record FinancialReport(
        double totalInterest,
        double totalPaid,
        List<Installment> installments
) {

    /**
     * Compact canonical constructor enforcing defensive immutability.
     *
     * @param totalInterest the total interest amount
     * @param totalPaid the total paid amount
     * @param installments the list of installments, copied defensively
     */
    public FinancialReport {
        Objects.requireNonNull(installments, "Installments list cannot be null");
        installments = List.copyOf(installments);
    }
}
