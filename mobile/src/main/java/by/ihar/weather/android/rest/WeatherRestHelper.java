package by.ihar.weather.android.rest;

import com.google.gson.GsonBuilder;

import by.ihar.weather.android.WeatherAndroidIharConfig;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class WeatherRestHelper {
    private static WeatherRestInterface mRestInterface;


    public static WeatherRestInterface getRestInterface() {
        if (mRestInterface == null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(WeatherAndroidIharConfig.WEATHER_API_ENDPOINT)
                    .setConverter(new GsonConverter(new GsonBuilder().create()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            mRestInterface = adapter.create(WeatherRestInterface.class);
        }
        return mRestInterface;
    }
}
