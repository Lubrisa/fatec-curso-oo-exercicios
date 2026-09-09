package br.com.fatec.basic.ex08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ex08 — Testes da Sequência de Fibonacci (FibonacciSequenceGenerator)")
class FibonacciSequenceGeneratorTest {

    @Nested
    @DisplayName("1. Geração de Sequência (generateSequence)")
    class GenerateSequenceTests {

        @Test
        @DisplayName("Deve gerar termo único para count = 1")
        void shouldGenerateSequenceForCountOne() {
            long[] result = FibonacciSequenceGenerator.generateSequence(1);

            assertThat(result)
                    .isNotNull()
                    .hasSize(1)
                    .containsExactly(0L);
        }

        @Test
        @DisplayName("Deve gerar os dois primeiros termos para count = 2")
        void shouldGenerateSequenceForCountTwo() {
            long[] result = FibonacciSequenceGenerator.generateSequence(2);

            assertThat(result)
                    .hasSize(2)
                    .containsExactly(0L, 1L);
        }

        @Test
        @DisplayName("Deve gerar os 5 primeiros termos [0, 1, 1, 2, 3]")
        void shouldGenerateFirst5Terms() {
            long[] result = FibonacciSequenceGenerator.generateSequence(5);

            assertThat(result)
                    .hasSize(5)
                    .containsExactly(0L, 1L, 1L, 2L, 3L);
        }

        @Test
        @DisplayName("Deve gerar os 10 primeiros termos [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]")
        void shouldGenerateFirst10Terms() {
            long[] result = FibonacciSequenceGenerator.generateSequence(10);

            assertThat(result)
                    .hasSize(10)
                    .containsExactly(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L);
        }

        @Test
        @DisplayName("Deve suportar geração no limite máximo seguro (93 termos)")
        void shouldSupportMaxSafeCount93() {
            long[] result = FibonacciSequenceGenerator.generateSequence(93);

            assertThat(result).hasSize(93);
            assertThat(result[0]).isEqualTo(0L);
            assertThat(result[1]).isEqualTo(1L);
            assertThat(result[92]).isEqualTo(7_540_113_804_746_346_429L);
        }

        @ParameterizedTest(name = "count inválido: {0}")
        @ValueSource(ints = {0, -1, -5, -100})
        @DisplayName("Deve lançar IllegalArgumentException para count menor que 1")
        void shouldThrowExceptionForCountLessThanOne(int invalidCount) {
            assertThatThrownBy(() -> FibonacciSequenceGenerator.generateSequence(invalidCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("A quantidade de termos deve ser maior ou igual a 1");
        }

        @ParameterizedTest(name = "count com overflow: {0}")
        @ValueSource(ints = {94, 95, 100, 200})
        @DisplayName("Deve lançar IllegalArgumentException para count maior que 93 prevenindo overflow")
        void shouldThrowExceptionForCountGreaterThan93(int overflowCount) {
            assertThatThrownBy(() -> FibonacciSequenceGenerator.generateSequence(overflowCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("A quantidade de termos excede o limite representável pelo tipo long (máximo 93 termos, até F(92))");
        }
    }

    @Nested
    @DisplayName("2. Consulta do N-ésimo Termo (getNthTerm)")
    class GetNthTermTests {

        @ParameterizedTest(name = "F({0}) deve ser igual a {1}")
        @CsvSource({
                "0, 0",
                "1, 1",
                "2, 1",
                "3, 2",
                "4, 3",
                "5, 5",
                "6, 8",
                "7, 13",
                "8, 21",
                "9, 34",
                "10, 55",
                "11, 89",
                "12, 144",
                "20, 6765",
                "50, 12586269025",
                "92, 7540113804746346429"
        })
        @DisplayName("Deve calcular corretamente os termos conhecidos da sucessão")
        void shouldReturnCorrectNthTerms(int n, long expected) {
            long result = FibonacciSequenceGenerator.getNthTerm(n);
            assertThat(result).isEqualTo(expected);
        }

        @ParameterizedTest(name = "Índice negativo: {0}")
        @ValueSource(ints = {-1, -2, -10})
        @DisplayName("Deve lançar IllegalArgumentException para índices negativos")
        void shouldThrowExceptionForNegativeIndex(int negativeN) {
            assertThatThrownBy(() -> FibonacciSequenceGenerator.getNthTerm(negativeN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O índice do termo não pode ser negativo");
        }

        @ParameterizedTest(name = "Índice com overflow: {0}")
        @ValueSource(ints = {93, 94, 100, 200})
        @DisplayName("Deve lançar IllegalArgumentException para índices superiores a 92")
        void shouldThrowExceptionForIndexGreaterThan92(int overflowN) {
            assertThatThrownBy(() -> FibonacciSequenceGenerator.getNthTerm(overflowN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O índice excede o limite representável pelo tipo long (máximo 92)");
        }
    }

    @Nested
    @DisplayName("3. Verificação de Pertencimento (isFibonacciNumber)")
    class IsFibonacciNumberTests {

        @ParameterizedTest(name = "Número de Fibonacci: {0}")
        @ValueSource(longs = {
                0L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L, 144L, 6765L, 7_540_113_804_746_346_429L
        })
        @DisplayName("Deve retornar true para números autênticos da sequência de Fibonacci")
        void shouldReturnTrueForValidFibonacciNumbers(long validFibonacci) {
            assertThat(FibonacciSequenceGenerator.isFibonacciNumber(validFibonacci)).isTrue();
        }

        @ParameterizedTest(name = "Número não-Fibonacci: {0}")
        @ValueSource(longs = {
                4L, 6L, 7L, 9L, 10L, 11L, 12L, 14L, 20L, 22L, 100L, 1000L, 7_540_113_804_746_346_430L
        })
        @DisplayName("Deve retornar false para inteiros positivos que não pertencem à sequência")
        void shouldReturnFalseForNonFibonacciNumbers(long nonFibonacci) {
            assertThat(FibonacciSequenceGenerator.isFibonacciNumber(nonFibonacci)).isFalse();
        }

        @ParameterizedTest(name = "Número negativo: {0}")
        @ValueSource(longs = {-1L, -2L, -5L, -13L, -100L})
        @DisplayName("Deve retornar false para números negativos")
        void shouldReturnFalseForNegativeNumbers(long negativeNumber) {
            assertThat(FibonacciSequenceGenerator.isFibonacciNumber(negativeNumber)).isFalse();
        }
    }

    @Nested
    @DisplayName("4. Estrutura de Classe Utilitária")
    class UtilityClassDesignTests {

        @Test
        @DisplayName("O construtor deve ser privado e inacessível via instanciação direta")
        void utilityClassConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<FibonacciSequenceGenerator> constructor =
                    FibonacciSequenceGenerator.class.getDeclaredConstructor();

            assertThat(constructor.canAccess(null)).isFalse();

            constructor.setAccessible(true);
            try {
                FibonacciSequenceGenerator instance = constructor.newInstance();
                assertThat(instance).isNotNull();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                // Acesso via reflexão verificado
            }
        }
    }
}
