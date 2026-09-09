package br.com.fatec.basic.ex03;

/**
 * Classe utilitária responsável pela gestão e auditoria de pontuações de partidas.
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

        int[] originalScores = scores;
        int[] updatedScores = applyBonusToScores(scores, bonus);
        int updatedHighScore = applyBonusToHighScore(currentHighScore, bonus);

        return new ScoreSnapshot(originalScores, updatedScores, currentHighScore, updatedHighScore);
    }

    /**
     * Aplica uma bonificação a cada pontuação contida no array.
     *
     * @param scores array com as pontuações
     * @param bonus valor do bônus a ser adicionado
     * @return array contendo as pontuações atualizadas
     * @throws IllegalArgumentException se o array for nulo ou se o bônus for negativo
     */
    private static int[] applyBonusToScores(int[] scores, int bonus) {
        validateInputs(scores, bonus);

        int[] updatedScores = scores;

        for (int i = 0; i < updatedScores.length; i++) {
            updatedScores[i] += bonus;
        }

        return updatedScores;
    }

    /**
     * Aplica o bônus sobre a pontuação máxima individual.
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
