package br.com.fatec.basic.ex07;

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

@DisplayName("ex07 — Testes de Verificação de Números Primos (PrimeNumberChecker)")
class PrimeNumberCheckerTest {

    @Nested
    @DisplayName("1. Verificação de Primalidade (isPrime)")
    class IsPrimeTests {

        @ParameterizedTest(name = "Número não-primo: {0}")
        @ValueSource(longs = {-10, -1, 0, 1})
        @DisplayName("Deve retornar false para números menores ou iguais a 1")
        void shouldReturnFalseForNumbersLessOrEqualToOne(long n) {
            assertThat(PrimeNumberChecker.isPrime(n)).isFalse();
        }

        @Test
        @DisplayName("Deve retornar true para o número 2 (único primo par)")
        void shouldReturnTrueForTwo() {
            assertThat(PrimeNumberChecker.isPrime(2)).isTrue();
        }

        @ParameterizedTest(name = "Número par não-primo: {0}")
        @ValueSource(longs = {4, 6, 8, 10, 12, 50, 100, 1_000_000})
        @DisplayName("Deve retornar false para números pares maiores que 2")
        void shouldReturnFalseForEvenNumbersGreaterThanTwo(long n) {
            assertThat(PrimeNumberChecker.isPrime(n)).isFalse();
        }

        @ParameterizedTest(name = "Primo conhecido: {0}")
        @ValueSource(longs = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47})
        @DisplayName("Deve retornar true para os primeiros números primos ímpares")
        void shouldReturnTrueForKnownSmallPrimes(long n) {
            assertThat(PrimeNumberChecker.isPrime(n)).isTrue();
        }

        @ParameterizedTest(name = "Composto ímpar: {0}")
        @ValueSource(longs = {9, 15, 21, 25, 27, 33, 35, 49, 77, 81, 121, 143, 169})
        @DisplayName("Deve retornar false para números ímpares compostos (incluindo quadrados perfeitos)")
        void shouldReturnFalseForOddCompositeNumbers(long n) {
            assertThat(PrimeNumberChecker.isPrime(n)).isFalse();
        }

        @ParameterizedTest(name = "Primo grande: {0}")
        @ValueSource(longs = {104_729L, 1_000_003L, 10_000_019L})
        @DisplayName("Deve validar números primos grandes com rapidez comprovando complexidade O(sqrt(N))")
        void shouldReturnTrueForLargePrimes(long largePrime) {
            assertThat(PrimeNumberChecker.isPrime(largePrime)).isTrue();
        }

        @ParameterizedTest(name = "Composto grande: {0}")
        @ValueSource(longs = {1_000_001L, 10_000_015L})
        @DisplayName("Deve retornar false para números compostos grandes")
        void shouldReturnFalseForLargeCompositeNumbers(long largeComposite) {
            assertThat(PrimeNumberChecker.isPrime(largeComposite)).isFalse();
        }
    }

    @Nested
    @DisplayName("2. Busca do Próximo Primo (nextPrime)")
    class NextPrimeTests {

        @ParameterizedTest(name = "nextPrime({0}) deve ser {1}")
        @CsvSource({
                "-5, 2",
                "0, 2",
                "1, 2",
                "2, 3",
                "3, 5",
                "4, 5",
                "10, 11",
                "11, 13",
                "19, 23",
                "97, 101"
        })
        @DisplayName("Deve localizar corretamente o menor primo estritamente maior que n")
        void shouldFindNextPrimeCorrectly(long n, long expectedNextPrime) {
            assertThat(PrimeNumberChecker.nextPrime(n)).isEqualTo(expectedNextPrime);
        }
    }

    @Nested
    @DisplayName("3. Contagem de Primos (countPrimes)")
    class CountPrimesTests {

        @Test
        @DisplayName("Deve contar 4 primos no intervalo [0, 10] (2, 3, 5, 7)")
        void shouldCountPrimesFrom0To10() {
            int count = PrimeNumberChecker.countPrimes(0, 10);
            assertThat(count).isEqualTo(4);
        }

        @Test
        @DisplayName("Deve contar 4 primos no intervalo [10, 20] (11, 13, 17, 19)")
        void shouldCountPrimesFrom10To20() {
            int count = PrimeNumberChecker.countPrimes(10, 20);
            assertThat(count).isEqualTo(4);
        }

        @Test
        @DisplayName("Deve contar 0 primos quando não houver primos no intervalo [24, 28]")
        void shouldCountZeroWhenNoPrimesExistInRange() {
            int count = PrimeNumberChecker.countPrimes(24, 28);
            assertThat(count).isZero();
        }

        @Test
        @DisplayName("Deve contar 1 primo para intervalo unitário com número primo [17, 17]")
        void shouldCountOneForUnitRangeWithPrime() {
            int count = PrimeNumberChecker.countPrimes(17, 17);
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("Deve contar 0 primos para intervalo unitário com número composto [18, 18]")
        void shouldCountZeroForUnitRangeWithComposite() {
            int count = PrimeNumberChecker.countPrimes(18, 18);
            assertThat(count).isZero();
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException se start for negativo")
        void shouldThrowExceptionIfStartIsNegative() {
            assertThatThrownBy(() -> PrimeNumberChecker.countPrimes(-1, 10))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O início do intervalo não pode ser negativo");
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException se start for maior que end")
        void shouldThrowExceptionIfStartIsGreaterThanEnd() {
            assertThatThrownBy(() -> PrimeNumberChecker.countPrimes(20, 10))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O início do intervalo não pode ser maior que o fim");
        }
    }

    @Nested
    @DisplayName("4. Localização de Primos em Intervalo (findPrimesInRange)")
    class FindPrimesInRangeTests {

        @Test
        @DisplayName("Deve encontrar os primos entre 1 e 20")
        void shouldFindPrimesBetween1And20() {
            long[] primes = PrimeNumberChecker.findPrimesInRange(1, 20);

            assertThat(primes)
                    .isNotNull()
                    .hasSize(8)
                    .containsExactly(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L);
        }

        @Test
        @DisplayName("Deve encontrar os primos entre 10 e 25")
        void shouldFindPrimesBetween10And25() {
            long[] primes = PrimeNumberChecker.findPrimesInRange(10, 25);

            assertThat(primes)
                    .hasSize(5)
                    .containsExactly(11L, 13L, 17L, 19L, 23L);
        }

        @Test
        @DisplayName("Deve retornar array vazio quando não houver primos no intervalo [24, 28]")
        void shouldReturnEmptyArrayWhenNoPrimesExist() {
            long[] primes = PrimeNumberChecker.findPrimesInRange(24, 28);

            assertThat(primes)
                    .isNotNull()
                    .isEmpty();
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException se start for negativo")
        void shouldThrowExceptionIfStartIsNegative() {
            assertThatThrownBy(() -> PrimeNumberChecker.findPrimesInRange(-5, 10))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O início do intervalo não pode ser negativo");
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException se start for maior que end")
        void shouldThrowExceptionIfStartIsGreaterThanEnd() {
            assertThatThrownBy(() -> PrimeNumberChecker.findPrimesInRange(30, 20))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O início do intervalo não pode ser maior que o fim");
        }
    }

    @Nested
    @DisplayName("5. Estrutura de Classe Utilitária")
    class UtilityClassDesignTests {

        @Test
        @DisplayName("O construtor deve ser privado e inacessível via instanciação direta")
        void utilityClassConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<PrimeNumberChecker> constructor =
                    PrimeNumberChecker.class.getDeclaredConstructor();

            assertThat(constructor.canAccess(null)).isFalse();

            constructor.setAccessible(true);
            try {
                PrimeNumberChecker instance = constructor.newInstance();
                assertThat(instance).isNotNull();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                // Acesso via reflexão verificado
            }
        }
    }
}
