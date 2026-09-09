# ex02 — Calculadora de Consumo de Viagem

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Tipos Primitivos (`int`, `double`), Casting Explícito, Validação de Argumentos e Agregação de Resultados (`record`)  

## 1. Contexto & Cenário

Você foi encarregado de construir o núcleo de cálculo para um aplicativo de logística e reembolso corporativo de viagens. Toda vez que um colaborador realiza uma viagem a trabalho com seu veículo particular, o sistema precisa apurar com exatidão a quantidade de combustível consumida, o gasto financeiro total e o custo rateado por quilômetro rodado.

Seu trabalho é implementar a classe utilitária `TripCostCalculator` e o modelo de dados imutável `TripSummary`.

## 2. Objetivos de Aprendizagem

- Praticar a interoperabilidade entre tipos inteiros (`int`) e números de ponto flutuante (`double`).
- Aplicar coerção explícita de tipos (*casting*) e entender a promoção automática em expressões aritméticas.
- Validar argumentos de entrada e proteger os métodos contra valores nulos, negativos ou zerados através de *fail-fast* com `IllegalArgumentException`.
- Compor resultados calculados em um registro imutável do Java (`record`), reaproveitando métodos atômicos.

## 3. Especificação Funcional & Fórmulas

A classe utilitária `TripCostCalculator` deve fornecer quatro operações públicas:

### 3.1. Litros Necessários

Fórmula:

$$\text{litersNeeded} = \frac{\text{distanceInKm}}{\text{fuelEfficiencyKmPerLiter}}$$

- Calcula a quantidade de litros necessária para percorrer a distância informada.
- A distância é informada como um número inteiro de quilômetros (`int`), enquanto o consumo médio é informado em km/l (`double`).
- Validação: se `distanceInKm <= 0`, deve lançar `IllegalArgumentException("A distância deve ser maior que zero")`.
- Validação: se `fuelEfficiencyKmPerLiter <= 0.0`, deve lançar `IllegalArgumentException("A eficiência de combustível deve ser maior que zero")`.

### 3.2. Custo Total com Combustível

Fórmula:

$$\text{totalCost} = \text{litersNeeded} \times \text{pricePerLiter}$$

- Multiplica os litros necessários pelo preço unitário do combustível.
- Validação: se `pricePerLiter <= 0.0`, deve lançar `IllegalArgumentException("O preço do combustível deve ser maior que zero")`.

### 3.3. Custo por Quilômetro

Fórmula:

$$\text{costPerKm} = \frac{\text{totalCost}}{\text{distanceInKm}}$$

- Determina o custo médio rateado para cada quilômetro rodado.
- Reutiliza os cálculos anteriores e aplica as mesmas validações de parâmetros.

### 3.4. Resumo Consolidado da Viagem

Assinatura:

```java
public static TripSummary calculateSummary(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter);
```

- Executa todas as validações e retorna uma instância do `record TripSummary` contendo todos os indicadores apurados:

```java
public record TripSummary(
    int distanceInKm,
    double litersNeeded,
    double totalCost,
    double costPerKm
) {}
```

## 4. Regras de Validação & Pontos de Atenção

1. **Promoção de Tipos e Casting:**

   - O parâmetro `distanceInKm` é do tipo `int`. Embora em operações mistas como `distanceInKm / fuelEfficiencyKmPerLiter` o Java promova o inteiro para `double` automaticamente, é uma excelente prática pedagógica entender e explicitar a conversão: `(double) distanceInKm`.

2. **Reuso de Métodos:**

   - O método composto `calculateSummary` não deve duplicar fórmulas matemáticas. Ele deve orquestrar e chamar os métodos atômicos `calculateLitersNeeded`, `calculateTotalCost` e `calculateCostPerKm`.

3. **Validação Fail-Fast:**

   - Todo parâmetro deve ser validado logo na primeira linha do método. Valores menores ou iguais a zero são inconsistentes no mundo real e devem ser rejeitados imediatamente.

## 5. O que Você Deve Fazer

1. Abra os arquivos no diretório `src/main/java/br/com/fatec/basic/ex02/`:
   - `TripSummary.java` (o modelo `record`)
   - `TripCostCalculator.java` (a classe utilitária com a lógica de negócio)
2. Implemente as validações e fórmulas matemáticas solicitadas.
3. Garanta que a documentação Javadoc de todos os métodos e componentes esteja completa e clara em português.
4. Execute os testes automatizados para validar sua implementação.

## 6. Critérios de Aceite

- Todos os testes da classe `TripCostCalculatorTest` devem passar com sucesso.
- As validações de parâmetros devem lançar `IllegalArgumentException` com as mensagens exatas especificadas.
- As asserções de ponto flutuante utilizam margem de precisão (`Offset.offset(0.001)`).
