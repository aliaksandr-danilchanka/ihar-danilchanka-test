package by.ihar.weather.android.model.wrapper;

import com.google.gson.annotations.SerializedName;

public class TodayWeatherWrapper {
    public Weather[] weather;
    public Main main;
    public Wind wind;
    public Rain rain;

    public class Weather {
        public String main;
        public String icon;
    }

    public class Main {
        public Double temp;
        public Double pressure;
        public Double humidity;
    }

    public class Wind {
        public Double speed;
        public Double deg;
    }

    public class Rain {
        @SerializedName("3h")
        public Double rain;
    }

}
