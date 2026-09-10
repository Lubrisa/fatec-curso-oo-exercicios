# Solução Proposta — ex11: Estatísticas de Vetor

Este documento apresenta a análise arquitetural, considerações de complexidade e boas práticas adotadas na resolução de referência do exercício **ex11 — Estatísticas de Vetor**.

---

## 1. Abordagem Arquitetural

### 1.1. Varredura em Passo Único ($O(N)$)

Ao implementar a agregação de múltiplas grandezas (`sum`, `min`, `max`), existem duas abordagens:

1. **Abordagem Composta (Múltiplos Passos):**
   ```java
   // Percorre o array 3 vezes (3N operações)
   double sum = calculateSum(values);
   double min = findMin(values);
   double max = findMax(values);
   ```
2. **Abordagem Agregada em Passo Único (Single-Pass):**
   ```java
   // Percorre o array apenas uma vez (N operações)
   double sum = values[0];
   double min = values[0];
   double max = values[0];

   for (int i = 1; i < values.length; i++) {
       double current = values[i];
       sum += current;
       if (current < min) min = current;
       if (current > max) max = current;
   }
   ```

A abordagem de **Passo Único** é a recomendada em sistemas de alta performance. Ela minimiza transferências de dados entre a memória RAM e a memória cache do processador (L1/L2), reduzindo a latência global de cálculo para $O(N)$ estrito e espaço auxiliar $O(1)$.

---

## 2. Armadilhas Clássicas da Linguagem Java

### 2.1. O Comportamento Contraintuitivo de `Double.MIN_VALUE`

Em tipos inteiros (`int`, `long`), `Integer.MIN_VALUE` é o menor número negativo possível ($-2^{31} = -2.147.483.648$).

No entanto, nos tipos de ponto flutuante IEEE 754 (`float`, `double`):
- `Double.MIN_VALUE` é **$4.9 \times 10^{-324}$**, ou seja, o **menor valor positivo normalizável/subnormal**, estritamente maior que zero!
- Para obter o menor valor negativo representável, seria necessário utilizar `-Double.MAX_VALUE` ou `Double.NEGATIVE_INFINITY`.

Por essa razão, a inicialização mais elegante, segura e idiomática para vetores não vazios é atribuir `min = values[0]` e `max = values[0]`, iniciando a iteração a partir do índice `1`.

---

## 3. Resolução do Desafio: Cálculo do Desvio Padrão Populacional

O **Desvio Padrão Populacional** ($\sigma$) mede o grau de dispersão dos valores em relação à média aritmética:

$$\sigma = \sqrt{\frac{1}{N} \sum_{i=1}^{N} (x_i - \mu)^2}$$

### Implementação:

```java
public static double calculateStandardDeviation(double[] values) {
    validateArray(values);
    double avg = calculateAverage(values);
    double sumOfSquaredDiffs = 0.0;
    
    for (double val : values) {
        double diff = val - avg;
        sumOfSquaredDiffs += diff * diff;
    }
    
    return Math.sqrt(sumOfSquaredDiffs / values.length);
}
```

- **Complexidade de Tempo:** $O(N)$ (duas passagens: uma para a média e outra para o somatório dos quadrados dos desvios).
- **Complexidade de Espaço Auxiliar:** $O(1)$ (apenas variáveis primitivas locais).

---

## 4. Estrutura Imutável com `record`

A utilização do `record VectorStatistics` garante:
- Imutabilidade natural (todos os campos são `final`).
- Métodos de acesso limpos (`stats.average()` em vez do verboso `stats.getAverage()`).
- Implementações automáticas e consistentes de `equals()`, `hashCode()` e `toString()`.
- Validação no construtor compacto assegurando que relatórios estatísticos nunca sejam instanciados com contagem nula ou negativa.
