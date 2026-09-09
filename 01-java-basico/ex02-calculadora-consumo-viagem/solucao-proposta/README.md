# Solução Proposta — ex02: Calculadora de Consumo de Viagem

Este diretório contém a resolução de referência do professor e a análise técnica dos conceitos trabalhados.

## 1. Código da Solução

Os arquivos resolvidos estão em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex02/TripSummary.java`
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex02/TripCostCalculator.java`

## 2. Decisões Didáticas & Análise Conceitual

### 2.1. Promoção de Tipos e Casting Explícito

No método `calculateLitersNeeded`, temos uma operação entre `int` e `double`:

```java
// O compilador promove 'distanceInKm' para double automaticamente,
// mas explicitar o casting comunica a intenção com clareza cristalina:
return (double) distanceInKm / fuelEfficiencyKmPerLiter;
```

Se a fórmula envolvesse dois inteiros (por exemplo, `int km / int horas`), a ausência do casting descartaria as casas decimais antes de atribuir a um `double`. Praticar o casting desde cedo desenvolve a disciplina mental necessária para lidar com o sistema de tipos do Java.

### 2.2. Reuso e Composição de Métodos

Em vez de repetir as fórmulas matemáticas dentro de `calculateSummary`, o método atua como um coordenador:

```java
public static TripSummary calculateSummary(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter) {
    double liters = calculateLitersNeeded(distanceInKm, fuelEfficiencyKmPerLiter);
    double totalCost = calculateTotalCost(distanceInKm, fuelEfficiencyKmPerLiter, pricePerLiter);
    double costPerKm = calculateCostPerKm(distanceInKm, fuelEfficiencyKmPerLiter, pricePerLiter);

    return new TripSummary(distanceInKm, liters, totalCost, costPerKm);
}
```

Dessa forma:
1. Qualquer ajuste futuro nas regras ou validações reflete automaticamente no resumo.
2. Evita-se duplicação de código (princípio DRY — *Don't Repeat Yourself*).

### 2.3. Modelagem Leve com `record`

A introdução do `record TripSummary` demonstra aos alunos como o Java moderno permite modelar transportadores de dados imutáveis de forma concisa, gerando automaticamente construtor canônico, *accessors*, `equals()`, `hashCode()` e `toString()`.
