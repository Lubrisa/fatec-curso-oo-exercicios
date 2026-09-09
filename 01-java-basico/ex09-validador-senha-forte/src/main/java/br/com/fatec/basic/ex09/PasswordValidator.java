package br.com.fatec.basic.ex09;

/**
 * Utilitário para auditoria e validação de requisitos de segurança em senhas de usuários.
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
        // TODO: Tratar se password == null ou password.isEmpty() retornando resultado com todas as flags false e força FRACA
        // TODO: Inspecionar o comprimento (password.length() >= MIN_LENGTH)
        // TODO: Percorrer cada caractere da senha com for (int i = 0; i < password.length(); i++)
        // TODO: Usar Character.isWhitespace para detectar espaços em branco
        // TODO: Usar Character.isUpperCase para detectar letras maiúsculas
        // TODO: Usar Character.isLowerCase para detectar letras minúsculas
        // TODO: Usar Character.isDigit para detectar dígitos e contar quantidade
        // TODO: Verificar se SPECIAL_CHARACTERS.indexOf(c) != -1 para detectar especiais e contar quantidade
        // TODO: Calcular a força da senha (FRACA, MEDIA, FORTE ou MUITO_FORTE)
        // TODO: Senha válida requer hasMinLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar && hasNoWhitespace
        // TODO: Retornar nova instância de PasswordValidationResult
        throw new UnsupportedOperationException("Método validate ainda não implementado");
    }

    /**
     * Método de conveniência que indica se a senha atende a todos os critérios de aceitação.
     *
     * @param password credencial a ser testada
     * @return true se a senha atende a todos os requisitos, false caso contrário
     */
    public static boolean isValid(String password) {
        // TODO: Retornar validate(password).isValid()
        throw new UnsupportedOperationException("Método isValid ainda não implementado");
    }

    /**
     * Método de conveniência que calcula o nível de força da senha.
     *
     * @param password credencial a ser avaliada
     * @return enum PasswordStrength correspondente
     */
    public static PasswordStrength estimateStrength(String password) {
        // TODO: Retornar validate(password).strength()
        throw new UnsupportedOperationException("Método estimateStrength ainda não implementado");
    }
}
