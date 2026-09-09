# ex01 — Simulador Financeiro & Amortização

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Algorítmico  
> **Dificuldade:** ⭐⭐☆ (Intermediário)  
> **Conceitos:** Operadores Aritméticos, Classe `Math`, Casting de Tipos, Laços de Repetição, Coleções e Invariantes  

## 1. Contexto & Cenário

Ao construir sistemas bancários e plataformas de empréstimos (fintechs), uma das funcionalidades mais críticas é a simulação e projeção de pagamentos. 

Você foi encarregado de implementar o motor de cálculo financeiro (`FinancialCalculator`) capaz de projetar o rendimento de aplicações com juros compostos e gerar cronogramas de amortização nas duas modalidades mais utilizadas no mercado: o Sistema de Amortização Constante (**SAC**) e a Tabela **Price** (Sistema Francês).

## 2. Objetivos de Aprendizagem

- Utilizar métodos estáticos da classe `java.lang.Math` (como `Math.pow` e `Math.round`).
- Compreender conversões de tipo (casting explícito de `double` para `long`/`int`) e o impacto do arredondamento em centavos.
- Controlar o acúmulo de variáveis em laços de repetição (`for` / `while`).
- Trabalhar com coleções imutáveis (`List.copyOf` ou `Collections.unmodifiableList`).
- Lançar exceções padronizadas da JDK (`IllegalArgumentException`) ao receber parâmetros fora dos limites de negócio.

## 3. Especificação Funcional & Regras de Negócio

### 3.1. Cálculo de Juros Compostos

O método deve calcular o montante final ($M$) acumulado por um capital principal ($P$) aplicado a uma taxa de juros mensal ($i$, em formato decimal) durante $n$ meses:

$$M = P \times (1 + i)^n$$

- A taxa de juros é informada em valor decimal (por exemplo, `0.01` para 1% ao mês).
- O resultado deve ser arredondado para duas casas decimais (centavos).

### 3.2. Sistema de Amortização Constante (SAC)

No sistema SAC, a parcela de amortização do saldo devedor é estritamente constante ao longo de todos os meses:

$$A = \frac{P}{n}$$

Para cada mês $k$ (de 1 até $n$):
1. **Juros do mês ($J_k$):** Calculados sobre o saldo devedor remanescente do mês anterior:

   $$J_k = \text{Saldo}_{k-1} \times i$$

2. **Valor da parcela ($PMT_k$):** Soma da amortização constante com os juros do mês:

   $$PMT_k = A + J_k$$

3. **Novo saldo devedor ($\text{Saldo}_k$):** Saldo anterior subtraído da amortização:

   $$\text{Saldo}_k = \text{Saldo}_{k-1} - A$$

   *(No último mês $n$, o saldo residual deve ser zerado).*

### 3.3. Tabela Price (Sistema Francês de Amortização)

Na Tabela Price, o valor da parcela mensal ($PMT$) é constante durante todo o contrato:

$$PMT = P \times \frac{i \times (1 + i)^n}{(1 + i)^n - 1}$$

Para cada mês $k$ (de 1 até $n$):
1. **Juros do mês ($J_k$):** Calculados sobre o saldo anterior:

   $$J_k = \text{Saldo}_{k-1} \times i$$

2. **Amortização do mês ($A_k$):** Parcela fixa menos os juros:

   $$A_k = PMT - J_k$$

3. **Novo saldo devedor ($\text{Saldo}_k$):** Saldo anterior subtraído da amortização do mês:

   $$\text{Saldo}_k = \text{Saldo}_{k-1} - A_k$$

### 3.4. Regras de Validação & Invariantes

Todas as operações devem validar rigorosamente os parâmetros de entrada:

- Se `principal <= 0`: lançar `IllegalArgumentException` com mensagem `"Principal must be strictly positive"`.
- Se `monthlyRate < 0`: lançar `IllegalArgumentException` com mensagem `"Interest rate cannot be negative"`.
- Se `months <= 0`: lançar `IllegalArgumentException` com mensagem `"Number of months must be strictly positive"`.
- Se `system == null`: lançar `IllegalArgumentException` com mensagem `"Amortization system cannot be null"`.

### 3.5. Arredondamento e Imutabilidade

- Todos os valores monetários retornados nas parcelas e no relatório final devem estar arredondados em duas casas decimais.
- A lista de parcelas (`installments`) contida em `FinancialReport` deve ser estritamente não-modificável. Tentativas externas de mutação (como `.add()` ou `.clear()`) devem resultar em `UnsupportedOperationException`.

## 4. Estrutura das Classes & API Pública

O pacote base é `br.com.fatec.basic.ex01`:

- **`AmortizationSystem`** (Enum):
  - Valores: `SAC`, `PRICE`
- **`Installment`** (Record):
  - Campos: `int month`, `double installmentAmount`, `double amortization`, `double interest`, `double remainingBalance`
- **`FinancialReport`** (Record):
  - Campos: `double totalInterest`, `double totalPaid`, `List<Installment> installments`
- **`FinancialCalculator`** (Classe com métodos estáticos utilitários):
  - `public static double calculateCompoundInterest(double principal, double monthlyRate, int months)`
  - `public static FinancialReport generateAmortizationSchedule(double principal, double monthlyRate, int months, AmortizationSystem system)`
  - `public static double roundToTwoDecimals(double value)`

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex01/FinancialCalculator.java`.
2. Implemente os métodos solicitados garantindo que passem em todos os casos de teste.
3. Não altere a suíte de testes em `src/test/java`.
4. Todos os métodos públicos devem conter Javadoc formal (`@param`, `@return`, `@throws`).

## 6. Critérios de Aceite

- Todos os testes da suíte `FinancialCalculatorTest` devem passar sem erros nem falhas.
- Não deve haver vazamento de mutabilidade na lista de parcelas retornada.
