package br.com.fatec.basic.ex09;

/**
 * Implementação de referência para auditoria e validação de requisitos de segurança em senhas.
 */
public final class PasswordValidator {

    public static final int MIN_LENGTH = 8;
    public static final int VERY_STRONG_MIN_LENGTH = 12;
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private PasswordValidator() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Realiza a auditoria completa de uma senha inspecionando cada caractere individualmente.
     *
     * @param password credencial em texto claro (pode ser nula ou vazia)
     * @return relatório detalhado PasswordValidationResult com status individual de conformidade
     */
    public static PasswordValidationResult validate(String password) {
        if (password == null || password.isEmpty()) {
            return new PasswordValidationResult(
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    PasswordStrength.FRACA
            );
        }

        boolean hasWhitespace = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        int digitCount = 0;
        int specialCharCount = 0;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isWhitespace(c)) {
                hasWhitespace = true;
            }
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
                digitCount++;
            }
            if (SPECIAL_CHARACTERS.indexOf(c) != -1) {
                hasSpecialChar = true;
                specialCharCount++;
            }
        }

        boolean hasMinLength = password.length() >= MIN_LENGTH;
        boolean hasNoWhitespace = !hasWhitespace;

        boolean isValid = hasMinLength
                && hasUpperCase
                && hasLowerCase
                && hasDigit
                && hasSpecialChar
                && hasNoWhitespace;

        int positiveCriteriaMet = 0;
        if (hasMinLength) positiveCriteriaMet++;
        if (hasUpperCase) positiveCriteriaMet++;
        if (hasLowerCase) positiveCriteriaMet++;
        if (hasDigit) positiveCriteriaMet++;
        if (hasSpecialChar) positiveCriteriaMet++;

        PasswordStrength strength;
        if (!hasNoWhitespace || positiveCriteriaMet < 4 || !hasMinLength) {
            strength = PasswordStrength.FRACA;
        } else if (positiveCriteriaMet == 4) {
            strength = PasswordStrength.MEDIA;
        } else {
            // positiveCriteriaMet == 5 (todos os requisitos atendidos)
            if (password.length() >= VERY_STRONG_MIN_LENGTH && digitCount >= 2 && specialCharCount >= 2) {
                strength = PasswordStrength.MUITO_FORTE;
            } else {
                strength = PasswordStrength.FORTE;
            }
        }

        return new PasswordValidationResult(
                isValid,
                hasMinLength,
                hasUpperCase,
                hasLowerCase,
                hasDigit,
                hasSpecialChar,
                hasNoWhitespace,
                strength
        );
    }

    /**
     * Método de conveniência que indica se a senha atende a todos os critérios de aceitação.
     *
     * @param password credencial a ser testada
     * @return true se a senha atende a todos os requisitos, false caso contrário
     */
    public static boolean isValid(String password) {
        return validate(password).isValid();
    }

    /**
     * Método de conveniência que calcula o nível de força da senha.
     *
     * @param password credencial a ser avaliada
     * @return enum PasswordStrength correspondente
     */
    public static PasswordStrength estimateStrength(String password) {
        return validate(password).strength();
    }
}
