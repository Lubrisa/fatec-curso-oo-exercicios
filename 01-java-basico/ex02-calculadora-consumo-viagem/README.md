# ex02 — Calculadora de Consumo de Viagem

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Tipos Primitivos (`int`, `double`), Casting Explícito, Validação de Argumentos e Composição de Chamadas  

## 1. Contexto & Cenário

Você foi encarregado de construir o núcleo de cálculo para um aplicativo de logística e reembolso corporativo de viagens. Toda vez que um colaborador realiza uma viagem a trabalho com seu veículo particular, o sistema precisa apurar com exatidão a quantidade de combustível consumida, o gasto financeiro total e o custo rateado por quilômetro rodado.

A classe utilitária `TripCostCalculator` organiza essa lógica em pequenos métodos especializados e gera um relatório consolidado.

## 2. Objetivos de Aprendizagem

- Praticar a interoperabilidade entre tipos inteiros (`int`) e números de ponto flutuante (`double`).
- Aplicar coerção explícita de tipos (*casting*) e entender a promoção em divisões aritméticas: `(double) distanceInKm`.
- Validar argumentos de entrada e proteger os métodos com *fail-fast* lançando `IllegalArgumentException`.
- Encadeamento de chamadas: passar o resultado retornado por um cálculo como parâmetro para o cálculo seguinte.
- Compor o relatório consolidado final passando os valores apurados para a estrutura já preparada.

## 3. Especificação dos Métodos

Na classe `TripCostCalculator`, você deve implementar os seguintes métodos:

### 3.1. Litros Necessários

```java
private static double calculateLitersNeeded(int distanceInKm, double fuelEfficiencyKmPerLiter)
```

- **Fórmula:**

  $$\text{litersNeeded} = \frac{\text{distanceInKm}}{\text{fuelEfficiencyKmPerLiter}}$$

- Calcula a quantidade de litros necessária para a viagem.
- Lembre-se de converter a distância inteira para `double` na operação.

### 3.2. Custo Total com Combustível

```java
private static double calculateTotalCost(double litersNeeded, double pricePerLiter)
```

- **Fórmula:**

  $$\text{totalCost} = \text{litersNeeded} \times \text{pricePerLiter}$$

- Recebe diretamente o total de litros apurado no passo anterior e multiplica pelo preço unitário do combustível.

### 3.3. Custo por Quilômetro

```java
private static double calculateCostPerKm(double totalCost, int distanceInKm)
```

- **Fórmula:**

  $$\text{costPerKm} = \frac{\text{totalCost}}{\text{distanceInKm}}$$

- Recebe o custo total calculado e divide pela distância percorrida, apurando o valor gasto para cada quilômetro rodado.

### 3.4. Resumo Consolidado (`calculateSummary`)

```java
public static TripSummary calculateSummary(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter)
```

Este é o método público que orquestra todo o processo:

1. **Validação de Argumentos (*Fail-Fast*):**
   - Se `distanceInKm <= 0`, lance `IllegalArgumentException("A distância deve ser maior que zero")`.
   - Se `fuelEfficiencyKmPerLiter <= 0.0`, lance `IllegalArgumentException("A eficiência de combustível deve ser maior que zero")`.
   - Se `pricePerLiter <= 0.0`, lance `IllegalArgumentException("O preço do combustível deve ser maior que zero")`.

2. **Encadear os Cálculos:**
   - Chame `calculateLitersNeeded(distanceInKm, fuelEfficiencyKmPerLiter)` e guarde em uma variável `double litersNeeded`.
   - Chame `calculateTotalCost(litersNeeded, pricePerLiter)` e guarde em uma variável `double totalCost`.
   - Chame `calculateCostPerKm(totalCost, distanceInKm)` e guarde em uma variável `double costPerKm`.

3. **Retorno do Relatório:**
   - O código já deixa o retorno do registro `new TripSummary(...)` engatilhado no final do método. Basta descomentar a linha e fornecer as variáveis calculadas na ordem correta:
     ```java
     return new TripSummary(distanceInKm, litersNeeded, totalCost, costPerKm);
     ```

## 4. O que Você Deve Fazer

1. Abra o arquivo `src/main/java/br/com/fatec/basic/ex02/TripCostCalculator.java`.
2. Complete a implementação dos métodos auxiliares de cálculo (`calculateLitersNeeded`, `calculateTotalCost`, `calculateCostPerKm`).
3. Complete o método `calculateSummary`, incluindo as validações e compondo as chamadas.
4. Execute os testes automatizados para verificar sua solução.

## 5. Critérios de Aceite

- Todos os testes da classe `TripCostCalculatorTest` devem passar com sucesso.
- As validações de parâmetros devem lançar `IllegalArgumentException` com as mensagens exatas especificadas.
- Os cálculos com casas decimais devem ser precisos.
