package by.ihar.weather.android.model;

import by.ihar.weather.android.R;
import by.ihar.weather.android.WeatherAndroidIharApplication;
import by.ihar.weather.android.model.wrapper.ForecastWeatherWrapper;
import by.ihar.weather.android.utility.Preferences;
import by.ihar.weather.android.utility.Utils;

public class ForecastWeather {

    public String weatherStatus = "";
    public String weatherIconUrl = "";
    public String day = "";
    private Double temp = 0.0;


    public ForecastWeather(ForecastWeatherWrapper.ForecastWeatherWrapperItem wrapper) {
        if (wrapper.dt != null) {
            this.day = Utils.getDayName(wrapper.dt);
        }
        if (wrapper.weather.length > 0) {
            this.weatherStatus = wrapper.weather[0].main;
            this.weatherIconUrl = Utils.getWeatherIconUrl(wrapper.weather[0].icon);
        }
        if (wrapper.temp != null) {
            this.temp = wrapper.temp.day;
        }
    }


    // returns temperature value in prefer format
    public String getTemperature() {
        if (Preferences.getTemperatureUnits() == Preferences.TEMPERATURE_UNITS_CELSIUS) {
            return Double.toString(Utils.round(Utils.getTemperatureInCelsius(this.temp)))
                    + WeatherAndroidIharApplication.getInstance().getResources().getString(R.string.weather_celsius);
        } else {
            return Double.toString(Utils.round(Utils.getTemperatureInFahrenheit(this.temp)))
                    + WeatherAndroidIharApplication.getInstance().getResources().getString(R.string.weather_fahrenheit);
        }
    }
}
