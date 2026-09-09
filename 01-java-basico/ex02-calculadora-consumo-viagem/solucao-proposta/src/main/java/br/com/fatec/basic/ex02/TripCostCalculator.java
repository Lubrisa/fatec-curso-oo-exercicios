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
     * Gera o resumo consolidado dos indicadores operacionais e financeiros da viagem.
     *
     * @param distanceInKm a distância em quilômetros, deve ser maior que zero
     * @param fuelEfficiencyKmPerLiter o consumo médio do veículo em km/l, deve ser maior que zero
     * @param pricePerLiter o preço unitário do combustível por litro, deve ser maior que zero
     * @return uma instância imutável de TripSummary contendo todos os dados calculados
     * @throws IllegalArgumentException se qualquer parâmetro for menor ou igual a zero
     */
    public static TripSummary calculateSummary(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter) {
        validateInputs(distanceInKm, fuelEfficiencyKmPerLiter, pricePerLiter);

        double litersNeeded = calculateLitersNeeded(distanceInKm, fuelEfficiencyKmPerLiter);
        double totalCost = calculateTotalCost(litersNeeded, pricePerLiter);
        double costPerKm = calculateCostPerKm(totalCost, distanceInKm);

        return new TripSummary(distanceInKm, litersNeeded, totalCost, costPerKm);
    }

    /**
     * Calcula a quantidade de litros necessária para percorrer uma determinada distância.
     * Fórmula: distanceInKm / fuelEfficiencyKmPerLiter
     *
     * @param distanceInKm a distância em quilômetros
     * @param fuelEfficiencyKmPerLiter o consumo médio do veículo em km/l
     * @return a quantidade de litros de combustível necessária
     */
    private static double calculateLitersNeeded(int distanceInKm, double fuelEfficiencyKmPerLiter) {
        return (double) distanceInKm / fuelEfficiencyKmPerLiter;
    }

    /**
     * Calcula o custo financeiro total gasto com combustível durante a viagem.
     * Fórmula: litersNeeded * pricePerLiter
     *
     * @param litersNeeded a quantidade calculada de litros consumida
     * @param pricePerLiter o preço unitário do combustível por litro
     * @return o custo total em combustível
     */
    private static double calculateTotalCost(double litersNeeded, double pricePerLiter) {
        return litersNeeded * pricePerLiter;
    }

    /**
     * Calcula o custo financeiro médio por quilômetro percorrido.
     * Fórmula: totalCost / distanceInKm
     *
     * @param totalCost o custo total gasto com combustível
     * @param distanceInKm a distância total percorrida em quilômetros
     * @return o custo financeiro por quilômetro rodado
     */
    private static double calculateCostPerKm(double totalCost, int distanceInKm) {
        return totalCost / (double) distanceInKm;
    }

    private static void validateInputs(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter) {
        if (distanceInKm <= 0) {
            throw new IllegalArgumentException(ERROR_DISTANCE);
        }
        if (fuelEfficiencyKmPerLiter <= 0.0) {
            throw new IllegalArgumentException(ERROR_EFFICIENCY);
        }
        if (pricePerLiter <= 0.0) {
            throw new IllegalArgumentException(ERROR_PRICE);
        }
    }
}
