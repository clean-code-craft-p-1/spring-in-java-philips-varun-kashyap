package statisticker;

/**
 * Temperature units supported by the statistics API.
 */
public enum TemperatureUnit
{
    FAHRENHEIT {
        @Override
        public float toFahrenheit(double value)
        {
            return (float) value;
        }

        @Override
        public float fromFahrenheit(double value)
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

        @Override
        public float fromFahrenheit(double value)
        {
            return (float) ((value - 32.0) * 5.0 / 9.0);
        }
    };

    /**
     * Converts a temperature value in this unit to Fahrenheit.
     *
     * @param value the value in this unit
     * @return the converted value in Fahrenheit
     */
    public abstract float toFahrenheit(double value);

    /**
     * Converts a temperature value in Fahrenheit to this unit.
     *
     * @param value the value in Fahrenheit
     * @return the converted value in this unit
     */
    public abstract float fromFahrenheit(double value);

    /**
     * Parses a unit token accepted from raw user input.
     *
     * @param token the textual unit token, such as F, C, Fahrenheit, or Celsius
     * @return the matching unit
     */
    public static TemperatureUnit fromToken(String token)
    {
        if (token == null) {
            throw new IllegalArgumentException("Temperature unit token must be provided");
        }

        String normalizedToken = token.trim().toUpperCase();
        if (normalizedToken.isEmpty()) {
            throw new IllegalArgumentException("Temperature unit token must not be blank");
        }

        if ("F".equals(normalizedToken) || "FAHRENHEIT".equals(normalizedToken)) {
            return FAHRENHEIT;
        }

        if ("C".equals(normalizedToken) || "CELSIUS".equals(normalizedToken)) {
            return CELSIUS;
        }

        throw new IllegalArgumentException("Unsupported temperature unit token: " + token);
    }
}