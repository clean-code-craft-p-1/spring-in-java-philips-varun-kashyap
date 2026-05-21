package statisticker;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class StatisticsTest 
{
    private static final float EPSILON = 0.001f;

    @Test
    public void reportsAverageMinMaxx()
    {
        Float[] numbers = {98.6f, 98.2f, 97.8f, 102.2f};
        List<Float> numberList = Arrays.asList(numbers);

        Stats s = Statistics.getStatistics(numberList);

        assertEquals(99.2f, s.average, EPSILON);
        assertEquals(97.8f, s.min, EPSILON);
        assertEquals(102.2f, s.max, EPSILON);
        assertEquals(TemperatureUnit.FAHRENHEIT, s.unit);
    }

    @Test
    public void reportsNaNForEmptyInput()
    {
        List<Float> emptyList = new ArrayList<Float>();

        Stats s = Statistics.getStatistics(emptyList);

        // All fields of computedStats (average, max, min) must be
        // Float.NaN (not-a-number), as described in
        // https://www.geeksforgeeks.org/nan-not-number-java/
        // Specify the asserts here and implement accordingly.
        assertTrue(Float.isNaN(s.average));
        assertTrue(Float.isNaN(s.min));
        assertTrue(Float.isNaN(s.max));
        assertEquals(TemperatureUnit.FAHRENHEIT, s.unit);
    }

    @Test
    public void preservesCelsiusUnitForNumericInput()
    {
        List<Double> temperaturesInCelsius = Arrays.asList(37.0, 36.7778, 36.5556, 39.0);

        Stats s = Statistics.getStatistics(temperaturesInCelsius, TemperatureUnit.CELSIUS);

        float epsilon = 0.01f;
        assertEquals(37.3333f, s.average, epsilon);
        assertEquals(36.5556f, s.min, epsilon);
        assertEquals(39.0f, s.max, epsilon);
        assertEquals(TemperatureUnit.CELSIUS, s.unit);
    }

    @Test
    public void acceptsIntegralTemperatureReadings()
    {
        List<Integer> numbers = Arrays.asList(98, 99, 100);

        Stats s = Statistics.getStatistics(numbers, TemperatureUnit.FAHRENHEIT);

        assertEquals(99.0f, s.average, EPSILON);
        assertEquals(98.0f, s.min, EPSILON);
        assertEquals(100.0f, s.max, EPSILON);
        assertEquals(TemperatureUnit.FAHRENHEIT, s.unit);
    }

    @Test
    public void parsesMixedRawReadingsAndReturnsRequestedUnit()
    {
        List<String> rawReadings = Arrays.asList("98.6F", "37C", "99.5", "36.5 celsius");

        Stats s = Statistics.getStatisticsFromReadings(
            rawReadings,
            TemperatureUnit.FAHRENHEIT,
            TemperatureUnit.CELSIUS);

        float epsilon = 0.02f;
        assertEquals(37.0f, s.average, epsilon);
        assertEquals(36.5f, s.min, epsilon);
        assertEquals(37.5f, s.max, epsilon);
        assertEquals(TemperatureUnit.CELSIUS, s.unit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullTemperatureReadings()
    {
        Statistics.getStatistics(Arrays.asList(98.6, null, 100.0), TemperatureUnit.FAHRENHEIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNotANumberTemperatureReadings()
    {
        Statistics.getStatistics(Arrays.asList(98.6, Double.NaN), TemperatureUnit.FAHRENHEIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsMissingTemperatureUnit()
    {
        Statistics.getStatistics(Arrays.asList(98.6, 99.1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsMalformedRawTemperatureReading()
    {
        Statistics.getStatisticsFromReadings(
            Arrays.asList("ninety eight point six"),
            TemperatureUnit.FAHRENHEIT,
            TemperatureUnit.FAHRENHEIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsUnsupportedRawTemperatureUnit()
    {
        Statistics.getStatisticsFromReadings(
            Arrays.asList("310K"),
            TemperatureUnit.FAHRENHEIT,
            TemperatureUnit.FAHRENHEIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsTemperaturesOutsideSupportedHumanRange()
    {
        Statistics.getStatistics(Arrays.asList(72.0, 98.6), TemperatureUnit.FAHRENHEIT);
    }
}
