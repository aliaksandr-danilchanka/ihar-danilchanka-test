package by.ihar.weather.android.model.wrapper;

import java.util.ArrayList;

import by.ihar.weather.android.model.ForecastWeather;

public class ForecastWeatherWrapper {

    private ForecastWeatherWrapperItem[] list;


    public ForecastWeather[] getForecastWeathers() {
        ArrayList<ForecastWeather> result = new ArrayList<>();
        for (ForecastWeatherWrapperItem item : list) {
            result.add(new ForecastWeather(item));
        }
        return result.toArray(new ForecastWeather[result.size()]);
    }


    public class ForecastWeatherWrapperItem {
        public Temp temp;
        public Long dt;
        public Weather[] weather;

        public class Temp {
            public Double day;
        }

        public class Weather {
            public String main;
            public String icon;
        }
    }


}
