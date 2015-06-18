package by.ihar.weather.android.utility;

import android.content.SharedPreferences;

import by.ihar.weather.android.WeatherAndroidIharApplication;

public class Preferences {

    private static final String KEY_TEMPERATURE_UNITS = "temperature_units";
    private static final String KEY_SPEED_UNITS = "speed_units";
    private static final String KEY_COUNT_OF_FORECAST_RESULTS = "forecast_cnt";

    public static final int TEMPERATURE_UNITS_CELSIUS = 0;
    public static final int TEMPERATURE_UNITS_FAHRENHEIT = 1;

    public static final int SPEED_UNITS_KPH = 0;
    public static final int SPEED_UNITS_MPH = 1;


    public static int getTemperatureUnits() {
        try {
            return Integer.parseInt(getPrefs().getString(KEY_TEMPERATURE_UNITS, null));
        } catch (Exception e) {
            return TEMPERATURE_UNITS_CELSIUS;
        }
    }


    public static int getSpeedUnits() {
        try {
            return Integer.parseInt(getPrefs().getString(KEY_SPEED_UNITS, null));
        } catch (Exception e) {
            return SPEED_UNITS_KPH;
        }
    }


    public static int getCountOfForecastResults() {
        try {
            return Integer.parseInt(getPrefs().getString(KEY_COUNT_OF_FORECAST_RESULTS, null));
        } catch (Exception e) {
            return 5;
        }
    }


    private static SharedPreferences getPrefs() {
        return WeatherAndroidIharApplication.getInstance().getPreferences();
    }


}
