package br.com.fatec.basic.ex05;

/**
 * Implementação de referência para cálculo de faturamento progressivo cumulativo.
 */
public final class ProgressiveRateCalculator {

    public static final double TIER1_LIMIT = 100.0;
    public static final double TIER2_LIMIT = 200.0;

    public static final double TIER1_RATE = 0.50;
    public static final double TIER2_RATE = 0.75;
    public static final double TIER3_RATE = 1.00;

    public static final double PUBLIC_LIGHTING_FEE = 15.00;

    private static final String ERROR_NEGATIVE_CONSUMPTION = "O consumo não pode ser negativo";

    private ProgressiveRateCalculator() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Calcula o custo da energia consumida aplicando as faixas progressivas cumulativas.
     *
     * @param consumptionKwh consumo em kWh no ciclo de medição
     * @return custo do consumo em reais
     * @throws IllegalArgumentException se consumptionKwh for menor que zero
     */
    public static double calculateEnergyCost(double consumptionKwh) {
        if (consumptionKwh < 0.0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_CONSUMPTION);
        }

        if (consumptionKwh <= TIER1_LIMIT) {
            return consumptionKwh * TIER1_RATE;
        } else if (consumptionKwh <= TIER2_LIMIT) {
            double tier1Cost = TIER1_LIMIT * TIER1_RATE;
            double tier2Cost = (consumptionKwh - TIER1_LIMIT) * TIER2_RATE;
            return tier1Cost + tier2Cost;
        } else {
            double tier1Cost = TIER1_LIMIT * TIER1_RATE;
            double tier2Cost = (TIER2_LIMIT - TIER1_LIMIT) * TIER2_RATE;
            double tier3Cost = (consumptionKwh - TIER2_LIMIT) * TIER3_RATE;
            return tier1Cost + tier2Cost + tier3Cost;
        }
    }

    /**
     * Consolida a fatura residencial completa de energia elétrica (energia + taxa de iluminação).
     *
     * @param consumptionKwh consumo em kWh no ciclo de medição
     * @return objeto imutável EnergyBill com o faturamento detalhado
     * @throws IllegalArgumentException se consumptionKwh for menor que zero
     */
    public static EnergyBill calculateBill(double consumptionKwh) {
        double energyCost = calculateEnergyCost(consumptionKwh);
        double totalAmount = energyCost + PUBLIC_LIGHTING_FEE;
        return new EnergyBill(consumptionKwh, energyCost, PUBLIC_LIGHTING_FEE, totalAmount);
    }
}
