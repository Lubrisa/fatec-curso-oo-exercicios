# Solução Proposta — ex08: Sequência de Fibonacci

Este diretório contém a implementação de referência do professor e a fundamentação técnica sobre variáveis de estado, complexidade algorítmica e análise de estouro de representação numérica.

## 1. Código da Solução

O arquivo resolvido está em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex08/FibonacciSequenceGenerator.java`

## 2. Decisões Didáticas & Análise Conceitual

### 2.1. Rotação de Variáveis de Estado em Memória O(1)

Muitos estudantes iniciantes, ao implementar `getNthTerm(n)`, alocam um array completo de tamanho `n + 1` usando `generateSequence(n + 1)` para simplesmente pegar a última posição `array[n]`.

Embora essa solução funcione, ela consome memória desnecessária no Heap ($O(N)$). A solução profissional mantém apenas duas variáveis primitivas no Stack:

```java
long a = 0L;
long b = 1L;
for (int i = 2; i <= n; i++) {
    long next = a + b;
    a = b;
    b = next;
}
return b;
```

Essa rotação de registradores possui custo de alocação nulo e executa estritamente em espaço constante **$O(1)$** e tempo linear **$O(N)$**.

### 2.2. A Fronteira Exata do Tipo `long` na JVM

Por que o limite máximo seguro é $92$?

O tipo primitivo `long` em Java é um inteiro com sinal de 64 bits em complemento de dois, cujo maior valor representável é:

$$\text{Long.MAX\_VALUE} = 2^{63} - 1 = 9.223.372.036.854.775.807$$

Ao computar os termos da sequência:
- $F(91) = 4.660.046.610.375.530.309L$
- $F(92) = 7.540.113.804.746.346.429L \le \text{Long.MAX\_VALUE}$ (OK)
- $F(93) = F(92) + F(91) = 12.200.160.415.121.876.738 > \text{Long.MAX\_VALUE}$

Ao somar $F(91) + F(92)$, ocorre um *integer overflow* que inverte o bit mais significativo de sinal, resultando no valor negativo `-6246583658587674878L`.

Ao validar rigorosamente `n > 92` e `count > 93`, protegemos a integridade da aplicação antes que números corrompidos cheguem às regras de negócio. Se fosse necessário calcular além de $F(92)$, a classe `java.math.BigInteger` seria a ferramenta adequada (com precisão arbitrária).

### 2.3. Análise da Armadilha Recursiva Ingênua: O(2ⁿ) vs O(N)

A definição recursiva clássica:

```java
public static long fibo(int n) {
    if (n <= 1) return n;
    return fibo(n - 1) + fibo(n - 2);
}
```

Gera uma árvore binária de chamadas de profundidade $N$. Para calcular $F(5)$:
```text
                     f(5)
            /                    \
         f(4)                    f(3)
        /    \                  /    \
     f(3)    f(2)            f(2)    f(1)
    /   \    /  \            /  \
  f(2) f(1) f(1) f(0)      f(1) f(0)
  / \
f(1) f(0)
```

Observe a quantidade massiva de trabalho redundante: `f(3)` é calculado 2 vezes, `f(2)` é calculado 3 vezes e `f(1)` é calculado 5 vezes! O total de nós na árvore é proporcional à razão áurea elevada a $N$, ou seja, **$O(1{,}618^N) \approx O(2^N)$**.

Enquanto a versão recursiva ingênua levaria séculos para calcular $F(80)$, a solução iterativa implementada roda em aproximadamente **$92$ nanossegundos**.
