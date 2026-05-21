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
}
