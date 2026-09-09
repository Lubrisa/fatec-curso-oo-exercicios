# ex06 — Gerador de Tabuada e Fatorial

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica Matemática  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Estruturas de Repetição (`for`, `while`), Contadores, Acumuladores de Produto, Tipos Primitivos Inteiros (`int` vs `long`), Limites de Representação e Overflow Aritmético  

## 1. Contexto & Cenário

Você está desenvolvendo o núcleo de cálculo para uma biblioteca educacional de matemática e análise combinatória. Essa biblioteca é empregada em softwares acadêmicos para demonstração de progressões aritméticas, tabuadas personalizadas e cálculos de arranjos, permutações e probabilidades.

O sistema precisa de uma classe utilitária de alta performance capaz de gerar sequências tabulares de multiplicação e calcular fatoriais de números inteiros de forma segura, prevenindo estouros de memória e inconsistências causadas por *overflow* aritmético.

Sua tarefa é implementar a classe utilitária `TableAndFactorialGenerator`.

## 2. Objetivos de Aprendizagem

- Dominar a sintaxe e o funcionamento de laços de repetição definidos (`for`) e condicionais (`while`).
- Trabalhar com **acumuladores de produto** inicializados com elemento neutro (`produto = 1L`).
- Compreender a diferença entre a capacidade de armazenamento de inteiros de 32 bits (`int`, até ~2,14 bilhões) e de 64 bits (`long`, até ~9,22 quintilhões).
- Reconhecer e mitigar o fenômeno do **overflow aritmético** (estouro silencioso de tipos numéricos primitivos em Java).
- Alocar, iterar e preencher vetores unidimensionais (`int[]` e `long[]`) com base no índice e nos limites do laço.
- Aplicar validações defensivas (*fail-fast*) com `IllegalArgumentException`.

## 3. Regras de Negócio & Modelagem Matemática

### 3.1. Tabuada de Multiplicação

A tabuada de uma base inteira qualquer consiste no produto dessa base pelos fatores sequenciais de `1` até um limite superior `upTo`:

```text
Exemplo: base = 7, upTo = 5
Posição [0] -> 7 * 1 = 7
Posição [1] -> 7 * 2 = 14
Posição [2] -> 7 * 3 = 21
Posição [3] -> 7 * 4 = 28
Posição [4] -> 7 * 5 = 35
Array resultante: [7, 14, 21, 28, 35]
```

- A `base` pode ser qualquer valor inteiro (positivo, zero ou negativo).
- O limite `upTo` deve ser um número estritamente positivo (`upTo >= 1`). Caso contrário, deve ser rejeitado.

---

### 3.2. Fatorial e o Perigo do Overflow Aritmético

O fatorial de um número inteiro não-negativo $N$ (representado por $N!$) é o produto de todos os inteiros positivos menores ou iguais a $N$:

```text
0! = 1  (por convenção matemática / caso base)
1! = 1
2! = 2 * 1 = 2
3! = 3 * 2 * 1 = 6
4! = 4 * 3 * 2 * 1 = 24
...
10! = 3.628.800
```

#### ⚠️ Por que usar `long` e por que limitar a $N \le 20$?

O crescimento da função fatorial é extremamente acelerado (super-exponencial):
- **Estouro do `int`:** O maior valor representável por um `int` é `2.147.483.647` ($2^{31} - 1$). Logo no número 13, temos $13! = 6.227.020.800$, o que excede a capacidade de um `int` e causa um estouro silencioso gerando números incorretos ou negativos!
- **Estouro do `long`:** O tipo `long` armazena inteiros com sinal de 64 bits até `9.223.372.036.854.775.807` ($2^{63} - 1$). Ele comporta com precisão exata até $20! = 2.432.902.008.176.640.000$.
- A partir de $N = 21$, o valor real de $21!$ ultrapassa a capacidade máxima do `long`. No Java, operações que ultrapassam os limites de tipos primitivos realizam um *wrap-around* em complemento de dois, produzindo valores absurdos sem lançar exceção automática da JVM.

Por essa razão, a classe deve validar os limites de forma estrita:
- Se $N < 0$, lança `IllegalArgumentException("Não é possível calcular fatorial de número negativo")`.
- Se $N > 20$, lança `IllegalArgumentException("O valor excede o limite representável pelo tipo long (máximo 20)")`.

---

### 3.3. Tabela de Fatoriais em Intervalo

A biblioteca também permite gerar os fatoriais de uma faixa contínua de números de `start` até `end` (inclusivo):

```text
Exemplo: start = 3, end = 6
Tamanho do array: (6 - 3 + 1) = 4 posições
Posição [0] -> 3! = 6
Posição [1] -> 4! = 24
Posição [2] -> 5! = 120
Posição [3] -> 6! = 720
Array resultante: [6L, 24L, 120L, 720L]
```

- Se `start < 0`, lança `IllegalArgumentException("O valor inicial não pode ser negativo")`.
- Se `end > 20`, lança `IllegalArgumentException("O valor final excede o limite representável pelo tipo long (máximo 20)")`.
- Se `start > end`, lança `IllegalArgumentException("O valor inicial não pode ser maior que o valor final")`.

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex06` na classe utilitária `TableAndFactorialGenerator`:

### 4.1. `generateMultiplicationTable`

```java
public static int[] generateMultiplicationTable(int base, int upTo)
```

- Aloca um array `int[]` de tamanho `upTo`.
- Utiliza um laço (`for` ou `while`) para preencher os produtos de `1` até `upTo`.
- Lança `IllegalArgumentException("O limite superior da tabuada deve ser maior ou igual a 1")` se `upTo < 1`.

### 4.2. `calculateFactorial`

```java
public static long calculateFactorial(int n)
```

- Acumula o produto em uma variável local do tipo `long` inicializada em `1L`.
- Retorna `1L` para $n = 0$ ou $n = 1$.
- Lança `IllegalArgumentException("Não é possível calcular fatorial de número negativo")` se `n < 0`.
- Lança `IllegalArgumentException("O valor excede o limite representável pelo tipo long (máximo 20)")` se `n > 20`.

### 4.3. `calculateFactorialsInRange`

```java
public static long[] calculateFactorialsInRange(int start, int end)
```

- Valida o intervalo (`start >= 0`, `end <= 20` e `start <= end`).
- Aloca um array `long[]` de tamanho `(end - start + 1)`.
- Itera de `start` até `end`, preenchendo o array com os fatoriais correspondentes.

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex06/TableAndFactorialGenerator.java`.
2. Implemente o método `generateMultiplicationTable` utilizando um laço iterativo e preenchendo o vetor de retorno.
3. Implemente o cálculo de `calculateFactorial` com controle de limites e acumulador `long`.
4. Implemente `calculateFactorialsInRange` reaproveitando `calculateFactorial`.
5. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex06-gerador-tabuada-fatorial
   ```

## 6. Critérios de Aceite

- Todos os testes da classe `TableAndFactorialGeneratorTest` devem passar com sucesso (`BUILD SUCCESS`).
- Casos limites da tabuada (`upTo = 1`, bases zero e negativas) devem ser suportados corretamente.
- Casos limites do fatorial ($0! = 1$, $1! = 1$ e $20! = 2432902008176640000L$) devem ser exatos.
- Tentativas de calcular fatorial para $n < 0$ ou $n > 20$ devem lançar `IllegalArgumentException` com as mensagens especificadas.
