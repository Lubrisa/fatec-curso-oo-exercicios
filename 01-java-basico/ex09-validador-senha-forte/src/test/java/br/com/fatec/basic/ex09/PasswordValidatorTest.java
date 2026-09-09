package br.com.fatec.basic.ex09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ex09 — Testes de Validação de Senhas Fortes (PasswordValidator)")
class PasswordValidatorTest {

    @Nested
    @DisplayName("1. Tratamento Defensivo de Entradas Nulas e Vazias")
    class NullAndEmptyInputTests {

        @ParameterizedTest(name = "Entrada nula ou vazia: \"{0}\"")
        @NullAndEmptySource
        @DisplayName("Deve retornar relatório inválido com força FRACA para null ou string vazia sem lançar exceção")
        void shouldHandleNullAndEmptyGracefully(String input) {
            PasswordValidationResult result = PasswordValidator.validate(input);

            assertThat(result).isNotNull();
            assertThat(result.isValid()).isFalse();
            assertThat(result.hasMinLength()).isFalse();
            assertThat(result.hasUpperCase()).isFalse();
            assertThat(result.hasLowerCase()).isFalse();
            assertThat(result.hasDigit()).isFalse();
            assertThat(result.hasSpecialChar()).isFalse();
            assertThat(result.hasNoWhitespace()).isFalse();
            assertThat(result.strength()).isEqualTo(PasswordStrength.FRACA);

            assertThat(PasswordValidator.isValid(input)).isFalse();
            assertThat(PasswordValidator.estimateStrength(input)).isEqualTo(PasswordStrength.FRACA);
        }
    }

    @Nested
    @DisplayName("2. Validação Individual de Requisitos")
    class IndividualRequirementsTests {

        @Test
        @DisplayName("Deve reprovar senha com comprimento inferior a 8 caracteres")
        void shouldFailWhenLengthIsLessThan8() {
            PasswordValidationResult result = PasswordValidator.validate("Ab1!xyz"); // 7 caracteres

            assertThat(result.isValid()).isFalse();
            assertThat(result.hasMinLength()).isFalse();
            assertThat(result.hasUpperCase()).isTrue();
            assertThat(result.hasLowerCase()).isTrue();
            assertThat(result.hasDigit()).isTrue();
            assertThat(result.hasSpecialChar()).isTrue();
            assertThat(result.hasNoWhitespace()).isTrue();
        }

        @Test
        @DisplayName("Deve reprovar senha sem letra maiúscula")
        void shouldFailWhenMissingUpperCase() {
            PasswordValidationResult result = PasswordValidator.validate("ab1!cdefgh");

            assertThat(result.isValid()).isFalse();
            assertThat(result.hasUpperCase()).isFalse();
            assertThat(result.hasLowerCase()).isTrue();
            assertThat(result.hasDigit()).isTrue();
            assertThat(result.hasSpecialChar()).isTrue();
        }

        @Test
        @DisplayName("Deve reprovar senha sem letra minúscula")
        void shouldFailWhenMissingLowerCase() {
            PasswordValidationResult result = PasswordValidator.validate("AB1!CDEFGH");

            assertThat(result.isValid()).isFalse();
            assertThat(result.hasLowerCase()).isFalse();
            assertThat(result.hasUpperCase()).isTrue();
            assertThat(result.hasDigit()).isTrue();
            assertThat(result.hasSpecialChar()).isTrue();
        }

        @Test
        @DisplayName("Deve reprovar senha sem dígito numérico")
        void shouldFailWhenMissingDigit() {
            PasswordValidationResult result = PasswordValidator.validate("Ab!cdefghij");

            assertThat(result.isValid()).isFalse();
            assertThat(result.hasDigit()).isFalse();
            assertThat(result.hasUpperCase()).isTrue();
            assertThat(result.hasLowerCase()).isTrue();
            assertThat(result.hasSpecialChar()).isTrue();
        }

        @Test
        @DisplayName("Deve reprovar senha sem caractere especial")
        void shouldFailWhenMissingSpecialCharacter() {
            PasswordValidationResult result = PasswordValidator.validate("Ab1cdefghij");

            assertThat(result.isValid()).isFalse();
            assertThat(result.hasSpecialChar()).isFalse();
            assertThat(result.hasUpperCase()).isTrue();
            assertThat(result.hasLowerCase()).isTrue();
            assertThat(result.hasDigit()).isTrue();
        }

        @ParameterizedTest(name = "Senha com espaço: \"{0}\"")
        @ValueSource(strings = {
                "Ab1! cdef",
                " Ab1!cdef",
                "Ab1!cdef ",
                "Ab1!\tcdef",
                "Ab1!\ncdef"
        })
        @DisplayName("Deve reprovar qualquer senha que contenha espaços em branco ou tabulações")
        void shouldFailWhenPasswordContainsWhitespace(String whitespacePassword) {
            PasswordValidationResult result = PasswordValidator.validate(whitespacePassword);

            assertThat(result.isValid()).isFalse();
            assertThat(result.hasNoWhitespace()).isFalse();
            assertThat(result.strength()).isEqualTo(PasswordStrength.FRACA);
        }

        @ParameterizedTest(name = "Caractere especial aceito: \"{0}\"")
        @ValueSource(strings = {
                "Senha!123", "Senha@123", "Senha#123", "Senha$123", "Senha%123",
                "Senha&123", "Senha*123", "Senha-123", "Senha_123", "Senha.123"
        })
        @DisplayName("Deve reconhecer os diversos caracteres especiais permitidos pela política")
        void shouldRecognizeVariousSpecialCharacters(String validPassword) {
            PasswordValidationResult result = PasswordValidator.validate(validPassword);

            assertThat(result.hasSpecialChar()).isTrue();
            assertThat(result.isValid()).isTrue();
        }
    }

    @Nested
    @DisplayName("3. Classificação de Força (PasswordStrength)")
    class StrengthClassificationTests {

        @ParameterizedTest(name = "Senha classificada como FRACA: \"{0}\"")
        @ValueSource(strings = {
                "12345",              // Curta
                "abcdefgh",           // Só minúsculas (1 critério atendido)
                "Abcdefgh",           // Maiúscula + minúscula (2 critérios)
                "Senha Com Espaço 1!" // Contém espaços
        })
        @DisplayName("Deve classificar como FRACA quando não atinge requisitos básicos ou contém espaços")
        void shouldClassifyAsFraca(String weakPassword) {
            assertThat(PasswordValidator.estimateStrength(weakPassword))
                    .isEqualTo(PasswordStrength.FRACA);
        }

        @ParameterizedTest(name = "Senha classificada como MEDIA: \"{0}\"")
        @ValueSource(strings = {
                "Ab1cdefgh",  // Sem especial (atende tamanho, maiúscula, minúscula, dígito)
                "Ab!cdefgh",  // Sem dígito (atende tamanho, maiúscula, minúscula, especial)
                "AB1!CDEFGH", // Sem minúscula (atende tamanho, maiúscula, dígito, especial)
                "ab1!cdefgh"  // Sem maiúscula (atende tamanho, minúscula, dígito, especial)
        })
        @DisplayName("Deve classificar como MEDIA quando atende a exatamente 4 requisitos e não tem espaços")
        void shouldClassifyAsMedia(String mediumPassword) {
            assertThat(PasswordValidator.estimateStrength(mediumPassword))
                    .isEqualTo(PasswordStrength.MEDIA);
        }

        @ParameterizedTest(name = "Senha classificada como FORTE: \"{0}\"")
        @ValueSource(strings = {
                "Senha@123",
                "P@ssw0rd",
                "Fatec#2026",
                "Dev!Code9"
        })
        @DisplayName("Deve classificar como FORTE quando atende a todos os 5 critérios com tamanho regular")
        void shouldClassifyAsForte(String strongPassword) {
            assertThat(PasswordValidator.estimateStrength(strongPassword))
                    .isEqualTo(PasswordStrength.FORTE);
            assertThat(PasswordValidator.isValid(strongPassword)).isTrue();
        }

        @ParameterizedTest(name = "Senha classificada como MUITO_FORTE: \"{0}\"")
        @ValueSource(strings = {
                "S3gur@nc@#2026",   // 14 caracteres, 5 números, 3 especiais
                "P@ssw0rd!2026#X",  // 15 caracteres, múltiplos especiais e números
                "Fatec_2026!Java#"  // 16 caracteres, 4 números, 3 especiais
        })
        @DisplayName("Deve classificar como MUITO_FORTE para senhas >= 12 chars com múltiplos dígitos e especiais")
        void shouldClassifyAsMuitoForte(String veryStrongPassword) {
            assertThat(PasswordValidator.estimateStrength(veryStrongPassword))
                    .isEqualTo(PasswordStrength.MUITO_FORTE);
            assertThat(PasswordValidator.isValid(veryStrongPassword)).isTrue();
        }
    }

    @Nested
    @DisplayName("4. Estrutura de Classe Utilitária")
    class UtilityClassDesignTests {

        @Test
        @DisplayName("O construtor deve ser privado e inacessível via instanciação direta")
        void utilityClassConstructorShouldBePrivate() throws NoSuchMethodException {
            Constructor<PasswordValidator> constructor =
                    PasswordValidator.class.getDeclaredConstructor();

            assertThat(constructor.canAccess(null)).isFalse();

            constructor.setAccessible(true);
            try {
                PasswordValidator instance = constructor.newInstance();
                assertThat(instance).isNotNull();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                // Acesso via reflexão verificado
            }
        }
    }
}
