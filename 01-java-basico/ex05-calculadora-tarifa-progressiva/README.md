# ex05 — Tarifador Progressivo de Energia

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica Matemática  
> **Dificuldade:** ⭐⭐☆ (Intermediário Iniciante)  
> **Conceitos:** Faturamento por Faixas Cumulativas (Fatiamento), Estruturas Condicionais (`if` / `else if` / `else`), Condições Não Redundantes e Composição com `record`  

## 1. Contexto & Cenário

Concessionárias de energia elétrica e serviços de utilidade pública frequentemente adotam modelos de **tarifação progressiva cumulativa** (também conhecida como tarifação em cascata ou fatiamento de faixas).

Em sistemas reais, a fatura de luz não funciona aplicando uma taxa única sobre todo o consumo. Se um cliente consome 250 kWh, a distribuidora não cobra a tarifa da faixa mais cara sobre os 250 kWh inteiros — pois isso penalizaria o usuário de forma desproporcional. Em vez disso, o consumo é **decomposto em blocos (fatias)**: os primeiros 100 kWh continuam sendo baratos para todos, a fatia intermediária tem um preço moderado, e apenas os quilowatts-hora excedentes pagam o valor mais alto.

Você foi contratado pela concessionária para implementar a classe utilitária `ProgressiveRateCalculator`.

## 2. Objetivos de Aprendizagem

- Compreender a mecânica do **cálculo por faixas cumulativas** (fatiamento de grandezas contínuas por excedente).
- Escrever estruturas condicionais limpas (`if / else if / else`), evitando repetições e condições redundantes (por exemplo: evitar checar `kwh > 100 && kwh <= 200` quando o primeiro `if (kwh <= 100)` já garantiu que o valor é superior a 100).
- Encapsular o resultado do faturamento em um registro imutável (`record EnergyBill`).
- Aplicar proteção *fail-fast* com `IllegalArgumentException` para entradas com consumo negativo.

## 3. Regras de Negócio & Mecânica de Faturamento

### 3.1. A Mecânica do Fatiamento por Excedente (A Analogia dos Baldes)

Para compreender como as faixas se comportam, imagine que o consumo total em kWh é uma quantidade de água despejada em baldes interligados com capacidades limitadas:

```text
Consumo Total: 250 kWh
=======================================================================
[ Balde 1: Faixa Básica ]       Capacidade: até 100 kWh
                                Enche 100 kWh × R$ 0,50/kWh = R$ 50,00
                                (Transborda 150 kWh para o Balde 2)
-----------------------------------------------------------------------
[ Balde 2: Faixa Moderada ]     Capacidade: até mais 100 kWh (de 100 a 200)
                                Enche 100 kWh × R$ 0,75/kWh = R$ 75,00
                                (Transborda 50 kWh para o Balde 3)
-----------------------------------------------------------------------
[ Balde 3: Faixa Elevada ]      Capacidade: Ilimitada (tudo acima de 200)
                                Recebe os 50 kWh restantes × R$ 1,00/kWh = R$ 50,00
=======================================================================
Custo Total de Energia = R$ 50,00 + R$ 75,00 + R$ 50,00 = R$ 175,00
```

> **Atenção (Armadilha de Iniciante):**  
> Não multiplique todo o consumo pela tarifa mais alta (`250 × 1,00 = R$ 250,00`).  
> Apenas a parcela que **excede o limite inferior de cada faixa** deve ser tarifada com o novo valor.

### 3.2. Tabela de Tarifas por Faixa

| Faixa | Bloco de Consumo Faturado | Tarifa por kWh | Custo Máximo do Bloco |
| :---: | :--- | :---: | :---: |
| **Faixa 1** | Primeiros 100 kWh (até 100.0) | **R$ 0,50** | **R$ 50,00** (100 × 0,50) |
| **Faixa 2** | Próximos 100 kWh (de 100.0 a 200.0) | **R$ 0,75** | **R$ 75,00** (100 × 0,75) |
| **Faixa 3** | Excedente além de 200.0 kWh | **R$ 1,00** | Proporcional ao excedente |

Fórmulas matemáticas para o consumo `C`:
- **Se `C <= 100`:** `Custo = C * 0.50`
- **Se `100 < C <= 200`:** `Custo = (100 * 0.50) + ((C - 100) * 0.75)`
- **Se `C > 200`:** `Custo = (100 * 0.50) + (100 * 0.75) + ((C - 200) * 1.00)`

### 3.3. Taxa Fixa de Iluminação Pública

Além do custo de consumo de energia, toda conta residencial possui uma taxa fixa de **Iluminação Pública** no valor de **R$ 15,00**, devida inclusive para imóveis com consumo zero no mês (desde que o consumo não seja negativo):

```text
Valor Total da Fatura = Custo de Energia + R$ 15,00
```

### 3.4. Exemplos de Faturas Consolidadas

1. **0.0 kWh:** Custo Energia: R$ 0,00 | Iluminação: R$ 15,00 | **Total: R$ 15,00**
2. **80.0 kWh:** Custo Energia: `80 * 0,50 = R$ 40,00` | Iluminação: R$ 15,00 | **Total: R$ 55,00**
3. **150.0 kWh:** Custo Energia: `50,00 + (50 * 0,75) = R$ 87,50` | Iluminação: R$ 15,00 | **Total: R$ 102,50**
4. **250.0 kWh:** Custo Energia: `50,00 + 75,00 + (50 * 1,00) = R$ 175,00` | Iluminação: R$ 15,00 | **Total: R$ 190,00**

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex05`:

### 4.1. `calculateEnergyCost`

```java
public static double calculateEnergyCost(double consumptionKwh)
```

- Aplica as fórmulas de fatiamento cumulativo por faixas.
- Lança `IllegalArgumentException("O consumo não pode ser negativo")` se `consumptionKwh < 0`.

### 4.2. `calculateBill`

```java
public static EnergyBill calculateBill(double consumptionKwh)
```

- Calcula o custo de energia e soma a taxa fixa de iluminação pública (`R$ 15,00`).
- Retorna o registro imutável `EnergyBill` com `consumptionKwh`, `energyCost`, `publicLightingFee` e `totalAmount`.
- Lança `IllegalArgumentException("O consumo não pode ser negativo")` se `consumptionKwh < 0`.

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex05/ProgressiveRateCalculator.java`.
2. Implemente o método `calculateEnergyCost` estruturando as faixas com `if` / `else if` / `else` de maneira limpa (sem condições redundantes).
3. Complete o método `calculateBill` compondo o retorno com o `record EnergyBill` já fornecido.
4. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex05-calculadora-tarifa-progressiva
   ```

## 6. Critérios de Aceite

- Todos os testes da classe `ProgressiveRateCalculatorTest` devem passar com sucesso.
- Consumos negativos devem lançar `IllegalArgumentException` com a mensagem `"O consumo não pode ser negativo"`.
- A composição dos custos em todas as faixas e o cálculo de excedente devem apresentar precisão centesimal correta.
