package by.ihar.weather.android.model;

import by.ihar.weather.android.R;
import by.ihar.weather.android.WeatherAndroidIharApplication;
import by.ihar.weather.android.model.wrapper.TodayWeatherWrapper;
import by.ihar.weather.android.utility.Preferences;
import by.ihar.weather.android.utility.Utils;

public class TodayWeather {

    public String weatherStatus = "";
    public String weatherIconUrl = "";

    private Double temp = 0.0;

    public Double humidity = 0.0;
    public Double precipitation = 0.0;
    public Double pressure = 0.0;
    public Double windSpeed = 0.0;
    public String windDirection = "";


    public TodayWeather(TodayWeatherWrapper wrapper) {
        if (wrapper.weather.length > 0) {
            this.weatherStatus = wrapper.weather[0].main;
            this.weatherIconUrl = Utils.getWeatherIconUrl(wrapper.weather[0].icon);
        }
        if (wrapper.main != null) {
            this.temp = wrapper.main.temp;
            this.humidity = wrapper.main.humidity;
            this.pressure = wrapper.main.pressure;
        }

        if (wrapper.rain != null) {
            this.precipitation = wrapper.rain.rain;
        }
        if (wrapper.wind != null) {
            this.windSpeed = wrapper.wind.speed;
            this.windDirection = Utils.getWindDirection(wrapper.wind.deg);
        }
    }


    // returns temperature value in prefer format
    public String getTemperature() {
        if (Preferences.getTemperatureUnits() == Preferences.TEMPERATURE_UNITS_CELSIUS) {
            return Double.toString(Utils.round(Utils.getTemperatureInCelsius(this.temp)))
                    + WeatherAndroidIharApplication.getInstance().getResources().getString(R.string.weather_celsius) + " |";
        } else {
            return Double.toString(Utils.round(Utils.getTemperatureInFahrenheit(this.temp)))
                    + WeatherAndroidIharApplication.getInstance().getResources().getString(R.string.weather_fahrenheit) + " |";
        }
    }
}
