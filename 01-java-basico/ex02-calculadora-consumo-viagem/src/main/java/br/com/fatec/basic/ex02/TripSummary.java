package br.com.fatec.basic.ex02;

/**
 * Registro imutável que consolida o resumo financeiro e operacional de uma viagem.
 *
 * @param distanceInKm a distância total percorrida em quilômetros
 * @param litersNeeded a quantidade calculada de litros de combustível consumidos
 * @param totalCost o custo financeiro total gasto com combustível
 * @param costPerKm o custo financeiro médio por cada quilômetro rodado
 */
public record TripSummary(
        int distanceInKm,
        double litersNeeded,
        double totalCost,
        double costPerKm
) {}
