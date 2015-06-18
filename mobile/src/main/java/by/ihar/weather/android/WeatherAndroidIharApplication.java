package by.ihar.weather.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class WeatherAndroidIharApplication extends Application {

    private SharedPreferences preferences;

    private static WeatherAndroidIharApplication sInstance;


    public static WeatherAndroidIharApplication getInstance() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
        }
        return preferences;
    }
}
