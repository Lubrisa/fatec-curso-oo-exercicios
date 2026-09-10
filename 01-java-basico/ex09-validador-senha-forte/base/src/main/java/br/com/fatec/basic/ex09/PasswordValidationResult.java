package br.com.fatec.basic.ex09;

/**
 * [ESTRUTURA FORNECIDA PRONTA — NÃO É NECESSÁRIO MODIFICAR ESTE ARQUIVO]
 *
 * Relatório imutável contendo o diagnóstico detalhado da validação de uma senha.
 * Utilizado como tipo de retorno do método PasswordValidator.validate(...).
 *
 * @param isValid          indica se a senha atendeu a todos os critérios obrigatórios
 * @param hasMinLength     indica se possui no mínimo 8 caracteres
 * @param hasUpperCase     indica se possui pelo menos uma letra maiúscula
 * @param hasLowerCase     indica se possui pelo menos uma letra minúscula
 * @param hasDigit         indica se possui pelo menos um dígito numérico
 * @param hasSpecialChar   indica se possui pelo menos um caractere especial permitido
 * @param hasNoWhitespace  indica se a senha não contém espaços em branco
 * @param strength         nível de segurança estimado para a credencial
 */
public record PasswordValidationResult(
        boolean isValid,
        boolean hasMinLength,
        boolean hasUpperCase,
        boolean hasLowerCase,
        boolean hasDigit,
        boolean hasSpecialChar,
        boolean hasNoWhitespace,
        PasswordStrength strength
) {
}
