package br.com.fatec.basic.ex02;

/**
 * Classe utilitária para cálculo de consumo e custos de viagens rodoviárias.
 */
public final class TripCostCalculator {

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
        // TODO: 1. Valide os argumentos de entrada (lance IllegalArgumentException se algum valor for <= 0):
        //       - "A distância deve ser maior que zero"
        //       - "A eficiência de combustível deve ser maior que zero"
        //       - "O preço do combustível deve ser maior que zero"

        // TODO: 2. Calcule os valores encadeando as chamadas aos métodos abaixo:
        // double litersNeeded = calculateLitersNeeded(...);
        // double totalCost = calculateTotalCost(...);
        // double costPerKm = calculateCostPerKm(...);

        // TODO: 3. O retorno abaixo já está engatilhado para você, apenas descomente e passe as variáveis:
        // return new TripSummary(distanceInKm, litersNeeded, totalCost, costPerKm);
        throw new UnsupportedOperationException("Método calculateSummary ainda não implementado");
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
        // TODO: Implementar o cálculo da litragem necessária (dica: converta distanceInKm para double)
        throw new UnsupportedOperationException("Método calculateLitersNeeded ainda não implementado");
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
        // TODO: Implementar o cálculo do custo total com combustível
        throw new UnsupportedOperationException("Método calculateTotalCost ainda não implementado");
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
        // TODO: Implementar o cálculo do custo por quilômetro (dica: converta distanceInKm para double)
        throw new UnsupportedOperationException("Método calculateCostPerKm ainda não implementado");
    }
}
