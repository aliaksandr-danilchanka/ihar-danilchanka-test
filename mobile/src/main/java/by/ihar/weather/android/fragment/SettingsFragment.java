package by.ihar.weather.android.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import by.ihar.weather.android.WeatherAndroidIharApplication;
import by.ihar.weather.android.R;
import by.ihar.weather.android.activity.WeatherActivity;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        WeatherAndroidIharApplication.getInstance().getPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            ((WeatherActivity) getActivity()).showNormalState();
            ((WeatherActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_settings);
            ((WeatherActivity) getActivity()).getDrawerToggle().setDrawerIndicatorEnabled(false);
            ((WeatherActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((WeatherActivity) getActivity()).getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getFragmentManager().popBackStack();
                }
            });
            ((WeatherActivity) getActivity()).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
        }
    }


}
