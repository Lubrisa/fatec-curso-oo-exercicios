# Solução Proposta — ex06: Gerador de Tabuada e Fatorial

Este diretório contém a implementação de referência do professor e a fundamentação técnica sobre estruturas de repetição, limites numéricos na JVM e otimização assintótica.

## 1. Código da Solução

O arquivo resolvido está em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex06/TableAndFactorialGenerator.java`

## 2. Decisões Didáticas & Análise Conceitual

### 2.1. Acumuladores de Soma vs Acumuladores de Produto

Uma falha conceitual muito frequente entre iniciantes ao implementar fatoriais é inicializar a variável acumuladora em `0` (hábito herdado de acumuladores de soma):

```java
// ERRADO: Inicializar acumulador de produto com 0
long product = 0L;
for (int i = 2; i <= n; i++) {
    product *= i; // Qualquer número multiplicado por 0 continuará 0!
}

// CORRETO: O elemento neutro da multiplicação é 1
long product = 1L;
for (int i = 2; i <= n; i++) {
    product *= i;
}
```

Além disso, iniciar o laço a partir de `i = 2` (em vez de `1`) economiza uma iteração inútil (1 × 1 = 1) e trata elegantemente os casos base 0! = 1 e 1! = 1 sem necessidade de condicionais adicionais, já que a condição `i <= n` não será satisfeita e o método retornará diretamente o valor inicial `1L`.

### 2.2. O Fenômeno do Overflow Silencioso na JVM

Em Java, os tipos numéricos primitivos inteiros (`byte`, `short`, `int`, `long`) operam segundo a aritmética modular de **complemento de dois**. Quando uma operação excede o valor máximo suportado pelo tipo:
- A JVM **não lança exceção automaticamente** (a menos que sejam usadas classes específicas como `Math.multiplyExact`).
- Os bits mais significativos são descartados e o bit de sinal é invertido, gerando números truncados ou negativos inesperados.

#### Exemplo Prático com Fatorial:

- Em `int` (32 bits com sinal, máximo `2.147.483.647`):
  - 12! = 479.001.600 (cabe em `int`).
  - 13! = 6.227.020.800 (estoura `int`, resultando silenciosamente em `1.932.053.504`).
- Em `long` (64 bits com sinal, máximo `9.223.372.036.854.775.807`):
  - 20! = 2.432.902.008.176.640.000 (cabe perfeitamente).
  - 21! = 51.090.942.171.709.440.000 (estoura `long`, gerando o valor negativo `-4.249.201.492.648.067.072L`).

Por essa razão, validar a entrada com `n > MAX_SAFE_FACTORIAL_INPUT` (20) é a forma profissional de garantir a integridade dos dados e ensinar aos alunos que tipos primitivos têm fronteiras físicas na memória.

### 2.3. Resolução do Desafio Opcional: Otimização O(end) com Recorrência Acumulativa

O método `calculateFactorialsInRange` pode ser abordado de duas formas fundamentais:

#### Abordagem 1: Ingênua por Rechamada — O(end²)

Recalcula cada termo a partir do 1 chamando `calculateFactorial(start + i)` a cada passo:

```java
for (int i = 0; i < size; i++) {
    factorials[i] = calculateFactorial(start + i);
}
```
- **Custo:** Para uma faixa de 0 a 20, o processador executa 190 multiplicações. Se a faixa fosse maior, o custo quadrático cresceria drasticamente.

#### Abordagem 2: Otimizada com Recorrência (Memoization em Array) — O(end)

Pela própria definição matemática do fatorial:
```text
N! = (N - 1)! * N
```

Isso significa que, tendo calculado o fatorial da posição anterior (`factorials[i - 1]`), o próximo elemento é simplesmente obtido com **uma única multiplicação**:

```java
factorials[0] = calculateFactorial(start);
for (int i = 1; i < size; i++) {
    int currentNumber = start + i;
    factorials[i] = factorials[i - 1] * currentNumber;
}
```

- **Custo:** Executa apenas uma única chamada inicial para `start!` e exatamente `(size - 1)` multiplicações subsequentes, caindo para **19 multiplicações** no intervalo de 0 a 20 (uma redução de **10 vezes** no volume de operações!).
- **Ganho Didático:** Essa é a introdução mais natural à ideia de **Programação Dinâmica / Memoization**: em vez de recomputar trabalho do zero, reaproveitamos o estado já guardado na memória.
