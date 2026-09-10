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

### 3.1. A Mecânica do Fatiamento (A Barra de Progresso do Consumo)

Para compreender como as faixas se comportam, imagine o consumo total de energia como uma barra de progresso horizontal que avança ao longo de marcos de corte:

#### 1. A Régua Base (Capacidades e Tarifas de Cada Segmento)

```text
[ 0.0 kWh ]──────────────[ 100.0 kWh ]──────────────[ 200.0 kWh ]──────────────► (sem teto)
     │                          │                          │
     └─── Faixa 1 (máx 100) ────┴─── Faixa 2 (máx 100) ────┴─── Faixa 3 (excedente) ───►
             R$ 0,50 / kWh              R$ 0,75 / kWh              R$ 1,00 / kWh
```

---

#### 2. Exemplo Parcial: Consumo de 150.0 kWh

A barra preenche completamente os primeiros 100 kWh da Faixa 1 e avança parcialmente até a metade da Faixa 2:

```text
[ 0 kWh ]════════════════[ 100 kWh ]═════════[ 150 kWh ] - - - - [ 200 kWh ] - - - - - - - -►
    │                         │                   │
    └──── Faixa 1 (Cheia) ────┴─ Faixa 2 (Parcial)┴── Faixa 2 (Vazia) ──┴── Faixa 3 (Vazia) ─►
          100 kWh × R$ 0,50        50 kWh × R$ 0,75
            = R$ 50,00               = R$ 37,50

Custo de Energia = R$ 50,00 + R$ 37,50 = R$ 87,50
```

---

#### 3. Exemplo com Excedente: Consumo de 250.0 kWh

A barra preenche integralmente as Faixas 1 e 2 (totalizando 200 kWh) e **transborda 50 kWh** para a Faixa 3:

```text
[ 0 kWh ]════════════════[ 100 kWh ]════════════════[ 200 kWh ]═════════[ 250 kWh ] - - - - -►
    │                         │                         │                   │
    └──── Faixa 1 (Cheia) ────┴──── Faixa 2 (Cheia) ────┴─ Faixa 3 (Exced.) ┴─ Faixa 3 Livre ─►
          100 kWh × R$ 0,50         100 kWh × R$ 0,75        50 kWh × R$ 1,00
            = R$ 50,00                = R$ 75,00               = R$ 50,00

Custo de Energia = R$ 50,00 + R$ 75,00 + R$ 50,00 = R$ 175,00
```

> **Atenção (Armadilha Clássica):**  
> Nunca multiplique o consumo total pela tarifa mais alta (`250 × 1,00 = R$ 250,00`).  
> O consumidor tem direito à tarifa menor nos blocos iniciais; apenas o excedente além de cada patamar é faturado pela nova alíquota.

---

### 3.2. Demonstrativo Detalhado de Faturamento (Simulação de 250 kWh)

O faturamento final equivale ao extrato detalhado impresso no verso de uma conta de energia elétrica:

| Parcela / Bloco Faturado | Volume Efetivo no Bloco | Tarifa Unitária | Subtotal da Parcela |
| :--- | :---: | :---: | :---: |
| **Parcela Faixa 1** (primeiros 100 kWh) | 100.0 kWh | R$ 0,50 / kWh | R$ 50,00 |
| **Parcela Faixa 2** (consumo entre 100 e 200 kWh) | 100.0 kWh | R$ 0,75 / kWh | R$ 75,00 |
| **Parcela Faixa 3** (excedente acima de 200 kWh) | 50.0 kWh | R$ 1,00 / kWh | R$ 50,00 |
| **Subtotal Consumo de Energia** | **250.0 kWh** | — | **R$ 175,00** |
| **Taxa Fixa de Iluminação Pública** | — | — | R$ 15,00 |
| **TOTAL A PAGAR NA FATURA** | — | — | **R$ 190,00** |

---

### 3.3. Tabela de Tarifas & Fórmulas Matemáticas

| Faixa | Intervalo de Consumo no Bloco | Tarifa por kWh | Custo Máximo Acumulado no Bloco |
| :---: | :--- | :---: | :---: |
| **Faixa 1** | Primeiros 100.0 kWh | **R$ 0,50** | **R$ 50,00** (100 × 0,50) |
| **Faixa 2** | Próximos 100.0 kWh (de 100.0 a 200.0) | **R$ 0,75** | **R$ 75,00** (100 × 0,75) |
| **Faixa 3** | Excedente além de 200.0 kWh | **R$ 1,00** | Proporcional ao excedente |

Fórmulas matemáticas para o consumo `C`:
- **Se `C <= 100.0`:**  
  `Custo = C * 0.50`
- **Se `100.0 < C <= 200.0`:**  
  `Custo = (100.0 * 0.50) + ((C - 100.0) * 0.75)`
- **Se `C > 200.0`:**  
  `Custo = (100.0 * 0.50) + (100.0 * 0.75) + ((C - 200.0) * 1.00)`

---

### 3.4. Taxa Fixa de Iluminação Pública & Exemplos Consolidados

Toda fatura residencial inclui uma taxa fixa de **Iluminação Pública** no valor de **R$ 15,00**, devida inclusive para imóveis com consumo zero no ciclo:

```text
Valor Total da Fatura = Custo de Energia + R$ 15,00
```

1. **Consumo de 0.0 kWh:** Custo de Energia: R$ 0,00 | Iluminação: R$ 15,00 | **Total: R$ 15,00**
2. **Consumo de 80.0 kWh:** Custo de Energia: `80 * 0,50 = R$ 40,00` | Iluminação: R$ 15,00 | **Total: R$ 55,00**
3. **Consumo de 150.0 kWh:** Custo de Energia: `50,00 + (50 * 0,75) = R$ 87,50` | Iluminação: R$ 15,00 | **Total: R$ 102,50**
4. **Consumo de 250.0 kWh:** Custo de Energia: `50,00 + 75,00 + (50 * 1,00) = R$ 175,00` | Iluminação: R$ 15,00 | **Total: R$ 190,00**

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex05` na classe `ProgressiveRateCalculator`:

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

---

### 4.3. Estrutura de Retorno Fornecida (`EnergyBill`)

> ℹ️ **Estrutura Pré-Pronta:** O arquivo `EnergyBill.java` já vem implementado e **não precisa ser modificado**. Ele funciona como um agrupador imutável de dados que reúne o extrato completo da fatura:

```java
public record EnergyBill(
        double consumptionKwh,
        double energyCost,
        double publicLightingFee,
        double totalAmount
) {}
```

---

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex05/ProgressiveRateCalculator.java`.  
   *(O arquivo `EnergyBill.java` já está pronto e não precisa de nenhuma modificação).*
2. Implemente o método `calculateEnergyCost` estruturando as faixas com `if` / `else if` / `else` de maneira limpa (sem condições redundantes).
3. Complete o método `calculateBill` compondo o retorno com o `record EnergyBill` já fornecido (`new EnergyBill(...)`).
4. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex05-calculadora-tarifa-progressiva
   ```

---

## 6. Critérios de Aceite

- Todos os testes da classe `ProgressiveRateCalculatorTest` devem passar com sucesso.
- Consumos negativos devem lançar `IllegalArgumentException` com a mensagem `"O consumo não pode ser negativo"`.
- A composição dos custos em todas as faixas e o cálculo de excedente devem apresentar precisão centesimal correta.
