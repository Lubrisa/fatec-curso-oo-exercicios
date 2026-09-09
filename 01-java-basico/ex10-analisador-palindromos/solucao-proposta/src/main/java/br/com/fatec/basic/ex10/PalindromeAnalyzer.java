package br.com.fatec.basic.ex10;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Implementação de referência para análise e validação de palíndromos.
 */
public final class PalindromeAnalyzer {

    private static final String ERROR_NULL_TEXT = "O texto informado não pode ser nulo";

    private PalindromeAnalyzer() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Verifica se o texto é um palíndromo estrito (considerando maiúsculas, espaços e pontuações exatamente como fornecidos).
     *
     * @param text texto a ser verificado
     * @return true se o texto é um palíndromo estrito, false caso contrário
     * @throws IllegalArgumentException se text for nulo
     */
    public static boolean isStrictPalindrome(String text) {
        if (text == null) {
            throw new IllegalArgumentException(ERROR_NULL_TEXT);
        }

        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * Sanitiza o texto para análise: remove acentos/diacríticos, converte para minúsculas e descarta qualquer
     * caractere que não seja letra ou dígito alfanumérico.
     *
     * @param text texto original
     * @return string normalizada contendo apenas letras e números minúsculos sem acentuação
     * @throws IllegalArgumentException se text for nulo
     */
    public static String sanitize(String text) {
        if (text == null) {
            throw new IllegalArgumentException(ERROR_NULL_TEXT);
        }

        // Decomposição canônica NFD para separar letras bases de seus diacríticos (ex: 'ô' vira 'o' + '^')
        String decomposed = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        StringBuilder sb = new StringBuilder(decomposed.length());
        for (int i = 0; i < decomposed.length(); i++) {
            char c = decomposed.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }

        return sb.toString();
    }

    /**
     * Verifica se o texto informado forma um palíndromo em linguagem natural (ignora acentos, maiúsculas,
     * pontuação, hífens e espaços em branco).
     *
     * @param text frase ou palavra a ser verificada
     * @return true se o texto sanitizado é um palíndromo, false caso contrário
     * @throws IllegalArgumentException se text for nulo
     */
    public static boolean isPalindrome(String text) {
        if (text == null) {
            throw new IllegalArgumentException(ERROR_NULL_TEXT);
        }

        String sanitized = sanitize(text);
        return isStrictPalindrome(sanitized);
    }
}
