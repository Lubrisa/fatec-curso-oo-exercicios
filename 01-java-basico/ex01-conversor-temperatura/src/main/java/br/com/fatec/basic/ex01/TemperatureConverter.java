package br.com.fatec.basic.ex01;

/**
 * Classe utilitária responsável pela conversão de temperaturas entre as escalas
 * Celsius, Fahrenheit e Kelvin.
 */
public final class TemperatureConverter {

    /**
     * Constante que define o zero absoluto na escala Celsius (-273.15 °C).
     */
    public static final double ABSOLUTE_ZERO_CELSIUS = -273.15;

    /**
     * Constante que define o zero absoluto na escala Fahrenheit (-459.67 °F).
     */
    public static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;

    /**
     * Constante que define o zero absoluto na escala Kelvin (0.0 K).
     */
    public static final double ABSOLUTE_ZERO_KELVIN = 0.0;

    private TemperatureConverter() {
        // Construtor privado para impedir instanciação de classe utilitária
    }

    /**
     * Converte um valor de temperatura da escala Celsius para Fahrenheit.
     * Fórmula: (celsius * 9/5) + 32
     *
     * @param celsius a temperatura em Celsius, não pode ser inferior ao zero absoluto (-273.15 °C)
     * @return a temperatura equivalente na escala Fahrenheit
     * @throws IllegalArgumentException se a temperatura informada for inferior ao zero absoluto
     */
    public static double celsiusToFahrenheit(double celsius) {
        // TODO: Validar o limite inferior e implementar o cálculo de conversão
        throw new UnsupportedOperationException("Método celsiusToFahrenheit ainda não implementado");
    }

    /**
     * Converte um valor de temperatura da escala Fahrenheit para Celsius.
     * Fórmula: (fahrenheit - 32) * 5/9
     *
     * @param fahrenheit a temperatura em Fahrenheit, não pode ser inferior ao zero absoluto (-459.67 °F)
     * @return a temperatura equivalente na escala Celsius
     * @throws IllegalArgumentException se a temperatura informada for inferior ao zero absoluto
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        // TODO: Validar o limite inferior e implementar o cálculo de conversão
        throw new UnsupportedOperationException("Método fahrenheitToCelsius ainda não implementado");
    }

    /**
     * Converte um valor de temperatura da escala Celsius para Kelvin.
     * Fórmula: celsius + 273.15
     *
     * @param celsius a temperatura em Celsius, não pode ser inferior ao zero absoluto (-273.15 °C)
     * @return a temperatura equivalente na escala Kelvin
     * @throws IllegalArgumentException se a temperatura informada for inferior ao zero absoluto
     */
    public static double celsiusToKelvin(double celsius) {
        // TODO: Validar o limite inferior e implementar o cálculo de conversão
        throw new UnsupportedOperationException("Método celsiusToKelvin ainda não implementado");
    }

    /**
     * Converte um valor de temperatura da escala Kelvin para Celsius.
     * Fórmula: kelvin - 273.15
     *
     * @param kelvin a temperatura em Kelvin, não pode ser inferior ao zero absoluto (0.0 K)
     * @return a temperatura equivalente na escala Celsius
     * @throws IllegalArgumentException se a temperatura informada for inferior ao zero absoluto
     */
    public static double kelvinToCelsius(double kelvin) {
        // TODO: Validar o limite inferior e implementar o cálculo de conversão
        throw new UnsupportedOperationException("Método kelvinToCelsius ainda não implementado");
    }
}
