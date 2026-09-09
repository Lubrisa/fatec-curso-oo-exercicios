# ex05 — Tarifador Progressivo de Energia

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica Matemática  
> **Dificuldade:** ⭐⭐☆ (Intermediário Iniciante)  
> **Conceitos:** Faturamento por Faixas Cumulativas (Fatiamento), Estruturas Condicionais (`if` / `else if` / `else`), Condições Não Redundantes e Composição de Registros com `record`  

## 1. Contexto & Cenário

Concessionárias de energia elétrica e serviços de utilidade pública frequentemente adotam modelos de **tarifação progressiva cumulativa** (também conhecida como tarifação em cascata ou fatiamento de faixas).

Em sistemas reais, a fatura de luz não funciona aplicando uma taxa única sobre todo o consumo. Se um cliente consome **250 kWh**, a distribuidora não cobra a tarifa da faixa mais cara sobre os 250 kWh inteiros — pois isso penalizaria o usuário de forma injusta e desproporcional. Em vez disso, o consumo é **decomposto em blocos (fatias)**: os primeiros 100 kWh continuam sendo baratos para todos, a fatia intermediária tem um preço moderado, e apenas os quilowatts-hora excedentes pagam o valor mais alto.

Você foi contratado pela concessionária para implementar a classe utilitária `ProgressiveRateCalculator`.

---

## 2. Como Funciona o Fatiamento Cumulativo (A Analogia dos Baldes)

Para compreender a mecânica de cálculo, imagine que o volume total de energia consumido (`C`) é um líquido despejado em uma sequência de **recipientes interligados de capacidades limitadas**:

```text
Consumo Total a Distribuir: 250 kWh
=======================================================================
[ Balde 1: Faixa Básica ]       Capacidade: até 100 kWh
                                Enche 100 kWh  × R$ 0,50/kWh = R$ 50,00
                                (Transborda 150 kWh para o Balde 2)
-----------------------------------------------------------------------
[ Balde 2: Faixa Moderada ]     Capacidade: até mais 100 kWh (de 100 a 200)
                                Enche 100 kWh  × R$ 0,75/kWh = R$ 75,00
                                (Transborda 50 kWh para o Balde 3)
-----------------------------------------------------------------------
[ Balde 3: Faixa Elevada ]      Capacidade: Ilimitada (tudo acima de 200)
                                Recebe os 50 kWh restantes × R$ 1,00/kWh = R$ 50,00
=======================================================================
Custo Total de Energia = R$ 50,00 + R$ 75,00 + R$ 50,00 = R$ 175,00
```

### ⚠️ Onde os Iniciantes Costumam Errar?

- **Erro comum (Tarifação Simples):** O programador olha a tabela, vê que `250 kWh > 200`, e calcula `250 × 1,00 = R$ 250,00`. **Isso está incorreto!**
- **Forma correta (Fatiamento Cumulativo):** O consumidor tem direito à tarifa mais barata nos primeiros 100 kWh (que custam **R$ 50,00**), à tarifa moderada nos próximos 100 kWh (que custam **R$ 75,00**), e **apenas o excedente além de 200 kWh** (`250 - 200 = 50 kWh`) é cobrado a **R$ 1,00**.

---

## 3. Tabela de Tarifas e Fórmulas de Cálculo

| Faixa | Bloco de Consumo Faturado | Tarifa por kWh no Bloco | Custo Máximo que a Faixa Pode Gerar |
| :---: | :--- | :---: | :---: |
| **Faixa 1** | Até os primeiros 100 kWh | **R$ 0,50** | **R$ 50,00** (100 × 0,50) |
| **Faixa 2** | Próximos 100 kWh (trecho de 100 a 200) | **R$ 0,75** | **R$ 75,00** (100 × 0,75) |
| **Faixa 3** | Todo o excedente além de 200 kWh | **R$ 1,00** | Proporcional ao excedente |

### Fórmulas por Faixa de Consumo

Sendo `C` o consumo mensal em kWh:

1. **Caso 1: Consumo até 100 kWh (`C <= 100`):**  
   Todo o consumo cabe no primeiro bloco:
   ```text
   Custo de Energia = C * 0.50
   ```

2. **Caso 2: Consumo entre 100 e 200 kWh (`100 < C <= 200`):**  
   Os primeiros 100 kWh esgotam a Faixa 1 (R$ 50,00), e o **excedente** `(C - 100)` é faturado pela Faixa 2:
   ```text
   Custo de Energia = (100 * 0.50) + ((C - 100) * 0.75)
   ```

3. **Caso 3: Consumo acima de 200 kWh (`C > 200`):**  
   A Faixa 1 e a Faixa 2 estão totalmente esgotadas (R$ 50,00 + R$ 75,00 = R$ 125,00). Apenas o **excedente** `(C - 200)` é faturado pela Faixa 3:
   ```text
   Custo de Energia = (100 * 0.50) + (100 * 0.75) + ((C - 200) * 1.00)
   ```

---

## 4. Taxa de Iluminação Pública & Fatura Consolidada

Além do custo de energia consumida por faixas, toda conta residencial possui uma taxa fixa de **Iluminação Pública** no valor de **R$ 15,00**, devida inclusive para imóveis com consumo zero no mês:

```text
Valor Total da Fatura = Custo de Energia + R$ 15,00
```

### Exemplos Completos de Fatura

- **Consumo de 0.0 kWh:**  
  Custo de energia: `R$ 0,00` | Iluminação: `R$ 15,00` | **Total: R$ 15,00**
- **Consumo de 80.0 kWh:**  
  Custo de energia: `80 * 0,50 = R$ 40,00` | Iluminação: `R$ 15,00` | **Total: R$ 55,00**
- **Consumo de 150.0 kWh:**  
  Custo de energia: `50,00 + (50 * 0,75) = R$ 87,50` | Iluminação: `R$ 15,00` | **Total: R$ 102,50**
- **Consumo de 250.0 kWh:**  
  Custo de energia: `50,00 + 75,00 + (50 * 1,00) = R$ 175,00` | Iluminação: `R$ 15,00` | **Total: R$ 190,00**

---

## 5. Especificação dos Métodos

### 5.1. `calculateEnergyCost`

```java
public static double calculateEnergyCost(double consumptionKwh)
```

- Aplica as fórmulas de fatiamento cumulativo descritas acima.
- Lança `IllegalArgumentException("O consumo não pode ser negativo")` se `consumptionKwh < 0`.

### 5.2. `calculateBill`

```java
public static EnergyBill calculateBill(double consumptionKwh)
```

- Retorna o registro imutável `EnergyBill` contendo:
  - `consumptionKwh`: consumo faturado.
  - `energyCost`: custo calculado pelo fatiamento.
  - `publicLightingFee`: taxa constante de R$ 15,00.
  - `totalAmount`: soma de `energyCost` e `publicLightingFee`.
- Se `consumptionKwh < 0`, lança `IllegalArgumentException("O consumo não pode ser negativo")`.

---

## 6. O que Você Deve Fazer

1. Abra o arquivo `src/main/java/br/com/fatec/basic/ex05/ProgressiveRateCalculator.java`.
2. Implemente o método `calculateEnergyCost` estruturando as faixas com `if` / `else if` / `else` de maneira limpa (sem condições redundantes).
3. Complete o método `calculateBill` compondo o retorno com o `record EnergyBill` já fornecido.
4. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex05-calculadora-tarifa-progressiva
   ```

## 7. Critérios de Aceite

- Todos os testes da classe `ProgressiveRateCalculatorTest` devem passar com sucesso.
- Consumos negativos devem ser rejeitados com `IllegalArgumentException`.
- A composição dos custos em todas as faixas e o cálculo de excedente devem apresentar precisão centesimal correta.
