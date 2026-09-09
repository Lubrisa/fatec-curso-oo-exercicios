package br.com.fatec.basic.ex01;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ex01 — TemperatureConverter Tests")
class TemperatureConverterTest {

    private static final Offset<Double> PRECISION = Offset.offset(0.01);

    @Nested
    @DisplayName("Celsius to Fahrenheit Conversion")
    class CelsiusToFahrenheitTests {

        @Test
        @DisplayName("Should convert water freezing point: 0 °C -> 32 °F")
        void shouldConvertFreezingPoint() {
            double result = TemperatureConverter.celsiusToFahrenheit(0.0);
            assertThat(result).isCloseTo(32.0, PRECISION);
        }

        @Test
        @DisplayName("Should convert water boiling point: 100 °C -> 212 °F")
        void shouldConvertBoilingPoint() {
            double result = TemperatureConverter.celsiusToFahrenheit(100.0);
            assertThat(result).isCloseTo(212.0, PRECISION);
        }

        @Test
        @DisplayName("Should convert human body temperature: 37 °C -> 98.6 °F")
        void shouldConvertBodyTemperature() {
            double result = TemperatureConverter.celsiusToFahrenheit(37.0);
            assertThat(result).isCloseTo(98.6, PRECISION);
        }

        @Test
        @DisplayName("Should convert coincidence point: -40 °C -> -40 °F")
        void shouldConvertMinusFortyDegrees() {
            double result = TemperatureConverter.celsiusToFahrenheit(-40.0);
            assertThat(result).isCloseTo(-40.0, PRECISION);
        }

        @Test
        @DisplayName("Should accept exactly absolute zero: -273.15 °C -> -459.67 °F")
        void shouldAcceptAbsoluteZeroInCelsius() {
            double result = TemperatureConverter.celsiusToFahrenheit(-273.15);
            assertThat(result).isCloseTo(-459.67, PRECISION);
        }

        @ParameterizedTest(name = "Invalid Celsius: {0}")
        @ValueSource(doubles = {-273.16, -274.0, -300.0, -1000.0})
        @DisplayName("Should reject temperatures below absolute zero in Celsius")
        void shouldRejectTemperaturesBelowAbsoluteZero(double invalidCelsius) {
            assertThatThrownBy(() -> TemperatureConverter.celsiusToFahrenheit(invalidCelsius))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Temperature below absolute zero");
        }
    }

    @Nested
    @DisplayName("Fahrenheit to Celsius Conversion")
    class FahrenheitToCelsiusTests {

        @Test
        @DisplayName("Should convert water freezing point: 32 °F -> 0 °C")
        void shouldConvertFreezingPoint() {
            double result = TemperatureConverter.fahrenheitToCelsius(32.0);
            assertThat(result).isCloseTo(0.0, PRECISION);
        }

        @Test
        @DisplayName("Should convert water boiling point: 212 °F -> 100 °C")
        void shouldConvertBoilingPoint() {
            double result = TemperatureConverter.fahrenheitToCelsius(212.0);
            assertThat(result).isCloseTo(100.0, PRECISION);
        }

        @Test
        @DisplayName("Should convert coincidence point: -40 °F -> -40 °C")
        void shouldConvertMinusFortyDegrees() {
            double result = TemperatureConverter.fahrenheitToCelsius(-40.0);
            assertThat(result).isCloseTo(-40.0, PRECISION);
        }

        @Test
        @DisplayName("Should accept exactly absolute zero: -459.67 °F -> -273.15 °C")
        void shouldAcceptAbsoluteZeroInFahrenheit() {
            double result = TemperatureConverter.fahrenheitToCelsius(-459.67);
            assertThat(result).isCloseTo(-273.15, PRECISION);
        }

        @ParameterizedTest(name = "Invalid Fahrenheit: {0}")
        @ValueSource(doubles = {-459.68, -460.0, -500.0, -1000.0})
        @DisplayName("Should reject temperatures below absolute zero in Fahrenheit")
        void shouldRejectTemperaturesBelowAbsoluteZero(double invalidFahrenheit) {
            assertThatThrownBy(() -> TemperatureConverter.fahrenheitToCelsius(invalidFahrenheit))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Temperature below absolute zero");
        }
    }

    @Nested
    @DisplayName("Celsius to Kelvin Conversion")
    class CelsiusToKelvinTests {

        @Test
        @DisplayName("Should convert water freezing point: 0 °C -> 273.15 K")
        void shouldConvertFreezingPoint() {
            double result = TemperatureConverter.celsiusToKelvin(0.0);
            assertThat(result).isCloseTo(273.15, PRECISION);
        }

        @Test
        @DisplayName("Should convert water boiling point: 100 °C -> 373.15 K")
        void shouldConvertBoilingPoint() {
            double result = TemperatureConverter.celsiusToKelvin(100.0);
            assertThat(result).isCloseTo(373.15, PRECISION);
        }

        @Test
        @DisplayName("Should convert absolute zero: -273.15 °C -> 0 K")
        void shouldConvertAbsoluteZero() {
            double result = TemperatureConverter.celsiusToKelvin(-273.15);
            assertThat(result).isCloseTo(0.0, PRECISION);
        }

        @ParameterizedTest(name = "Invalid Celsius: {0}")
        @ValueSource(doubles = {-273.16, -300.0})
        @DisplayName("Should reject temperatures below absolute zero when converting to Kelvin")
        void shouldRejectBelowAbsoluteZero(double invalidCelsius) {
            assertThatThrownBy(() -> TemperatureConverter.celsiusToKelvin(invalidCelsius))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Temperature below absolute zero");
        }
    }

    @Nested
    @DisplayName("Kelvin to Celsius Conversion")
    class KelvinToCelsiusTests {

        @Test
        @DisplayName("Should convert absolute zero: 0 K -> -273.15 °C")
        void shouldConvertAbsoluteZero() {
            double result = TemperatureConverter.kelvinToCelsius(0.0);
            assertThat(result).isCloseTo(-273.15, PRECISION);
        }

        @Test
        @DisplayName("Should convert water freezing point: 273.15 K -> 0 °C")
        void shouldConvertFreezingPoint() {
            double result = TemperatureConverter.kelvinToCelsius(273.15);
            assertThat(result).isCloseTo(0.0, PRECISION);
        }

        @Test
        @DisplayName("Should convert water boiling point: 373.15 K -> 100 °C")
        void shouldConvertBoilingPoint() {
            double result = TemperatureConverter.kelvinToCelsius(373.15);
            assertThat(result).isCloseTo(100.0, PRECISION);
        }

        @ParameterizedTest(name = "Invalid Kelvin: {0}")
        @ValueSource(doubles = {-0.01, -1.0, -273.15})
        @DisplayName("Should reject negative Kelvin values")
        void shouldRejectNegativeKelvin(double invalidKelvin) {
            assertThatThrownBy(() -> TemperatureConverter.kelvinToCelsius(invalidKelvin))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Temperature below absolute zero");
        }
    }
}
