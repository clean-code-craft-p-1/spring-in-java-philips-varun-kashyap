package statisticker;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class StatisticsTest 
{
    @Test
    public void reportsAverageMinMaxx()
    {
        Float[] numbers = {98.6f, 98.2f, 97.8f, 102.2f};
        List<Float> numberList = Arrays.asList(numbers);

        Stats s = Statistics.getStatistics(numberList);

        float epsilon = 0.001f;
        assertEquals(s.average, 99.2f, epsilon);
        assertEquals(s.min, 97.8f, epsilon);
        assertEquals(s.max, 102.2f, epsilon);
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
    }

    @Test
    public void convertsCelsiusInputToFahrenheitBeforeComputingStats()
    {
        List<Double> temperaturesInCelsius = Arrays.asList(37.0, 36.7778, 36.5556, 39.0);

        Stats s = Statistics.getStatistics(temperaturesInCelsius, TemperatureUnit.CELSIUS);

        float epsilon = 0.01f;
        assertEquals(99.2f, s.average, epsilon);
        assertEquals(97.8f, s.min, epsilon);
        assertEquals(102.2f, s.max, epsilon);
    }

    @Test
    public void acceptsIntegralTemperatureReadings()
    {
        List<Integer> numbers = Arrays.asList(98, 99, 100);

        Stats s = Statistics.getStatistics(numbers, TemperatureUnit.FAHRENHEIT);

        float epsilon = 0.001f;
        assertEquals(99.0f, s.average, epsilon);
        assertEquals(98.0f, s.min, epsilon);
        assertEquals(100.0f, s.max, epsilon);
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
}
