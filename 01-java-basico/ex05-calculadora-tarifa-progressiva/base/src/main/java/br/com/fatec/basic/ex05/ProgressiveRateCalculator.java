package br.com.fatec.basic.ex05;

/**
 * Calculadora de faturamento progressivo de energia elétrica por faixas cumulativas.
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
     * <p>Regras de Fatiamento:
     * - Até 100.0 kWh: R$ 0,50 por kWh.
     * - De 100.01 a 200.0 kWh: primeiros 100 kWh a R$ 0,50 + excedente a R$ 0,75.
     * - Acima de 200.0 kWh: primeiros 100 kWh a R$ 0,50 + 100 kWh seguintes a R$ 0,75 + excedente a R$ 1,00.</p>
     *
     * @param consumptionKwh consumo em kWh no ciclo de medição
     * @return custo do consumo em reais
     * @throws IllegalArgumentException se consumptionKwh for menor que zero
     */
    public static double calculateEnergyCost(double consumptionKwh) {
        // TODO: Validar se o consumo é negativo e lançar IllegalArgumentException
        // TODO: Implementar faturamento cumulativo por faixas com if / else if / else
        throw new UnsupportedOperationException("Método calculateEnergyCost ainda não implementado");
    }

    /**
     * Consolida a fatura residencial completa de energia elétrica (energia + taxa de iluminação).
     *
     * @param consumptionKwh consumo em kWh no ciclo de medição
     * @return objeto imutável EnergyBill com o faturamento detalhado
     * @throws IllegalArgumentException se consumptionKwh for menor que zero
     */
    public static EnergyBill calculateBill(double consumptionKwh) {
        // TODO: Obter o custo de energia via calculateEnergyCost
        // TODO: Retornar nova instância de EnergyBill com totalAmount = energyCost + PUBLIC_LIGHTING_FEE
        throw new UnsupportedOperationException("Método calculateBill ainda não implementado");
    }
}
