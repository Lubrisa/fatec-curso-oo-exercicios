package br.com.fatec.basic.ex02;

/**
 * Implementação de referência para o cálculo de consumo e custos de viagem.
 */
public final class TripCostCalculator {

    private static final String ERROR_DISTANCE = "A distância deve ser maior que zero";
    private static final String ERROR_EFFICIENCY = "A eficiência de combustível deve ser maior que zero";
    private static final String ERROR_PRICE = "O preço do combustível deve ser maior que zero";

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
        validateDistance(distanceInKm);
        validateEfficiency(fuelEfficiencyKmPerLiter);

        return (double) distanceInKm / fuelEfficiencyKmPerLiter;
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
        validatePrice(pricePerLiter);
        double liters = calculateLitersNeeded(distanceInKm, fuelEfficiencyKmPerLiter);

        return liters * pricePerLiter;
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
        double totalCost = calculateTotalCost(distanceInKm, fuelEfficiencyKmPerLiter, pricePerLiter);

        return totalCost / (double) distanceInKm;
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
        double liters = calculateLitersNeeded(distanceInKm, fuelEfficiencyKmPerLiter);
        double totalCost = calculateTotalCost(distanceInKm, fuelEfficiencyKmPerLiter, pricePerLiter);
        double costPerKm = calculateCostPerKm(distanceInKm, fuelEfficiencyKmPerLiter, pricePerLiter);

        return new TripSummary(distanceInKm, liters, totalCost, costPerKm);
    }

    private static void validateDistance(int distanceInKm) {
        if (distanceInKm <= 0) {
            throw new IllegalArgumentException(ERROR_DISTANCE);
        }
    }

    private static void validateEfficiency(double fuelEfficiencyKmPerLiter) {
        if (fuelEfficiencyKmPerLiter <= 0.0) {
            throw new IllegalArgumentException(ERROR_EFFICIENCY);
        }
    }

    private static void validatePrice(double pricePerLiter) {
        if (pricePerLiter <= 0.0) {
            throw new IllegalArgumentException(ERROR_PRICE);
        }
    }
}
