package br.com.fatec.basic.ex01;

/**
 * Implementação de referência para o conversor de temperaturas.
 */
public final class TemperatureConverter {

    /**
     * Constante que define o zero absoluto na escala Celsius (-273.15 °C).
     */
    private static final double ABSOLUTE_ZERO_CELSIUS = -273.15;

    /**
     * Constante que define o zero absoluto na escala Fahrenheit (-459.67 °F).
     */
    private static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;

    /**
     * Constante que define o zero absoluto na escala Kelvin (0.0 K).
     */
    private static final double ABSOLUTE_ZERO_KELVIN = 0.0;

    private static final String ERROR_BELOW_ABSOLUTE_ZERO = "Temperatura abaixo do zero absoluto";

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
        if (celsius < ABSOLUTE_ZERO_CELSIUS) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return (celsius * 9.0 / 5.0) + 32.0;
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
        if (fahrenheit < ABSOLUTE_ZERO_FAHRENHEIT) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return (fahrenheit - 32.0) * (5.0 / 9.0);
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
        if (celsius < ABSOLUTE_ZERO_CELSIUS) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return celsius + 273.15;
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
        if (kelvin < ABSOLUTE_ZERO_KELVIN) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return kelvin - 273.15;
    }
}
