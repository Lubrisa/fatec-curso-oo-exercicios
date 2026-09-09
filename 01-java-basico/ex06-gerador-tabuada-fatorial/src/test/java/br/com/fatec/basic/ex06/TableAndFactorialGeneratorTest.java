package br.com.fatec.basic.ex06;

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

@DisplayName("ex06 — Testes de Tabuada e Fatorial (TableAndFactorialGenerator)")
class TableAndFactorialGeneratorTest {

    @Nested
    @DisplayName("1. Geração de Tabuada (generateMultiplicationTable)")
    class MultiplicationTableTests {

        @Test
        @DisplayName("Deve gerar a tabuada padrão do 7 de 1 até 5")
        void shouldGenerateTableForBase7UpTo5() {
            int[] result = TableAndFactorialGenerator.generateMultiplicationTable(7, 5);

            assertThat(result)
                    .isNotNull()
                    .hasSize(5)
                    .containsExactly(7, 14, 21, 28, 35);
        }

        @Test
        @DisplayName("Deve gerar a tabuada clássica do 5 de 1 até 10")
        void shouldGenerateTableForBase5UpTo10() {
            int[] result = TableAndFactorialGenerator.generateMultiplicationTable(5, 10);

            assertThat(result)
                    .isNotNull()
                    .hasSize(10)
                    .containsExactly(5, 10, 15, 20, 25, 30, 35, 40, 45, 50);
        }

        @Test
        @DisplayName("Deve suportar tabuada com base zero")
        void shouldSupportBaseZero() {
            int[] result = TableAndFactorialGenerator.generateMultiplicationTable(0, 4);

            assertThat(result)
                    .hasSize(4)
                    .containsExactly(0, 0, 0, 0);
        }

        @Test
        @DisplayName("Deve suportar tabuada com base negativa (-3 de 1 até 4)")
        void shouldSupportNegativeBase() {
            int[] result = TableAndFactorialGenerator.generateMultiplicationTable(-3, 4);

            assertThat(result)
                    .hasSize(4)
                    .containsExactly(-3, -6, -9, -12);
        }

        @Test
        @DisplayName("Deve suportar limite unitário upTo = 1")
        void shouldSupportUnitLimit() {
            int[] result = TableAndFactorialGenerator.generateMultiplicationTable(9, 1);

            assertThat(result)
                    .hasSize(1)
                    .containsExactly(9);
        }

        @ParameterizedTest(name = "upTo inválido: {0}")
        @ValueSource(ints = {0, -1, -5, -100})
        @DisplayName("Deve lançar IllegalArgumentException para upTo menor que 1")
        void shouldThrowExceptionForInvalidUpTo(int invalidUpTo) {
            assertThatThrownBy(() -> TableAndFactorialGenerator.generateMultiplicationTable(5, invalidUpTo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O limite superior da tabuada deve ser maior ou igual a 1");
        }
    }

    @Nested
    @DisplayName("2. Cálculo de Fatorial (calculateFactorial)")
    class FactorialTests {

        @ParameterizedTest(name = "{0}! deve ser igual a {1}")
        @CsvSource({
                "0, 1",
                "1, 1",
                "2, 2",
                "3, 6",
                "4, 24",
                "5, 120",
                "6, 720",
                "7, 5040",
                "8, 40320",
                "9, 362880",
                "10, 3628800",
                "12, 479001600"
        })
        @DisplayName("Deve calcular corretamente os fatoriais de valores conhecidos")
        void shouldCalculateKnownFactorials(int n, long expected) {
            long result = TableAndFactorialGenerator.calculateFactorial(n);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("Deve calcular corretamente 13! comprovando suporte além do limite de int de 32 bits")
        void shouldCalculate13FactorialExceedingIntCapacity() {
            // 13! = 6.227.020.800 (Integer.MAX_VALUE é 2.147.483.647)
            long result = TableAndFactorialGenerator.calculateFactorial(13);
            assertThat(result).isEqualTo(6_227_020_800L);
        }

        @Test
        @DisplayName("Deve calcular corretamente o limite máximo representável por long (20!)")
        void shouldCalculateMaxSafeFactorial20() {
            // 20! = 2.432.902.008.176.640.000
            long result = TableAndFactorialGenerator.calculateFactorial(20);
            assertThat(result).isEqualTo(2_432_902_008_176_640_000L);
        }

        @ParameterizedTest(name = "Entrada negativa: {0}")
        @ValueSource(ints = {-1, -2, -5, -20})
        @DisplayName("Deve lançar IllegalArgumentException para entradas negativas")
        void shouldThrowExceptionForNegativeInput(int negativeN) {
            assertThatThrownBy(() -> TableAndFactorialGenerator.calculateFactorial(negativeN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Não é possível calcular fatorial de número negativo");
        }

        @ParameterizedTest(name = "Entrada com overflow: {0}")
        @ValueSource(ints = {21, 22, 25, 50, 100})
        @DisplayName("Deve lançar IllegalArgumentException para entradas maiores que 20 prevenindo overflow silencioso")
        void shouldThrowExceptionForOverflowInput(int overflowN) {
            assertThatThrownBy(() -> TableAndFactorialGenerator.calculateFactorial(overflowN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O valor excede o limite representável pelo tipo long (máximo 20)");
        }
    }

    @Nested
    @DisplayName("3. Fatoriais em Intervalo (calculateFactorialsInRange)")
    class FactorialsInRangeTests {

        @Test
        @DisplayName("Deve calcular fatoriais de 3 até 6")
        void shouldCalculateFactorialsFrom3To6() {
            long[] result = TableAndFactorialGenerator.calculateFactorialsInRange(3, 6);

            assertThat(result)
                    .hasSize(4)
                    .containsExactly(6L, 24L, 120L, 720L);
        }

        @Test
        @DisplayName("Deve calcular fatoriais de 0 até 3")
        void shouldCalculateFactorialsFrom0To3() {
            long[] result = TableAndFactorialGenerator.calculateFactorialsInRange(0, 3);

            assertThat(result)
                    .hasSize(4)
                    .containsExactly(1L, 1L, 2L, 6L);
        }

        @Test
        @DisplayName("Deve suportar intervalo de elemento único (start == end)")
        void shouldSupportSingleElementRange() {
            long[] result = TableAndFactorialGenerator.calculateFactorialsInRange(5, 5);

            assertThat(result)
                    .hasSize(1)
                    .containsExactly(120L);
        }

        @Test
        @DisplayName("Deve lançar exceção se start for negativo")
        void shouldThrowExceptionIfStartIsNegative() {
            assertThatThrownBy(() -> TableAndFactorialGenerator.calculateFactorialsInRange(-1, 5))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O valor inicial não pode ser negativo");
        }

        @Test
        @DisplayName("Deve lançar exceção se end exceder 20")
        void shouldThrowExceptionIfEndExceedsMaxSafeLimit() {
            assertThatThrownBy(() -> TableAndFactorialGenerator.calculateFactorialsInRange(18, 22))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O valor final excede o limite representável pelo tipo long (máximo 20)");
        }

        @Test
        @DisplayName("Deve lançar exceção se start for maior que end")
        void shouldThrowExceptionIfStartIsGreaterThanEnd() {
            assertThatThrownBy(() -> TableAndFactorialGenerator.calculateFactorialsInRange(7, 4))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("O valor inicial não pode ser maior que o valor final");
        }
    }

    @Nested
    @DisplayName("4. Estrutura de Classe Utilitária")
    class UtilityClassDesignTests {

        @Test
        @DisplayName("O construtor deve ser privado e inacessível via instanciação direta")
        void utilityClassConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<TableAndFactorialGenerator> constructor =
                    TableAndFactorialGenerator.class.getDeclaredConstructor();

            assertThat(constructor.canAccess(null)).isFalse();

            constructor.setAccessible(true);
            try {
                TableAndFactorialGenerator instance = constructor.newInstance();
                assertThat(instance).isNotNull();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                // Acesso via reflexão testado
            }
        }
    }
}
