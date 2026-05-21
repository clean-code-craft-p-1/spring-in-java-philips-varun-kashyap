package statisticker;

import java.util.List;

public class Statistics 
{
    public static Stats getStatistics(List<Float> numbers) {
        if (numbers.isEmpty()) {
            return new Stats(Float.NaN, Float.NaN, Float.NaN);
        }

        float sum = 0.0f;
        float min = numbers.get(0);
        float max = numbers.get(0);

        for (float number : numbers) {
            sum += number;
            min = Math.min(min, number);
            max = Math.max(max, number);
        }

        return new Stats(sum / numbers.size(), min, max);
    }
}
