package br.com.fatec.basic.ex01;

/**
 * Utility class providing conversions between Celsius, Fahrenheit, and Kelvin temperature scales.
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

    private TemperatureConverter() {
        // Utility class: prevent direct instantiation
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
        // TODO: Validate lower bound and implement conversion
        throw new UnsupportedOperationException("Method celsiusToFahrenheit not implemented yet");
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
        // TODO: Validate lower bound and implement conversion
        throw new UnsupportedOperationException("Method fahrenheitToCelsius not implemented yet");
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
        // TODO: Validate lower bound and implement conversion
        throw new UnsupportedOperationException("Method celsiusToKelvin not implemented yet");
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
        // TODO: Validate lower bound and implement conversion
        throw new UnsupportedOperationException("Method kelvinToCelsius not implemented yet");
    }
}
