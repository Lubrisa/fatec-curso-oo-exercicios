package br.com.fatec.basic.ex03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ex03 — Testes de Diagnóstico de Aliasing e Tipos Primitivos")
class ScoreSnapshotTrackerTest {

    private static final String ERROR_SCORES_NULL = "O array de pontuações não pode ser nulo";
    private static final String ERROR_BONUS_NEGATIVE = "O bônus não pode ser negativo";

    @Nested
    @DisplayName("Diagnóstico e Prevenção de Aliasing em Arrays (Heap)")
    class AliasingPreventionTests {

        @Test
        @DisplayName("Não deve mutar o array original do chamador ao processar o bônus")
        void shouldNotMutateOriginalScoresArrayWhenApplyingBonus() {
            int[] originalInput = {100, 200, 300};
            int bonus = 50;
            int highScore = 300;

            ScoreSnapshot snapshot = ScoreSnapshotTracker.processRoundBonus(originalInput, bonus, highScore);

            // O array original fornecido pelo chamador DEVE permanecer inalterado
            assertThat(originalInput)
                    .as("O array original do chamador não pode sofrer mutação")
                    .containsExactly(100, 200, 300);

            // O array de pontuações atualizadas deve conter os novos valores calculados
            assertThat(snapshot.updatedScores())
                    .as("As pontuações atualizadas devem conter o bônus somado")
                    .containsExactly(150, 250, 350);

            // O array original preservado no snapshot deve refletir o histórico intacto
            assertThat(snapshot.originalScores())
                    .as("O snapshot deve registrar os valores originais intactos")
                    .containsExactly(100, 200, 300);
        }

        @Test
        @DisplayName("O array updatedScores deve ser um novo objeto no Heap (referência diferente)")
        void shouldProduceDifferentReferenceForUpdatedScores() {
            int[] originalInput = {50, 80, 120};
            ScoreSnapshot snapshot = ScoreSnapshotTracker.processRoundBonus(originalInput, 20, 120);

            assertThat(snapshot.updatedScores())
                    .as("updatedScores deve ser uma nova referência na memória (Heap)")
                    .isNotSameAs(originalInput);
        }

        @Test
        @DisplayName("O array originalScores no snapshot deve ser uma cópia defensiva independente")
        void shouldProduceDefensiveCopyForOriginalScores() {
            int[] originalInput = {10, 20, 30};
            ScoreSnapshot snapshot = ScoreSnapshotTracker.processRoundBonus(originalInput, 5, 30);

            assertThat(snapshot.originalScores())
                    .as("originalScores deve ser uma cópia defensiva para proteger a auditoria")
                    .isNotSameAs(originalInput);

            // Se o chamador alterar seu array local após o snapshot, o snapshot não deve ser corrompido
            originalInput[0] = 999;
            assertThat(snapshot.originalScores()[0])
                    .as("Mutação tardia no array do chamador não pode afetar o snapshot já gerado")
                    .isEqualTo(10);
        }
    }

    @Nested
    @DisplayName("Comportamento de Tipos Primitivos (Cópia por Valor na Stack)")
    class PrimitiveValueSemanticsTests {

        @Test
        @DisplayName("Deve atualizar o recorde individual sem efeitos colaterais de referência")
        void shouldUpdateHighScoreCorrectly() {
            int[] scores = {45, 90, 75};
            int originalHighScore = 90;
            int bonus = 15;

            ScoreSnapshot snapshot = ScoreSnapshotTracker.processRoundBonus(scores, bonus, originalHighScore);

            assertThat(snapshot.originalHighScore())
                    .as("A pontuação original individual deve ser preservada")
                    .isEqualTo(90);

            assertThat(snapshot.updatedHighScore())
                    .as("A pontuação individual atualizada deve receber o bônus")
                    .isEqualTo(105);
        }
    }

    @Nested
    @DisplayName("Casos de Borda")
    class EdgeCaseTests {

        @Test
        @DisplayName("Deve processar array vazio sem falhas e retornando novos arrays vazios")
        void shouldHandleEmptyArray() {
            int[] emptyScores = new int[0];
            ScoreSnapshot snapshot = ScoreSnapshotTracker.processRoundBonus(emptyScores, 10, 0);

            assertThat(snapshot.originalScores()).isEmpty();
            assertThat(snapshot.updatedScores()).isEmpty();
            assertThat(snapshot.updatedScores()).isNotSameAs(emptyScores);
        }

        @Test
        @DisplayName("Deve processar bônus zero mantendo os mesmos valores, mas com novas referências")
        void shouldHandleZeroBonus() {
            int[] scores = {100, 200};
            ScoreSnapshot snapshot = ScoreSnapshotTracker.processRoundBonus(scores, 0, 200);

            assertThat(snapshot.updatedScores()).containsExactly(100, 200);
            assertThat(snapshot.updatedScores()).isNotSameAs(scores);
        }
    }

    @Nested
    @DisplayName("Validações Fail-Fast de Argumentos Inválidos")
    class ValidationTests {

        @Test
        @DisplayName("Deve rejeitar array de pontuações nulo")
        void shouldRejectNullScores() {
            assertThatThrownBy(() -> ScoreSnapshotTracker.processRoundBonus(null, 10, 100))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_SCORES_NULL);
        }

        @ParameterizedTest(name = "Bônus inválido: {0}")
        @ValueSource(ints = {-1, -10, -100})
        @DisplayName("Deve rejeitar bônus com valor negativo")
        void shouldRejectNegativeBonus(int negativeBonus) {
            assertThatThrownBy(() -> ScoreSnapshotTracker.processRoundBonus(new int[]{10, 20}, negativeBonus, 20))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_BONUS_NEGATIVE);
        }
    }
}
