# Solução Proposta — ex03: O Mistério do Aliasing de Referências

Este diretório contém a resolução de referência do professor e a análise detalhada sobre o gerenciamento de memória em Java.

## 1. Código da Solução

Os arquivos resolvidos estão em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex03/ScoreSnapshot.java`
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex03/ScoreSnapshotTracker.java`

## 2. Diagnóstico & Análise Técnica do Bug

### 2.1. O que é Aliasing?

No Java, variáveis declaradas com tipos que não sejam primitivos (`int[]`, `String`, classes, interfaces, etc.) **não contêm os dados do objeto diretamente**. Em vez disso, elas guardam uma **referência** (um ponteiro para um endereço de memória) que aponta para onde o objeto realmente existe na área da memória chamada **Heap**.

Quando o desenvolvedor escreveu:

```java
// BUG CRÍTICO:
int[] updatedScores = scores;
```

Nenhum novo array foi alocado. O operador `=` simplesmente copiou os bits do endereço de memória de `scores` para `updatedScores`. Como resultado:
- Tanto `scores` quanto `updatedScores` apontavam para o mesmíssimo array na memória.
- Qualquer escrita como `updatedScores[i] += bonus` alterava fisicamente o array original que pertencia a quem chamou o método.

### 2.2. A Solução: Alocação Independente e Cópia Defensiva

Para eliminar o efeito colateral, a solução deve alocar um novo bloco de memória no Heap para o resultado:

```java
private static int[] applyBonusToScores(int[] scores, int bonus) {
    validateInputs(scores, bonus);

    // Aloca explicitamente um novo array com o mesmo tamanho
    int[] updatedScores = new int[scores.length];
    for (int i = 0; i < scores.length; i++) {
        updatedScores[i] = scores[i] + bonus;
    }

    return updatedScores;
}
```

E no método `processRoundBonus`, para garantir que o snapshot guarde o histórico original sem risco de que o chamador modifique seu próprio array depois e contamine o relatório, realizamos uma **cópia defensiva** (*defensive copy*):

```java
int[] originalScores = Arrays.copyOf(scores, scores.length);
```

### 2.3. Por que o Tipo Primitivo não Sofre com Aliasing?

No método `applyBonusToHighScore`, temos:

```java
private static int applyBonusToHighScore(int currentHighScore, int bonus) {
    return currentHighScore + bonus;
}
```

Como `int` é um **tipo primitivo**, seu valor numérico de 32 bits reside diretamente no quadro da **Stack** (pilha de execução) do método. Ao passar ou atribuir um primitivo, o Java realiza uma cópia exata do valor bit a bit (*pass-by-value*). Modificar esse valor local jamais afetará a variável original do método chamador.
