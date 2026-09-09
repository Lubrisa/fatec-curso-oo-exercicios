# ex03 — O Mistério do Aliasing de Referências

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Bugfix / Diagnóstico de Código  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Tipos Primitivos vs Tipos por Referência, Efeitos Colaterais, Diagnóstico por Testes Automatizados  

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

## 2. Comportamento Esperado vs Observado

- **Comportamento Esperado:**
  - O array de pontuações recebido como parâmetro pelo método deve permanecer **estritamente inalterado** após a execução (sem efeitos colaterais no chamador).
  - O relatório `ScoreSnapshot` deve registrar em `originalScores` os valores iniciais intactos e em `updatedScores` os valores acrescidos da bonificação.
  - A pontuação máxima individual (`highScore`), que é um número inteiro, também deve ter sua versão original e sua versão atualizada devidamente registradas.

- **Comportamento Observado:**
  - A execução atual dos testes automatizados acusa falhas apontando que o array original foi mutado e que as pontuações antigas foram perdidas.

## 3. Sua Missão

Como engenheiro de software responsável pelo módulo, você deve:

1. **Executar a suíte de testes automatizados:**
   ```bash
   ./mvnw test -pl :ex03-pegadinha-tipos-referencia
   ```
2. **Analisar as falhas nos testes:**
   - Observe quais cenários falharam e leia com atenção as mensagens de erro reportadas pelo JUnit e AssertJ.
3. **Investigar o código-fonte:**
   - Abra o arquivo `src/main/java/br/com/fatec/basic/ex03/ScoreSnapshotTracker.java`.
   - Rastreie o ciclo de vida das variáveis e analise como os dados estão sendo manipulados na memória durante a execução dos métodos.
4. **Corrigir o defeito:**
   - Aplique a alteração necessária para eliminar o efeito colateral indesejado, garantindo que o array original seja preservado e que o novo cálculo funcione de forma independente.
   - **Atenção:** Você **NÃO deve alterar a classe de testes** (`ScoreSnapshotTrackerTest.java`). Os testes representam o contrato de conformidade que seu código deve satisfazer.

## 4. Critérios de Aceite

- Todos os testes da classe `ScoreSnapshotTrackerTest` devem passar com sucesso (`BUILD SUCCESS`).
- O código do teste não deve sofrer nenhuma modificação.
- O array original fornecido pelo chamador deve permanecer estritamente inalterado.
