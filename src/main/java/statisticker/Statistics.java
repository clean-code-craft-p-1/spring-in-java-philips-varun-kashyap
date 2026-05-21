package statisticker;

import java.util.List;

public class Statistics 
{
    public static Stats getStatistics(List<? extends Number> numbers) {
        return getStatistics(numbers, TemperatureUnit.FAHRENHEIT);
    }

    public static Stats getStatistics(List<? extends Number> numbers, TemperatureUnit inputUnit) {
        if (inputUnit == null) {
            throw new IllegalArgumentException("Temperature unit must be provided");
        }

        if (numbers == null) {
            throw new IllegalArgumentException("Temperature list must be provided");
        }

        if (numbers.isEmpty()) {
            return new Stats(Float.NaN, Float.NaN, Float.NaN);
        }

        float sum = 0.0f;
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;

        for (Number number : numbers) {
            float normalizedTemperature = normalizeTemperature(number, inputUnit);
            sum += normalizedTemperature;
            min = Math.min(min, normalizedTemperature);
            max = Math.max(max, normalizedTemperature);
        }

        return new Stats(sum / numbers.size(), min, max);
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

        return inputUnit.toFahrenheit(value);
    }
}
