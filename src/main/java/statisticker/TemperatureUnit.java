package statisticker;

public enum TemperatureUnit
{
    FAHRENHEIT {
        @Override
        public float toFahrenheit(double value)
        {
            return (float) value;
        }
    },
    CELSIUS {
        @Override
        public float toFahrenheit(double value)
        {
            return (float) ((value * 9.0 / 5.0) + 32.0);
        }
    };

    public abstract float toFahrenheit(double value);
}