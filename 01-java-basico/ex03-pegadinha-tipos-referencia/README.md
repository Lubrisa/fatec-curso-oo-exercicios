# ex03 — O Mistério do Aliasing de Referências

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Bugfix / Diagnóstico de Memória  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Tipos Primitivos vs Tipos por Referência, Memória Stack vs Heap, Compartilhamento de Referência (*Aliasing*), Clonagem e Efeitos Colaterais  

## 1. Contexto & Cenário

Você acabou de entrar na equipe de desenvolvimento de uma plataforma de torneios de e-sports. O sistema possui um módulo responsável por registrar as pontuações obtidas pelos competidores e emitir um relatório consolidado (*snapshot*) antes e depois da aplicação de pontos de bonificação da rodada.

Recentemente, jogadores e auditores começaram a relatar um comportamento estranho:
> *"Toda vez que o sistema aplica o bônus da rodada, o histórico original de pontuações desaparece ou é sobrescrito pelos novos valores com bônus, impossibilitando a conferência das pontuações iniciais!"*

Ao inspecionar a classe utilitária `ScoreSnapshotTracker`, você encontrou um clássico caso de **aliasing de referências** (*referência compartilhada*), introduzido por um desenvolvedor que presumiu que atribuir um array a uma nova variável criaria uma cópia independente de seus dados.

## 2. Objetivos de Aprendizagem

- Compreender a diferença fundamental entre **tipos primitivos** (`int`, `double`, `boolean`) e **tipos por referência** (arrays, objetos).
- Visualizar o funcionamento da memória no Java: variáveis locais primitivas armazenam o próprio valor diretamente na **Stack**, enquanto variáveis de referência armazenam apenas o endereço de memória que aponta para o objeto residente no **Heap**.
- Identificar os perigos do *aliasing*: quando duas variáveis apontam para o mesmo objeto, alterar o conteúdo por meio de uma reflete instantaneamente na outra.
- Praticar a criação de cópias defensivas (*defensive copy*) em arrays utilizando `scores.clone()`, `Arrays.copyOf()` ou instanciação com cópia manual elemento a elemento.

## 3. Especificação Funcional

A classe utilitária `ScoreSnapshotTracker` possui os seguintes métodos:

### 3.1. Aplicação de Bônus em Pontuações (Com Bug)

```java
private static int[] applyBonusToScores(int[] scores, int bonus)
```

- **Comportamento Esperado:** Deve receber um array de pontuações e retornar um **novo array independente**, contendo cada pontuação original somada ao valor de `bonus`.
- **Regra Fundamental de Isolamento:** O array `scores` original recebido como argumento **não pode ser modificado** (efeito colateral proibido). O array retornado deve residir em uma posição de memória diferente no Heap (`returned != scores`).
- **Validação:** Se `scores == null`, lance `IllegalArgumentException("O array de pontuações não pode ser nulo")`. Se `bonus < 0`, lance `IllegalArgumentException("O bônus não pode ser negativo")`.

### 3.2. Aplicação de Bônus no Recorde Individual (Tipo Primitivo)

```java
private static int applyBonusToHighScore(int currentHighScore, int bonus)
```

- Soma o bônus à pontuação máxima individual.
- Como `currentHighScore` é do tipo primitivo `int`, sua atribuição já gera uma cópia do valor numérico na Stack, sem risco de aliasing.
- **Validação:** Se `bonus < 0`, lance `IllegalArgumentException("O bônus não pode ser negativo")`.

### 3.3. Geração do Resumo da Rodada (`processRoundBonus`)

```java
public static ScoreSnapshot processRoundBonus(int[] scores, int bonus, int currentHighScore)
```

Este é o método público que compõe os dados e gera o relatório `ScoreSnapshot`:

1. Valida as entradas (`scores != null` e `bonus >= 0`).
2. Mantém uma cópia defensiva das pontuações originais para auditoria.
3. Gera o novo array com os bônus aplicados chamando `applyBonusToScores`.
4. Calcula a nova pontuação máxima chamando `applyBonusToHighScore`.
5. Retorna o registro imutável com a estrutura já engatilhada:
   ```java
   return new ScoreSnapshot(originalScores, updatedScores, currentHighScore, updatedHighScore);
   ```

## 4. Diagnóstico do Bug

Observe o trecho de código original que causou o defeito:

```java
// CÓDIGO COM DEFEITO:
private static int[] applyBonusToScores(int[] scores, int bonus) {
    int[] updatedScores = scores; // <-- O BUG ESTÁ AQUI!
    for (int i = 0; i < updatedScores.length; i++) {
        updatedScores[i] += bonus;
    }
    return updatedScores;
}
```

A linha `int[] updatedScores = scores;` **não cria um novo array**. Ela apenas copia o endereço de memória da referência. Tanto `scores` quanto `updatedScores` passam a apontar para o mesmíssimo bloco no Heap. Ao alterar `updatedScores[i]`, os dados do array original do chamador são irreversivelmente modificados.

## 5. O que Você Deve Fazer

1. Abra o arquivo `src/main/java/br/com/fatec/basic/ex03/ScoreSnapshotTracker.java`.
2. Observe as falhas nos testes automatizados executando `mvn test`.
3. Corrija o método `applyBonusToScores` para que ele crie um **novo array**, preenchendo-o com as pontuações acrescidas do bônus, sem mutar o array recebido.
4. No método `processRoundBonus`, certifique-se de que o histórico original preservado no relatório (`originalScores`) também seja uma cópia independente dos dados originais.
5. Verifique se todas as validações de argumentos (*fail-fast*) estão sendo devidamente aplicadas.

## 6. Critérios de Aceite

- Todos os testes da classe `ScoreSnapshotTrackerTest` devem passar com sucesso.
- O array retornado por `applyBonusToScores` deve ser uma referência diferente do array de entrada (`assertThat(result).isNotSameAs(scores)`).
- O array original fornecido pelo chamador deve permanecer estritamente inalterado após a execução.
