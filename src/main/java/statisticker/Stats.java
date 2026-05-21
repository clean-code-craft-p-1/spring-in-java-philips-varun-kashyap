package statisticker;

/**
 * Immutable statistical summary for a set of temperature readings.
 */
public class Stats
{
    public final float average;
    public final float min;
    public final float max;
    public final TemperatureUnit unit;

    /**
     * Creates a summary in the supplied display unit.
     *
     * @param average the arithmetic mean of all readings
     * @param min the minimum reading
     * @param max the maximum reading
     * @param unit the unit used by all values in this summary
     */
    public Stats(float average, float min, float max, TemperatureUnit unit)
    {
        this.average = average;
        this.min = min;
        this.max = max;
        this.unit = unit;
    }
}