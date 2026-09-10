# ex02 — Calculadora de Consumo de Viagem

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica Matemática  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Tipos Primitivos (`int`, `double`), Casting Explícito, Validação de Argumentos e Composição de Chamadas  

## 1. Contexto & Cenário

Você foi encarregado de construir o núcleo de cálculo para um aplicativo de logística e reembolso corporativo de viagens. Toda vez que um colaborador realiza uma viagem a trabalho com seu veículo particular, o sistema precisa apurar com exatidão a quantidade de combustível consumida, o gasto financeiro total e o custo rateado por quilômetro rodado.

A classe utilitária `TripCostCalculator` organiza essa lógica em pequenos métodos especializados e gera um relatório consolidado através de um `record TripSummary`.

## 2. Objetivos de Aprendizagem

- Praticar a interoperabilidade entre tipos inteiros (`int`) e números de ponto flutuante (`double`).
- Aplicar coerção explícita de tipos (*casting*) e entender a promoção em divisões aritméticas: `(double) distanceInKm`.
- Validar argumentos de entrada e proteger os métodos com *fail-fast* lançando `IllegalArgumentException`.
- Encadeamento de chamadas: passar o resultado retornado por um cálculo como parâmetro para o cálculo seguinte.
- Compor o relatório consolidado final passando os valores apurados para a estrutura já preparada.

## 3. Regras de Negócio & Fórmulas de Cálculo

Os cálculos de faturamento de viagem são decompostos em três etapas sucessivas:

### 3.1. Litros de Combustível Necessários

Calcula a quantidade de litros consumida ao longo do trajeto:
```text
litersNeeded = distanceInKm / fuelEfficiencyKmPerLiter
```
*Observação:* A distância é informada como número inteiro (`int`). Lembre-se de convertê-la para `double` na operação para evitar divisão inteira truncada.

### 3.2. Custo Financeiro Total com Combustível

Calcula o gasto total em reais multiplicando os litros necessários pelo preço unitário:
```text
totalCost = litersNeeded * pricePerLiter
```

### 3.3. Custo Médio por Quilômetro Rodado

Rateia o gasto financeiro total pela quilometragem percorrida:
```text
costPerKm = totalCost / distanceInKm
```

### 3.4. Regras de Validação (Fail-Fast)

Todas as grandezas de entrada devem ser estritamente positivas:
- Se `distanceInKm <= 0`: lançar `IllegalArgumentException("A distância deve ser maior que zero")`.
- Se `fuelEfficiencyKmPerLiter <= 0.0`: lançar `IllegalArgumentException("A eficiência de combustível deve ser maior que zero")`.
- Se `pricePerLiter <= 0.0`: lançar `IllegalArgumentException("O preço do combustível deve ser maior que zero")`.

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex02` na classe `TripCostCalculator`:

### 4.1. Métodos Auxiliares Privados

```java
private static double calculateLitersNeeded(int distanceInKm, double fuelEfficiencyKmPerLiter)
private static double calculateTotalCost(double litersNeeded, double pricePerLiter)
private static double calculateCostPerKm(double totalCost, int distanceInKm)
```

### 4.2. Método Público Orquestrador (`calculateSummary`)

```java
public static TripSummary calculateSummary(int distanceInKm, double fuelEfficiencyKmPerLiter, double pricePerLiter)
```

- Valida os argumentos de entrada lançando `IllegalArgumentException` se algum for menor ou igual a zero.
- Encadeia as chamadas aos métodos auxiliares.
- Retorna uma nova instância da estrutura agregadora:
  ```java
  return new TripSummary(distanceInKm, litersNeeded, totalCost, costPerKm);
  ```

---

### 4.3. Estrutura de Retorno Fornecida (`TripSummary`)

> ℹ️ **Estrutura Pré-Pronta:** O arquivo `TripSummary.java` já vem implementado e **não precisa ser modificado**. Ele funciona como um agrupador imutável de dados que consolida as 4 métricas calculadas:

```java
public record TripSummary(
        int distanceInKm,
        double litersNeeded,
        double totalCost,
        double costPerKm
) {}
```

---

## 5. O que Você Deve Fazer

1. Abra o arquivo `src/main/java/br/com/fatec/basic/ex02/TripCostCalculator.java`.  
   *(O arquivo `TripSummary.java` já está pronto e não precisa de nenhuma modificação).*
2. Complete a implementação dos métodos auxiliares de cálculo (`calculateLitersNeeded`, `calculateTotalCost`, `calculateCostPerKm`).
3. Complete o método `calculateSummary`, incluindo as validações e compondo as chamadas para instanciar `new TripSummary(...)`.
4. Execute os testes automatizados para verificar sua solução:
   ```bash
   ./mvnw test -pl :ex02-calculadora-consumo-viagem
   ```

---

## 6. Critérios de Aceite

- Todos os testes da classe `TripCostCalculatorTest` devem passar com sucesso.
- As validações de parâmetros devem lançar `IllegalArgumentException` com as mensagens exatas especificadas.
- Os cálculos com casas decimais devem apresentar precisão adequada.
