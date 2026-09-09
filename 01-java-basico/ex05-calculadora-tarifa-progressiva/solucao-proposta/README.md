# Solução Proposta — ex05: Tarifador Progressivo de Energia

Este diretório contém a implementação de referência do professor e a análise técnica do faturamento por faixas cumulativas e boas práticas com estruturas condicionais.

## 1. Código da Solução

Os arquivos resolvidos estão em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex05/EnergyBill.java`
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex05/ProgressiveRateCalculator.java`

## 2. Análise Técnica & Decisões Didáticas

### 2.1. O Princípio da Condição Não Redundante

Ao escrever ramificações com `if` / `else if` / `else`, programadores iniciantes frequentemente incorrem no vício de checar limites inferiores já implicitamente garantidos:

```java
// ❌ CÓDIGO COM CONDIÇÕES REDUNDANTES E POLUÍDAS:
if (kwh <= 100) {
    ...
} else if (kwh > 100 && kwh <= 200) { // O teste "kwh > 100" é 100% redundante!
    ...
} else if (kwh > 200) {               // O teste "kwh > 200" também é 100% redundante!
    ...
}
```

**Por que isso é um anti-pattern?**
Se o fluxo de execução atingiu a cláusula `else if`, a primeira condição (`kwh <= 100`) **já foi avaliada como falsa**. Logo, é logicamente garantido que `kwh > 100`.

A forma idiomática e limpa é:

```java
// ✅ CÓDIGO LIMPO E IDIOMÁTICO:
if (kwh <= TIER1_LIMIT) {
    return kwh * TIER1_RATE;
} else if (kwh <= TIER2_LIMIT) {
    return (TIER1_LIMIT * TIER1_RATE) + ((kwh - TIER1_LIMIT) * TIER2_RATE);
} else {
    return (TIER1_LIMIT * TIER1_RATE)
            + ((TIER2_LIMIT - TIER1_LIMIT) * TIER2_RATE)
            + ((kwh - TIER2_LIMIT) * TIER3_RATE);
}
```

### 2.2. Tarifação Cumulativa (Fatiamento) vs Tarifação Simples

- **Tarifação Simples (Incorreta neste contexto):** Se o cliente gasta 101 kWh, pagar a tarifa mais cara sobre todos os 101 kWh criaria uma distorção financeira absurda (consumir 1 kWh a mais geraria um salto desproporcional na conta).
- **Tarifação Cumulativa (Correta):** Cada fatia do consumo é isolada e precificada de acordo com seu intervalo. Os primeiros 100 kWh continuam sendo cobrados a R$ 0,50, independentemente de o cliente ter consumido 100 ou 10.000 kWh naquele mês. Apenas o excedente sobe de categoria.

### 2.3. Composição de Métodos Sem Redundância

O método `calculateBill` não precisa duplicar a validação `consumptionKwh < 0`. Ao chamar `calculateEnergyCost(consumptionKwh)`, qualquer inconsistência causará o lançamento imediato da exceção antes da criação do registro `EnergyBill`.
