# Solução Proposta — ex07: Verificador de Números Primos

Este diretório contém a implementação de referência do professor e a análise técnica dos fundamentos de complexidade e teoria dos números.

## 1. Código da Solução

O arquivo resolvido está em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex07/PrimeNumberChecker.java`

## 2. Decisões Didáticas & Análise Conceitual

### 2.1. A Prova Matemática da Raiz Quadrada

Todo número composto $N$ possui divisores em pares. Por exemplo, os divisores de 36 são:
- 1 e 36
- 2 e 18
- 3 e 12
- 4 e 9
- 6 e 6

Note que o ponto de inflexão ocorre exatamente em $\sqrt{36} = 6$. Para qualquer par $(a, b)$ tal que $a \times b = N$:
- Se $a < \sqrt{N}$, então necessariamente $b > \sqrt{N}$.
- Se ambos fossem maiores que $\sqrt{N}$, teríamos $a \times b > \sqrt{N} \times \sqrt{N} = N$, o que é uma contradição.

Portanto, se não encontrarmos nenhum divisor até $\lfloor\sqrt{N}\rfloor$, temos a certeza matemática absoluta de que não existirá nenhum divisor acima de $\sqrt{N}$.

### 2.2. Por que `d * d <= n` é Superior a `Math.sqrt(n)`

Muitos estudantes escrevem o laço da seguinte forma:

```java
// Menos eficiente:
for (long d = 3; d <= Math.sqrt(n); d += 2) { ... }
```

Esse padrão possui desvantagens:
1. **Custo de CPU:** A cada iteração do laço, a função `Math.sqrt` é chamada novamente (a menos que seja extraída para uma variável externa). O cálculo de raiz quadrada envolve operações de ponto flutuante com aproximação iterativa (como o método de Newton), consumindo dezenas de ciclos de clock da CPU.
2. **Perda de Precisão:** Converter um inteiro de 64 bits (`long`) para `double` pode introduzir pequenos erros de arredondamento em números muito grandes, pois a mantissa do `double` do padrão IEEE 754 possui apenas 53 bits de precisão.

Ao utilizar `d * d <= n`, toda a aritmética permanece estritamente no domínio dos números inteiros (`long`), executando em um único ciclo de clock nativo do processador.

### 2.3. Poda de Pares e Passo 2

Ao tratar previamente `n == 2` (retorna `true`) e `n % 2 == 0` (retorna `false`), eliminamos 50% dos números possíveis antes mesmo de entrar no laço. Isso nos permite iniciar o divisor em `3` e incrementar de 2 em 2 (`d += 2`), cortando pela metade as iterações restantes:

$$\text{Total de iterações no pior caso} \approx \frac{\sqrt{N}}{2}$$

### 2.4. Alocação Exata de Arrays Primitivos

Como neste ponto da trilha ainda não introduzimos oficialmente a coleção `List<Long>` (que será explorada no `ex15`), o exercício exige que o aluno aloque um array primitivo de tamanho exato.

A solução elegante divide o problema em duas etapas:
1. `countPrimes`: determina a contagem precisa de elementos.
2. `findPrimesInRange`: aloca o vetor com `new long[count]` e preenche sequencialmente sem desperdício de memória nem elementos nulos.
