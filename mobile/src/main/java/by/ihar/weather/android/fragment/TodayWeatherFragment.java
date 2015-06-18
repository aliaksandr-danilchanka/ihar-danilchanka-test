package by.ihar.weather.android.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.ihar.weather.android.R;
import by.ihar.weather.android.activity.WeatherActivity;
import by.ihar.weather.android.fragment.base.BaseFragment;
import by.ihar.weather.android.model.TodayWeather;
import by.ihar.weather.android.model.wrapper.TodayWeatherWrapper;
import by.ihar.weather.android.rest.WeatherRestHelper;
import by.ihar.weather.android.utility.Preferences;
import by.ihar.weather.android.utility.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TodayWeatherFragment extends BaseFragment {

    private static final String ARG_LAT = "ARG_LAT";
    private static final String ARG_LON = "ARG_LON";
    private static final String ARG_CITY = "ARG_CITY";
    private static final String ARG_COUNTRY = "ARG_COUNTRY";

    @InjectView(R.id.img_status_today)
    ImageView mImgStatusToday;
    @InjectView(R.id.txt_location)
    TextView mTxtLocation;
    @InjectView(R.id.txt_status)
    TextView mTxtStatus;
    @InjectView(R.id.txt_humidity)
    TextView mTxtHumidity;
    @InjectView(R.id.txt_precipitation)
    TextView mTxtPrecipitation;
    @InjectView(R.id.txt_pressure)
    TextView mTxtPressure;
    @InjectView(R.id.txt_wind_speed)
    TextView mTxtWindSpeed;
    @InjectView(R.id.txt_direction)
    TextView mTxtDirection;

    TodayWeather mTodayWeather;
    private Double mLat;
    private Double mLon;
    private String mCity;
    private String mCountry;


    public TodayWeatherFragment() {
        // Required empty public constructor
    }


    public static TodayWeatherFragment newInstance(Double lat, Double lon, String city, String country) {
        TodayWeatherFragment fragment = new TodayWeatherFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LAT, lat);
        args.putDouble(ARG_LON, lon);
        args.putString(ARG_CITY, city);
        args.putString(ARG_COUNTRY, country);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLat = getArguments().getDouble(ARG_LAT);
            mLon = getArguments().getDouble(ARG_LON);
            mCity = getArguments().getString(ARG_CITY);
            mCountry = getArguments().getString(ARG_COUNTRY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        ButterKnife.inject(this, view);
        if (mLat != null && mLon != null && Utils.isNetworkAvailable()) {
            loadData();
        } else {
            if (getActivity() != null) {
                ((WeatherActivity) getActivity()).showErrorState();
            }
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTodayWeather != null) {
            renderView();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mTodayWeather != null) {
            renderView();
        }
    }


    @Override
    public void loadData() {
        if (getActivity() != null) {
            ((WeatherActivity) getActivity()).showProgressState();
        }
        WeatherRestHelper.getRestInterface().getTodayWeather(mLat, mLon, new Callback<TodayWeatherWrapper>() {
            @Override
            public void success(TodayWeatherWrapper todayWeatherWrapper, Response response) {
                if (todayWeatherWrapper != null) {
                    mTodayWeather = new TodayWeather(todayWeatherWrapper);
                    renderView();
                    if (getActivity() != null) {
                        ((WeatherActivity) getActivity()).showNormalState();
                    }
                }
            }


            @Override
            public void failure(RetrofitError error) {
                ((WeatherActivity) getActivity()).showErrorState();
            }
        });
    }


    @Override
    protected int getTitle() {
        return R.string.title_today_weather;
    }


    private void renderView() {
        if (isSafeState()) {
            Picasso.with(getActivity())
                    .load(mTodayWeather.weatherIconUrl)
                    .into(mImgStatusToday);

            mTxtStatus.setText(mTodayWeather.getTemperature() + " " + mTodayWeather.weatherStatus);
            if (mCity != null && mCountry != null) {
                mTxtLocation.setText(mCity + ", " + mCountry);
            }

            mTxtHumidity.setText(Double.toString(Utils.round(mTodayWeather.humidity)) + " "
                    + getResources().getString(R.string.weather_percent));
            mTxtPrecipitation.setText(Double.toString(Utils.round(mTodayWeather.precipitation)) + " "
                    + getResources().getString(R.string.weather_mm));
            mTxtPressure.setText(Double.toString(Utils.round(mTodayWeather.pressure)) + " "
                    + getResources().getString(R.string.weather_hpa));
            if (Preferences.getSpeedUnits() == Preferences.SPEED_UNITS_KPH) {
                mTxtWindSpeed.setText(Double.toString(Utils.round(mTodayWeather.windSpeed)) + " "
                        + getResources().getString(R.string.weather_kmh));
            } else {
                mTxtWindSpeed.setText(Double.toString(Utils.round(Utils.kphToMph(mTodayWeather.windSpeed))) + " "
                        + getResources().getString(R.string.weather_mph));
            }
            mTxtDirection.setText(mTodayWeather.windDirection);
        }
    }
}
