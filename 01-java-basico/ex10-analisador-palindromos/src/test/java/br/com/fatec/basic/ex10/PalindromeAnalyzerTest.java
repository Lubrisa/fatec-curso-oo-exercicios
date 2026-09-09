package br.com.fatec.basic.ex10;

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

@DisplayName("ex10 — Testes do Analisador de Palíndromos (PalindromeAnalyzer)")
class PalindromeAnalyzerTest {

    private static final String ERROR_NULL_MESSAGE = "O texto informado não pode ser nulo";

    @Nested
    @DisplayName("1. Validação Estrita (isStrictPalindrome)")
    class StrictPalindromeTests {

        @ParameterizedTest(name = "Palíndromo estrito: \"{0}\"")
        @ValueSource(strings = {
                "", "a", "Z", "arara", "radar", "reviver", "osso", "socos", "12321", "aba aba", "!@#" + "#@!"
        })
        @DisplayName("Deve retornar true para palíndromos estritos perfeitos")
        void shouldReturnTrueForStrictPalindromes(String input) {
            assertThat(PalindromeAnalyzer.isStrictPalindrome(input)).isTrue();
        }

        @ParameterizedTest(name = "Não-palíndromo estrito: \"{0}\"")
        @ValueSource(strings = {
                "Arara", "Radar", "java", "fatec", "computador", "aba ", " aba", "arara!"
        })
        @DisplayName("Deve retornar false quando há diferenças de caixa, espaços não espelhados ou pontuações")
        void shouldReturnFalseForNonStrictPalindromes(String input) {
            assertThat(PalindromeAnalyzer.isStrictPalindrome(input)).isFalse();
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para entrada nula")
        void shouldThrowExceptionForNull() {
            assertThatThrownBy(() -> PalindromeAnalyzer.isStrictPalindrome(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_NULL_MESSAGE);
        }
    }

    @Nested
    @DisplayName("2. Sanitização de Texto (sanitize)")
    class SanitizeTests {

        @ParameterizedTest(name = "Texto bruto: \"{0}\" -> Sanitizado: \"{1}\"")
        @CsvSource(delimiter = '|', value = {
                "ônibus | onibus",
                "maçã | maca",
                "Coração | coracao",
                "Olá, Mundo! | olamundo",
                "A   B   C | abc",
                "Ano 2026! | ano2026",
                "---!@#$%^&*()--- | ''",
                "'' | ''"
        })
        @DisplayName("Deve normalizar acentos, converter para minúsculas e reter apenas alfanuméricos")
        void shouldSanitizeTextCorrectly(String input, String expected) {
            assertThat(PalindromeAnalyzer.sanitize(input)).isEqualTo(expected);
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para entrada nula")
        void shouldThrowExceptionForNull() {
            assertThatThrownBy(() -> PalindromeAnalyzer.sanitize(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_NULL_MESSAGE);
        }
    }

    @Nested
    @DisplayName("3. Validação em Linguagem Natural (isPalindrome)")
    class NaturalLanguagePalindromeTests {

        @ParameterizedTest(name = "Frase palindrômica: \"{0}\"")
        @ValueSource(strings = {
                "Socorram-me, subi no ônibus em Marrocos!",
                "A cara rajada da jararaca",
                "Ame o poema",
                "A base do teto desaba",
                "A diva em Argel alegra-me a vida",
                "O lobo ama o bolo",
                "Anotaram a data da maratona",
                "Roma me tem amor",
                "Luz azul",
                "Was it a car or a cat I saw?",
                "Madam, in Eden, I'm Adam.",
                "123-321!",
                "Arara",
                "Radar",
                "",
                "   --- !@# ---   "
        })
        @DisplayName("Deve reconhecer frases clássicas da língua portuguesa e inglesa como palíndromos")
        void shouldRecognizeFamousNaturalLanguagePalindromes(String sentence) {
            assertThat(PalindromeAnalyzer.isPalindrome(sentence)).isTrue();
        }

        @ParameterizedTest(name = "Frase não-palindrômica: \"{0}\"")
        @ValueSource(strings = {
                "Java não é palíndromo",
                "Desenvolvimento Orientado a Objetos",
                "Quase um palíndromo",
                "Engenharia de Software na Fatec",
                "O código compila com sucesso",
                "123456"
        })
        @DisplayName("Deve retornar false para textos convencionais que não formam palíndromos")
        void shouldRejectNonPalindromes(String sentence) {
            assertThat(PalindromeAnalyzer.isPalindrome(sentence)).isFalse();
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para entrada nula")
        void shouldThrowExceptionForNull() {
            assertThatThrownBy(() -> PalindromeAnalyzer.isPalindrome(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_NULL_MESSAGE);
        }
    }

    @Nested
    @DisplayName("4. Estrutura de Classe Utilitária")
    class UtilityClassDesignTests {

        @Test
        @DisplayName("O construtor deve ser privado e inacessível via instanciação direta")
        void utilityClassConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<PalindromeAnalyzer> constructor =
                    PalindromeAnalyzer.class.getDeclaredConstructor();

            assertThat(constructor.canAccess(null)).isFalse();

            constructor.setAccessible(true);
            try {
                PalindromeAnalyzer instance = constructor.newInstance();
                assertThat(instance).isNotNull();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                // Acesso via reflexão verificado
            }
        }
    }
}
