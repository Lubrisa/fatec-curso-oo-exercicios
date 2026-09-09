package br.com.fatec.basic.ex03;

/**
 * Classe utilitária responsável pela gestão e auditoria de pontuações de partidas.
 *
 * <p>ATENÇÃO: Esta classe contém um defeito clássico de aliasing de referências na memória.
 * Seu objetivo é diagnosticar o problema e implementar a cópia defensiva para evitar
 * efeitos colaterais indesejados no array original do chamador.</p>
 */
public final class ScoreSnapshotTracker {

    private ScoreSnapshotTracker() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Processa a bonificação da rodada e gera um snapshot auditável contendo
     * o estado antes e depois da aplicação dos pontos.
     *
     * @param scores array com as pontuações dos jogadores na rodada
     * @param bonus quantidade de pontos a ser adicionada a cada jogador (deve ser >= 0)
     * @param currentHighScore recorde individual atual
     * @return uma instância de ScoreSnapshot com o estado original e o estado atualizado
     * @throws IllegalArgumentException se o array for nulo ou se o bônus for negativo
     */
    public static ScoreSnapshot processRoundBonus(int[] scores, int bonus, int currentHighScore) {
        validateInputs(scores, bonus);

        // TODO: Garanta que 'originalScores' seja uma cópia independente para preservar a auditoria!
        // Atualmente, há risco de aliasing se utilizarmos a referência direta.
        int[] originalScores = scores;

        // Gera as novas pontuações aplicando o bônus
        int[] updatedScores = applyBonusToScores(scores, bonus);

        // Aplica o bônus ao recorde individual (observe como o tipo primitivo se comporta)
        int updatedHighScore = applyBonusToHighScore(currentHighScore, bonus);

        // Retorno engatilhado para o aluno:
        return new ScoreSnapshot(originalScores, updatedScores, currentHighScore, updatedHighScore);
    }

    /**
     * Aplica uma bonificação a cada pontuação contida no array.
     *
     * <p>ATENÇÃO: Este método NÃO deve alterar o array passado como argumento.
     * Deve retornar um novo array com os novos valores calculados.</p>
     *
     * @param scores array original com as pontuações
     * @param bonus valor do bônus a ser adicionado
     * @return novo array contendo as pontuações atualizadas
     * @throws IllegalArgumentException se o array for nulo ou se o bônus for negativo
     */
    private static int[] applyBonusToScores(int[] scores, int bonus) {
        validateInputs(scores, bonus);

        // BUG: A atribuição abaixo copia apenas a referência do objeto no Heap!
        // Ambas as variáveis 'scores' e 'updatedScores' apontam para o mesmo array.
        // Ao modificar 'updatedScores[i]', o array original do chamador é corrompido.
        //
        // TODO: Crie um NOVO array com o mesmo tamanho (ex: new int[scores.length] ou scores.clone())
        // e preencha-o com as novas pontuações calculadas sem alterar o array original!
        int[] updatedScores = scores;

        for (int i = 0; i < updatedScores.length; i++) {
            updatedScores[i] += bonus;
        }

        return updatedScores;
    }

    /**
     * Aplica o bônus sobre a pontuação máxima individual.
     *
     * <p>Observe que, por se tratar de um tipo primitivo (int), a atribuição gera
     * uma cópia real do valor na memória Stack, sem risco de aliasing.</p>
     *
     * @param currentHighScore pontuação máxima atual
     * @param bonus valor do bônus a ser somado
     * @return novo valor com o bônus adicionado
     * @throws IllegalArgumentException se o bônus for negativo
     */
    private static int applyBonusToHighScore(int currentHighScore, int bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("O bônus não pode ser negativo");
        }
        int updatedHighScore = currentHighScore;
        updatedHighScore += bonus;
        return updatedHighScore;
    }

    private static void validateInputs(int[] scores, int bonus) {
        if (scores == null) {
            throw new IllegalArgumentException("O array de pontuações não pode ser nulo");
        }
        if (bonus < 0) {
            throw new IllegalArgumentException("O bônus não pode ser negativo");
        }
    }
}
