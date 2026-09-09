package br.com.fatec.basic.ex02;

/**
 * Classe utilitária para cálculo de consumo e custos de viagens rodoviárias.
 */
public final class TripCostCalculator {

    private TripCostCalculator() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Calcula a quantidade de litros necessária para percorrer uma determinada distância.
     * Fórmula: distanceInKm / fuelEfficiencyKmPerLiter
     *
     * @param distanceInKm a distância em quilômetros, deve ser estritamente maior que zero
     * @param fuelEfficiencyKmPerLiter o consumo médio do veículo em km/l, deve ser estritamente maior que zero
     * @return a quantidade de litros de combustível necessária
     * @throws IllegalArgumentException se a distância ou o consumo forem menores ou iguais a zero
     */
    public static double calculateLitersNeeded(int distanceInKm, double fuelEfficiencyKmPerLiter) {
        // TODO: Validar argumentos e implementar o cálculo
        throw new UnsupportedOperationException("Método calculateLitersNeeded ainda não implementado");
    }

    /**
     * Calcula o custo financeiro total gasto com combustível durante a viagem.
     * Fórmula: litersNeeded * pricePerLiter
     *
     * @param distanceInKm a distância em quilômetros, deve ser maior que zero
     * @param fuelEfficiencyKmPerLiter o consumo médio do veículo em km/l, deve ser maior que zero
     * @param pricePerLiter o preço unitário do combustível por litro, deve ser maior que zero
     * @return o custo total em combustível
     * @throws IllegalArgumentException se qualquer um dos parâmetros for menor ou igual a zero
     */
    public static double calculateTotalCost(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter) {
        // TODO: Validar argumentos e implementar o cálculo
        throw new UnsupportedOperationException("Método calculateTotalCost ainda não implementado");
    }

    /**
     * Calcula o custo financeiro médio por quilômetro percorrido.
     * Fórmula: totalCost / distanceInKm
     *
     * @param distanceInKm a distância em quilômetros, deve ser maior que zero
     * @param fuelEfficiencyKmPerLiter o consumo médio do veículo em km/l, deve ser maior que zero
     * @param pricePerLiter o preço unitário do combustível por litro, deve ser maior que zero
     * @return o custo financeiro por quilômetro rodado
     * @throws IllegalArgumentException se qualquer um dos parâmetros for menor ou igual a zero
     */
    public static double calculateCostPerKm(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter) {
        // TODO: Validar argumentos e implementar o cálculo
        throw new UnsupportedOperationException("Método calculateCostPerKm ainda não implementado");
    }

    /**
     * Gera o resumo consolidado dos indicadores operacionais e financeiros da viagem.
     *
     * @param distanceInKm a distância em quilômetros, deve ser maior que zero
     * @param fuelEfficiencyKmPerLiter o consumo médio do veículo em km/l, deve ser maior que zero
     * @param pricePerLiter o preço unitário do combustível por litro, deve ser maior que zero
     * @return uma instância imutável de TripSummary contendo todos os dados calculados
     * @throws IllegalArgumentException se qualquer parâmetro for inválido
     */
    public static TripSummary calculateSummary(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter) {
        // TODO: Orquestrar os métodos anteriores e retornar a instância do record
        throw new UnsupportedOperationException("Método calculateSummary ainda não implementado");
    }
}
