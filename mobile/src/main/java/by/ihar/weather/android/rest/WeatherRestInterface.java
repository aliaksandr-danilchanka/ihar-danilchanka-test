package by.ihar.weather.android.rest;

import by.ihar.weather.android.model.wrapper.ForecastWeatherWrapper;
import by.ihar.weather.android.model.wrapper.TodayWeatherWrapper;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WeatherRestInterface {

    @GET("/weather")
    void getTodayWeather(@Query("lat") Double lat, @Query("lon") Double lon, Callback<TodayWeatherWrapper> cb);

    @GET("/forecast/daily")
    void getForecastWeathers(@Query("cnt") Integer cnt, @Query("lat") Double lat, @Query("lon") Double lon, Callback<ForecastWeatherWrapper> cb);
}
