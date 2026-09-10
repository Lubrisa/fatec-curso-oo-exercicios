# ex03 — O Mistério do Aliasing de Referências

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Bugfix / Diagnóstico de Código  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Tipos Primitivos vs Tipos por Referência, Aliasing em Memória Heap, Efeitos Colaterais Indesejados e Diagnóstico com Testes  

## 1. Contexto & Chamado de Incidente

Você atua como desenvolvedor em uma plataforma de torneios de e-sports. O sistema possui um módulo responsável por processar as pontuações de cada rodada, aplicando bonificações de vitória e gerando um relatório (*snapshot*) com os valores antes e depois da bonificação para fins de auditoria.

Hoje pela manhã, a equipe de auditoria e compliance abriu o seguinte chamado crítico:

---

### 📋 Chamado de Suporte #4092: Corrupção do Histórico de Pontuações

> **Prioridade:** Alta  
> **Módulo Afetado:** `ScoreSnapshotTracker`  
> **Descrição do Problema:**  
> *"Ao processar o bônus da rodada no final da partida, o histórico de pontuações originais dos jogadores está desaparecendo ou sendo sobrescrito! Quando a equipe de auditoria tenta inspecionar o array de pontuações iniciais para conferência com as súmulas dos juízes, os valores originais já aparecem alterados com o bônus somado.*  
> *Além disso, os sistemas parceiros que nos passam o array de pontuações relatam que as variáveis deles também estão sendo modificadas misteriosamente após chamar o nosso método!"*

---

## 2. Objetivos de Aprendizagem

- Diferenciar o modelo de memória de **tipos primitivos** (onde os valores residem diretamente na variável) de **tipos por referência** (onde a variável guarda apenas o endereço do objeto no Heap).
- Diagnosticar problemas de **aliasing de referências** (*reference aliasing*) por meio da análise de comportamento e rastreamento de variáveis.
- Eliminar efeitos colaterais indesejados, garantindo que métodos de cálculo não mutem estruturas recebidas como parâmetro.
- Interpretar relatórios de falha de testes automatizados (`AssertionError`) para isolar a causa-raiz de um defeito.

## 3. O Problema Reportado & Comportamento Observado

- **Comportamento Esperado:**
  - O array de pontuações recebido como parâmetro pelo método deve permanecer **estritamente inalterado** após a execução (sem efeitos colaterais no chamador).
  - O relatório `ScoreSnapshot` deve registrar em `originalScores` os valores iniciais intactos e em `updatedScores` os valores acrescidos da bonificação.
  - A pontuação máxima individual (`highScore`), que é um número primitivo `int`, deve manter sua versão original e sua versão atualizada devidamente registradas.

- **Comportamento Observado (O Bug):**
  - A execução atual dos testes automatizados acusa falhas apontando que o array original foi mutado e que as pontuações antigas foram perdidas.

## 4. Estrutura de Apoio Fornecida (`ScoreSnapshot`)

> ℹ️ **Estrutura Pré-Pronta:** O arquivo `ScoreSnapshot.java` já vem implementado e **não precisa ser modificado**. Ele funciona como um agrupador imutável de dados para consolidar o estado antes e depois da bonificação:

```java
public record ScoreSnapshot(
        int[] originalScores,
        int[] updatedScores,
        int originalHighScore,
        int updatedHighScore
) {}
```

---

## 5. Pistas de Investigação & Hipóteses a Considerar

Ao investigar o código da classe `ScoreSnapshotTracker`, considere as seguintes perguntas norteadoras:

1. **O Contraste entre Primitivos e Objetos:**  
   Por que o cálculo do `highScore` (que utiliza o tipo primitivo `int`) funcionou sem corromper o valor original, enquanto o array de `scores` sofreu alteração indevida?
2. **O Comportamento do Operador de Atribuição (`=`):**  
   Quando atribuímos uma variável de array a outra, ou a passamos como argumento para um método, o Java cria um novo conjunto de dados na memória ou compartilha o acesso à mesma estrutura existente?
3. **Isolamento de Efeitos Colaterais:**  
   Como garantir que o cálculo de bonificação opere sobre uma estrutura independente, sem alterar o array que pertence ao chamador externo?

---

## 6. O que Você Deve Fazer

1. Execute a suíte de testes automatizados para reproduzir o bug:
   ```bash
   ./mvnw test -pl :ex03-pegadinha-tipos-referencia
   ```
2. Analise as mensagens de erro reportadas pelo JUnit e AssertJ.
3. Abra a classe `src/main/java/br/com/fatec/basic/ex03/ScoreSnapshotTracker.java`.  
   *(O arquivo `ScoreSnapshot.java` já está pronto e não precisa de nenhuma modificação).*
4. Rastreie como o array está sendo manipulado na memória e corrija o defeito, assegurando a integridade do array original e a independência do relatório.
5. Reexecute os testes até obter `BUILD SUCCESS`.

> **Atenção:** Você **NÃO deve alterar a classe de testes** (`ScoreSnapshotTrackerTest.java`). Os testes representam o contrato de conformidade que seu código deve satisfazer.

---

## 7. Critérios de Aceite

- Todos os testes da classe `ScoreSnapshotTrackerTest` devem passar com sucesso (`BUILD SUCCESS`).
- O código do teste não deve sofrer nenhuma modificação.
- O array original fornecido pelo chamador deve permanecer estritamente inalterado.
