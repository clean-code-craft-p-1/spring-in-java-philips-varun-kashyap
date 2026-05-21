package statisticker;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Computes statistics for human temperature measurements.
 */
public class Statistics 
{
    private static final Pattern READING_PATTERN = Pattern.compile(
        "^\\s*([+-]?(?:\\d+(?:\\.\\d+)?|\\.\\d+))\\s*([a-zA-Z]+)?\\s*$");
    private static final float MIN_REASONABLE_FAHRENHEIT = 80.0f;
    private static final float MAX_REASONABLE_FAHRENHEIT = 115.0f;

    /**
     * Computes statistics for numeric readings assumed to be in Fahrenheit.
     *
     * @param numbers the numeric temperature readings
     * @return the computed summary in Fahrenheit
     */
    public static Stats getStatistics(List<? extends Number> numbers) {
        return getStatistics(numbers, TemperatureUnit.FAHRENHEIT);
    }

    /**
     * Computes statistics for numeric readings in the supplied input unit.
     * The returned statistics use the same unit as the caller supplied.
     *
     * @param numbers the numeric temperature readings
     * @param inputUnit the unit used by every reading in the list
     * @return the computed summary in the requested unit
     */
    public static Stats getStatistics(List<? extends Number> numbers, TemperatureUnit inputUnit) {
        if (inputUnit == null) {
            throw new IllegalArgumentException("Temperature unit must be provided");
        }

        if (numbers == null) {
            throw new IllegalArgumentException("Temperature list must be provided");
        }

        if (numbers.isEmpty()) {
            return new Stats(Float.NaN, Float.NaN, Float.NaN, inputUnit);
        }

        float fahrenheitSum = 0.0f;
        float fahrenheitMin = Float.POSITIVE_INFINITY;
        float fahrenheitMax = Float.NEGATIVE_INFINITY;

        for (Number number : numbers) {
            float normalizedTemperature = normalizeTemperature(number, inputUnit);
            fahrenheitSum += normalizedTemperature;
            fahrenheitMin = Math.min(fahrenheitMin, normalizedTemperature);
            fahrenheitMax = Math.max(fahrenheitMax, normalizedTemperature);
        }

        return new Stats(
            inputUnit.fromFahrenheit(fahrenheitSum / numbers.size()),
            inputUnit.fromFahrenheit(fahrenheitMin),
            inputUnit.fromFahrenheit(fahrenheitMax),
            inputUnit);
    }

    /**
     * Computes statistics for raw user-entered readings.
     * Each reading may optionally include its own unit, such as 98.6F or 37C.
     * When no unit is present, the provided default unit is used.
     * The returned statistics are expressed in the requested output unit.
     *
     * @param readings the raw user-entered readings
     * @param defaultInputUnit the unit to assume when a reading omits a unit token
     * @param outputUnit the unit to use for the returned summary
     * @return the computed summary in the requested output unit
     */
    public static Stats getStatisticsFromReadings(
        List<String> readings,
        TemperatureUnit defaultInputUnit,
        TemperatureUnit outputUnit)
    {
        if (defaultInputUnit == null) {
            throw new IllegalArgumentException("Default input unit must be provided");
        }

        if (outputUnit == null) {
            throw new IllegalArgumentException("Output unit must be provided");
        }

        if (readings == null) {
            throw new IllegalArgumentException("Temperature readings must be provided");
        }

        if (readings.isEmpty()) {
            return new Stats(Float.NaN, Float.NaN, Float.NaN, outputUnit);
        }

        float fahrenheitSum = 0.0f;
        float fahrenheitMin = Float.POSITIVE_INFINITY;
        float fahrenheitMax = Float.NEGATIVE_INFINITY;

        for (String reading : readings) {
            float normalizedTemperature = parseAndNormalizeTemperature(reading, defaultInputUnit);
            fahrenheitSum += normalizedTemperature;
            fahrenheitMin = Math.min(fahrenheitMin, normalizedTemperature);
            fahrenheitMax = Math.max(fahrenheitMax, normalizedTemperature);
        }

        return new Stats(
            outputUnit.fromFahrenheit(fahrenheitSum / readings.size()),
            outputUnit.fromFahrenheit(fahrenheitMin),
            outputUnit.fromFahrenheit(fahrenheitMax),
            outputUnit);
    }

    private static float normalizeTemperature(Number number, TemperatureUnit inputUnit)
    {
        if (number == null) {
            throw new IllegalArgumentException("Temperature readings cannot contain null values");
        }

        double value = number.doubleValue();
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Temperature readings must be finite numbers");
        }

        return validateNormalizedTemperature(inputUnit.toFahrenheit(value));
    }

    private static float parseAndNormalizeTemperature(String reading, TemperatureUnit defaultInputUnit)
    {
        if (reading == null) {
            throw new IllegalArgumentException("Temperature readings cannot contain null values");
        }

        Matcher matcher = READING_PATTERN.matcher(reading);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid temperature reading: " + reading);
        }

        double value = Double.parseDouble(matcher.group(1));
        String unitToken = matcher.group(2);
        TemperatureUnit inputUnit = unitToken == null ? defaultInputUnit : TemperatureUnit.fromToken(unitToken);

        return validateNormalizedTemperature(inputUnit.toFahrenheit(value));
    }

    private static float validateNormalizedTemperature(float valueInFahrenheit)
    {
        if (valueInFahrenheit < MIN_REASONABLE_FAHRENHEIT || valueInFahrenheit > MAX_REASONABLE_FAHRENHEIT) {
            throw new IllegalArgumentException(
                "Temperature reading is outside the supported human range: " + valueInFahrenheit + "F");
        }

        return valueInFahrenheit;
    }
}
