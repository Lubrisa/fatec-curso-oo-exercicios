package br.com.fatec.basic.ex03;

import java.util.Arrays;

/**
 * Implementação de referência para o exercício de diagnóstico de aliasing de referências.
 */
public final class ScoreSnapshotTracker {

    private static final String ERROR_SCORES_NULL = "O array de pontuações não pode ser nulo";
    private static final String ERROR_BONUS_NEGATIVE = "O bônus não pode ser negativo";

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

        int[] originalScores = Arrays.copyOf(scores, scores.length);
        int[] updatedScores = applyBonusToScores(scores, bonus);
        int updatedHighScore = applyBonusToHighScore(currentHighScore, bonus);

        return new ScoreSnapshot(originalScores, updatedScores, currentHighScore, updatedHighScore);
    }

    /**
     * Aplica uma bonificação a cada pontuação contida no array sem alterar o array original.
     *
     * @param scores array original com as pontuações
     * @param bonus valor do bônus a ser adicionado
     * @return novo array contendo as pontuações atualizadas
     */
    private static int[] applyBonusToScores(int[] scores, int bonus) {
        int[] updatedScores = new int[scores.length];
        for (int i = 0; i < scores.length; i++) {
            updatedScores[i] = scores[i] + bonus;
        }
        return updatedScores;
    }

    /**
     * Aplica o bônus sobre a pontuação máxima individual.
     *
     * @param currentHighScore pontuação máxima atual
     * @param bonus valor do bônus a ser somado
     * @return novo valor com o bônus adicionado
     */
    private static int applyBonusToHighScore(int currentHighScore, int bonus) {
        return currentHighScore + bonus;
    }

    private static void validateInputs(int[] scores, int bonus) {
        if (scores == null) {
            throw new IllegalArgumentException(ERROR_SCORES_NULL);
        }
        if (bonus < 0) {
            throw new IllegalArgumentException(ERROR_BONUS_NEGATIVE);
        }
    }
}
