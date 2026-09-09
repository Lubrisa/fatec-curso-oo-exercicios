package br.com.fatec.basic.ex01;

/**
 * Reference implementation of the temperature converter.
 */
public final class TemperatureConverter {

    /**
     * Absolute zero constant in Celsius scale (-273.15 °C).
     */
    public static final double ABSOLUTE_ZERO_CELSIUS = -273.15;

    /**
     * Absolute zero constant in Fahrenheit scale (-459.67 °F).
     */
    public static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;

    /**
     * Absolute zero constant in Kelvin scale (0.0 K).
     */
    public static final double ABSOLUTE_ZERO_KELVIN = 0.0;

    private static final String ERROR_BELOW_ABSOLUTE_ZERO = "Temperature below absolute zero";

    private TemperatureConverter() {
        // Utility class: prevent instantiation
    }

    /**
     * Converts a temperature value from Celsius to Fahrenheit.
     * Formula: (celsius * 9/5) + 32
     *
     * @param celsius the temperature in Celsius, must not be below absolute zero (-273.15 °C)
     * @return the equivalent temperature in Fahrenheit
     * @throws IllegalArgumentException if the provided temperature is below absolute zero
     */
    public static double celsiusToFahrenheit(double celsius) {
        if (celsius < ABSOLUTE_ZERO_CELSIUS) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    /**
     * Converts a temperature value from Fahrenheit to Celsius.
     * Formula: (fahrenheit - 32) * 5/9
     *
     * @param fahrenheit the temperature in Fahrenheit, must not be below absolute zero (-459.67 °F)
     * @return the equivalent temperature in Celsius
     * @throws IllegalArgumentException if the provided temperature is below absolute zero
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        if (fahrenheit < ABSOLUTE_ZERO_FAHRENHEIT) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return (fahrenheit - 32.0) * (5.0 / 9.0);
    }

    /**
     * Converts a temperature value from Celsius to Kelvin.
     * Formula: celsius + 273.15
     *
     * @param celsius the temperature in Celsius, must not be below absolute zero (-273.15 °C)
     * @return the equivalent temperature in Kelvin
     * @throws IllegalArgumentException if the provided temperature is below absolute zero
     */
    public static double celsiusToKelvin(double celsius) {
        if (celsius < ABSOLUTE_ZERO_CELSIUS) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return celsius + 273.15;
    }

    /**
     * Converts a temperature value from Kelvin to Celsius.
     * Formula: kelvin - 273.15
     *
     * @param kelvin the temperature in Kelvin, must not be below absolute zero (0.0 K)
     * @return the equivalent temperature in Celsius
     * @throws IllegalArgumentException if the provided temperature is below absolute zero
     */
    public static double kelvinToCelsius(double kelvin) {
        if (kelvin < ABSOLUTE_ZERO_KELVIN) {
            throw new IllegalArgumentException(ERROR_BELOW_ABSOLUTE_ZERO);
        }
        return kelvin - 273.15;
    }
}
