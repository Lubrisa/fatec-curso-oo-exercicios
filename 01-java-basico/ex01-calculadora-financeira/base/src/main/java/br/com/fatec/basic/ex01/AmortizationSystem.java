package br.com.fatec.basic.ex01;

/**
 * Defines the supported loan amortization systems.
 */
public enum AmortizationSystem {

    /**
     * Sistema de Amortização Constante (Constant Amortization System).
     * The principal repayment remains constant across all payment periods.
     */
    SAC,

    /**
     * Tabela Price (French Amortization System).
     * The total installment payment remains constant across all payment periods.
     */
    PRICE
}
