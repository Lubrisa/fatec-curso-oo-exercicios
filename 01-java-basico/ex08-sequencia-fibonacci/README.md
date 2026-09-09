# ex08 — Sequência de Fibonacci

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Algoritmo / Sequências Numéricas  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Atualização de Variáveis de Estado, Casos Base, Laços Iterativos, Limites Físicos de Representação (`long`) e Prevenção de Overflow Aritmético  

## 1. Contexto & Cenário

A Sequência de Fibonacci é uma das progressões matemáticas mais célebres da história, descrita pelo matemático italiano Leonardo de Pisa (Fibonacci) no início do século XIII. Seus termos aparecem na natureza (espirais de conchas, ramificação de árvores, arranjo de pétalas), no mercado financeiro (retrações de Fibonacci na análise técnica) e em algoritmos de computação (como estruturas de dados Fibonacci Heap e técnicas de busca de Fibonacci).

Você foi encarregado de criar um módulo utilitário de alta performance chamado `FibonacciSequenceGenerator`, responsável por gerar sequências de termos, consultar índices específicos e testar se um determinado número pertence à sucessão de Fibonacci de forma segura e rápida.

## 2. Objetivos de Aprendizagem

- Compreender a modelagem de sucessões definidas por recorrência aditiva: $F(n) = F(n-1) + F(n-2)$.
- Praticar a mecânica fundamental de **rotação de variáveis de estado** em laços iterativos (`proximo = a + b; a = b; b = proximo;`).
- Dominar o tratamento de **casos base** ($F(0) = 0$ e $F(1) = 1$) antes de iniciar iterações.
- Compreender a velocidade de crescimento exponencial de Fibonacci e identificar o limite exato de representação em tipos de 64 bits (`long`).
- Implementar validações defensivas com `IllegalArgumentException`.

## 3. Regras de Negócio & Modelagem Matemática

### 3.1. Definição da Sequência

A sucessão de Fibonacci é formalmente definida por:
- $F(0) = 0$ (termo no índice 0)
- $F(1) = 1$ (termo no índice 1)
- $F(n) = F(n-1) + F(n-2)$, para todo $n \ge 2$

Os primeiros termos gerados são:
```text
Índice:  0   1   2   3   4   5   6   7   8   9  10  11   12
Termo:   0,  1,  1,  2,  3,  5,  8, 13, 21, 34, 55, 89, 144, ...
```

---

### 3.2. A Fronteira do Overflow: Por que limitar em F(92)?

A sequência de Fibonacci cresce exponencialmente em uma taxa proporcional à proporção áurea ($\phi \approx 1{,}618$).

- O tipo `long` em Java armazena inteiros de 64 bits com sinal, suportando valores até `Long.MAX_VALUE` = $9.223.372.036.854.775.807$.
- O termo **$F(92)$** vale **$7.540.113.804.746.346.429L$**, cabendo confortavelmente dentro do `long`.
- O termo seguinte, **$F(93)$**, ultrapassaria o limite do `long` ($12{,}2 \times 10^{18}$), provocando um *overflow* silencioso com *wrap-around* para um número negativo.

Por essa razão, a biblioteca impõe as seguintes regras de validação:
- Para busca do n-ésimo termo (`getNthTerm`):
  - $n < 0$: lança `IllegalArgumentException("O índice do termo não pode ser negativo")`.
  - $n > 92$: lança `IllegalArgumentException("O índice excede o limite representável pelo tipo long (máximo 92)")`.
- Para geração da sequência (`generateSequence(count)`):
  - `count < 1`: lança `IllegalArgumentException("A quantidade de termos deve ser maior ou igual a 1")`.
  - `count > 93`: lança `IllegalArgumentException("A quantidade de termos excede o limite representável pelo tipo long (máximo 93 termos, até F(92))")`.

---

### 3.3. Verificação de Pertencimento (`isFibonacciNumber`)

Determina se um número inteiro qualquer pertence à sequência de Fibonacci:
- Números negativos retornam `false` de imediato.
- $0$ e $1$ retornam `true`.
- Para números maiores que 1, itera a sucessão comparando os termos gerados:
  - Se um termo gerado for igual ao número informado, retorna `true`.
  - Se o termo gerado ultrapassar o número informado, encerra o laço e retorna `false`.
  - Essa busca iterativa leva no máximo 93 iterações ($O(\log N)$), sendo praticamente instantânea!

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex08` na classe utilitária `FibonacciSequenceGenerator`:

### 4.1. `generateSequence`

```java
public static long[] generateSequence(int count)
```

- Aloca um array `long[]` de tamanho `count`.
- Se `count >= 1`, a primeira posição recebe `0L`.
- Se `count >= 2`, a segunda posição recebe `1L`.
- Para `count >= 3`, preenche as posições restantes com `array[i] = array[i - 1] + array[i - 2]`.
- Lança `IllegalArgumentException` caso `count < 1` ou `count > 93`.

### 4.2. `getNthTerm`

```java
public static long getNthTerm(int n)
```

- Trata os casos base: se $n = 0$, retorna `0L`; se $n = 1$, retorna `1L`.
- Mantém duas variáveis locais de estado (`a = 0L`, `b = 1L`) e roda um laço iterativo atualizando o estado sem alocar arrays desnecessários.
- Lança `IllegalArgumentException` caso $n < 0$ ou $n > 92$.

### 4.3. `isFibonacciNumber`

```java
public static boolean isFibonacciNumber(long number)
```

- Retorna `false` para números negativos.
- Retorna `true` se `number == 0L` ou `number == 1L`.
- Itera gerando os termos subsequentes até encontrar o valor ou ultrapassá-lo.

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex08/FibonacciSequenceGenerator.java`.
2. Implemente `generateSequence` inicializando os casos base e preenchendo o array com laço iterativo.
3. Implemente `getNthTerm` de forma iterativa com duas variáveis de estado (`a` e `b`), garantindo tempo $O(N)$ e memória constante $O(1)$.
4. Implemente `isFibonacciNumber` com interrupção antecipada assim que o termo gerado for maior ou igual ao número procurado.
5. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex08-sequencia-fibonacci
   ```

---

> ### 💡 Desafio Opcional: A Armadilha da Recursão Ingênua (O(2ⁿ) vs O(N))
>
> Ao aprender recursão, muitos livros apresentam o cálculo de Fibonacci desta forma:
> ```java
> public static long fiboRecursivo(int n) {
>     if (n <= 1) return n;
>     return fiboRecursivo(n - 1) + fiboRecursivo(n - 2);
> }
> ```
> Embora matematicamente elegante, essa implementação é uma **armadilha clássica de desempenho**:
> - Para calcular `fiboRecursivo(50)`, o computador realiza mais de **2 trilhões de chamadas redundantes**, recalculando `fibo(2)` bilhões de vezes e travando a CPU por minutos!
> - Sua implementação iterativa com laço `for`, por outro lado, calcula $F(50)$ em menos de **0,00001 milissegundo** com apenas 50 somas.
>
> 🔍 **Provocação:** Consegue perceber por que a abordagem iterativa que você acabou de implementar é infinitamente superior à recursão ingênua? Como você resolveria essa limitação se fosse obrigado a usar recursão? *(Dica: Pense no conceito de **Memoization** que discutimos no exercício de fatoriais!).*

## 6. Critérios de Aceite

- Todos os testes da classe `FibonacciSequenceGeneratorTest` devem passar com sucesso (`BUILD SUCCESS`).
- Casos base (`count = 1`, `count = 2`, `n = 0`, `n = 1`) devem ser tratados com exatidão.
- Casos limites ($F(92) = 7540113804746346429L$) devem ser calculados com precisão.
- Entradas inválidas (`count < 1`, `count > 93`, `n < 0`, `n > 92`) devem lançar `IllegalArgumentException` com as mensagens especificadas.
- O método `isFibonacciNumber` deve identificar corretamente números pertencentes e não-pertencentes à sequência.
