package by.ihar.weather.android.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.ihar.weather.android.WeatherAndroidIharApplication;

public class Utils {

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) WeatherAndroidIharApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    // returns URL from weather API
    public static String getWeatherIconUrl(String icon) {
        return String.format("http://openweathermap.org/img/w/%s.png", icon);
    }


    // returns name of wind direction from wind degree
    public static String getWindDirection(Double deg) {
        String[] arr = new String[]{"N", "NNE", "NE", "ENE", "E", "ESE", "SE",
                "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        return arr[Math.abs((int) Math.round((deg - 11.25) / 22.5))];
    }


    public static Double getTemperatureInCelsius(Double kelvin) {
        return kelvin - 273.15;
    }


    public static Double getTemperatureInFahrenheit(Double kelvin) {
        return getTemperatureInCelsius(kelvin) * 1.8 + 32;
    }


    // returns double number with 1 digit after point
    public static Double round(Double doub) {
        return ((double) Math.round(doub * 10)) / 10;
    }


    // returns day name from unix timestamp
    public static String getDayName(long timestamp) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(date);
    }


    public static Double kphToMph(Double kph) {
        return kph * 0.621371192;
    }


}
