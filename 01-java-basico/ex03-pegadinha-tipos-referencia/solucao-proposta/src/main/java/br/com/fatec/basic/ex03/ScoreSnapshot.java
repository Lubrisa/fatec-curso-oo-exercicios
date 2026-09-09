package br.com.fatec.basic.ex03;

/**
 * Registro imutável que consolida o snapshot das pontuações antes e depois da bonificação.
 *
 * @param originalScores cópia das pontuações originais da rodada
 * @param updatedScores novas pontuações calculadas com o bônus aplicado
 * @param originalHighScore pontuação máxima individual original
 * @param updatedHighScore pontuação máxima individual com o bônus aplicado
 */
public record ScoreSnapshot(
        int[] originalScores,
        int[] updatedScores,
        int originalHighScore,
        int updatedHighScore
) {}
