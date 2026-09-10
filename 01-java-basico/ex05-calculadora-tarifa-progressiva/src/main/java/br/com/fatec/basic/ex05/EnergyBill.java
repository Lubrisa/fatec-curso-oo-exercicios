package br.com.fatec.basic.ex05;

/**
 * [ESTRUTURA FORNECIDA PRONTA — NÃO É NECESSÁRIO MODIFICAR ESTE ARQUIVO]
 *
 * Registro imutável que consolida a fatura de energia elétrica residencial.
 * Utilizado como tipo de retorno do método ProgressiveRateCalculator.calculateBill(...).
 *
 * @param consumptionKwh     consumo faturado no ciclo em kWh
 * @param energyCost         custo calculado pela soma das faixas cumulativas de consumo
 * @param publicLightingFee taxa de iluminação pública fixa
 * @param totalAmount        valor total da fatura (energia + iluminação pública)
 */
public record EnergyBill(
        double consumptionKwh,
        double energyCost,
        double publicLightingFee,
        double totalAmount
) {
}
