# ex05 — Tarifador Progressivo de Energia

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica Matemática  
> **Dificuldade:** ⭐⭐☆ (Intermediário Iniciante)  
> **Conceitos:** Faturamento por Faixas Cumulativas (Fatiamento), Estruturas Condicionais (`if` / `else if` / `else`), Condições Não Redundantes e Composição de Registros com `record`  

## 1. Contexto & Cenário

Concessionárias de energia elétrica e serviços de utilidade pública frequentemente adotam modelos de **tarifação progressiva**. Em vez de aplicar uma alíquota única sobre todo o volume consumido, o consumo é dividido em fatias (*tiers* ou faixas cumulativas), tarifando o volume básico a um preço mais acessível e encarecendo apenas as fatias que excederem os patamares estipulados.

Um dos erros mais comuns de programação iniciante nesse tipo de problema é a confusão entre **tarifação por faixa simples** (multiplicar todo o consumo pela tarifa da faixa mais alta atingida) e **tarifação progressiva cumulativa** (onde cada bloco de kWh é tarifado pelo valor correspondente à sua respectiva faixa).

Você foi contratado pela concessionária para implementar a classe utilitária `ProgressiveRateCalculator`, garantindo cálculos justos e estruturados sem condições condicionais redundantes.

## 2. Objetivos de Aprendizagem

- Compreender a mecânica do **cálculo por faixas cumulativas** (fatiamento de grandezas contínuas).
- Escrever estruturas condicionais limpas (`if / else if / else`), evitando repetições e condições redundantes (por exemplo: evitar checar `kwh > 100 && kwh <= 200` quando o primeiro `if (kwh <= 100)` já garantiu que o valor é superior a 100).
- Encapsular o resultado do faturamento em um registro imutável (`record EnergyBill`).
- Aplicar proteção *fail-fast* com `IllegalArgumentException` para entradas com consumo negativo.

## 3. Regras de Faturamento por Faixas

A tarifa de energia elétrica é calculada com base na seguinte tabela progressiva cumulativa:

| Faixa | Intervalo de Consumo (kWh) | Tarifa por kWh | Custo Máximo Acumulado da Faixa |
| :---: | :--- | :---: | :---: |
| **Faixa 1** | Até 100.0 kWh (inclusive) | **R$ 0,50** | R$ 50,00 (100 × 0,50) |
| **Faixa 2** | De 100.01 até 200.0 kWh (inclusive) | **R$ 0,75** | R$ 75,00 (100 × 0,75) |
| **Faixa 3** | Acima de 200.0 kWh | **R$ 1,00** | Proporcional ao excedente |

### 3.1. Taxa Fixa de Iluminação Pública

Além do custo de consumo de energia, toda conta possui uma taxa fixa de **Iluminação Pública** no valor de **R$ 15,00**, devida inclusive para imóveis com consumo zero no mês (desde que o consumo não seja negativo).

$$\text{Valor Total da Fatura} = \text{Custo de Energia} + \text{Taxa de Iluminação Pública}$$

### 3.2. Exemplos Práticos de Cálculo

1. **Consumo de 0.0 kWh:**
   - Custo de energia: $0{,}00$
   - Taxa de iluminação: $15{,}00$
   - Total da fatura: **R$ 15,00**

2. **Consumo de 80.0 kWh (pertence inteiramente à Faixa 1):**
   - Faixa 1: $80 \times 0{,}50 = 40{,}00$
   - Custo de energia: $40{,}00$
   - Total da fatura: $40{,}00 + 15{,}00 =$ **R$ 55,00**

3. **Consumo de 150.0 kWh (atravessa a Faixa 1 e entra na Faixa 2):**
   - Faixa 1 (primeiros 100 kWh): $100 \times 0{,}50 = 50{,}00$
   - Faixa 2 (excedente de 50 kWh): $50 \times 0{,}75 = 37{,}50$
   - Custo de energia: $50{,}00 + 37{,}50 = 87{,}50$
   - Total da fatura: $87{,}50 + 15{,}00 =$ **R$ 102,50**

4. **Consumo de 250.0 kWh (atravessa as Faixas 1 e 2 e entra na Faixa 3):**
   - Faixa 1 (100 kWh): $100 \times 0{,}50 = 50{,}00$
   - Faixa 2 (100 kWh): $100 \times 0{,}75 = 75{,}00$
   - Faixa 3 (excedente de 50 kWh além de 200): $50 \times 1{,}00 = 50{,}00$
   - Custo de energia: $50{,}00 + 75{,}00 + 50{,}00 = 175{,}00$
   - Total da fatura: $175{,}00 + 15{,}00 =$ **R$ 190,00**

## 4. Especificação dos Métodos

### 4.1. `calculateEnergyCost`

```java
public static double calculateEnergyCost(double consumptionKwh)
```

- Calcula e retorna apenas a soma dos valores consumidos nas faixas progressivas.
- Lança `IllegalArgumentException("O consumo não pode ser negativo")` se `consumptionKwh < 0`.

### 4.2. `calculateBill`

```java
public static EnergyBill calculateBill(double consumptionKwh)
```

- Retorna o relatório consolidado `EnergyBill` com os campos:
  - `consumptionKwh`: consumo em kWh fornecido.
  - `energyCost`: valor calculado da energia consumida.
  - `publicLightingFee`: valor constante de R$ 15,00.
  - `totalAmount`: soma do custo de energia com a taxa de iluminação.
- Lança `IllegalArgumentException("O consumo não pode ser negativo")` se `consumptionKwh < 0`.

## 5. O que Você Deve Fazer

1. Abra o arquivo `src/main/java/br/com/fatec/basic/ex05/ProgressiveRateCalculator.java`.
2. Implemente o método `calculateEnergyCost` estruturando as faixas com `if` / `else if` / `else` de maneira limpa e não redundante.
3. Complete o método `calculateBill` compondo o retorno com o `record EnergyBill` já engatilhado.
4. Execute os testes com `./mvnw test -pl :ex05-calculadora-tarifa-progressiva`.

## 6. Critérios de Aceite

- Todos os testes da classe `ProgressiveRateCalculatorTest` devem passar com sucesso.
- Consumos negativos devem ser rejeitados com `IllegalArgumentException`.
- A composição dos custos em todas as faixas (básica, moderada e elevada) deve apresentar precisão centesimal correta.
