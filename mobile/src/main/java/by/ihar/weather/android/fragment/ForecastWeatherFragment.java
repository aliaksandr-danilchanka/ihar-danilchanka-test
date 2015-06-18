package by.ihar.weather.android.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.ihar.weather.android.R;
import by.ihar.weather.android.activity.WeatherActivity;
import by.ihar.weather.android.adapter.ForecastWeatherAdapter;
import by.ihar.weather.android.fragment.base.BaseFragment;
import by.ihar.weather.android.model.ForecastWeather;
import by.ihar.weather.android.model.wrapper.ForecastWeatherWrapper;
import by.ihar.weather.android.rest.WeatherRestHelper;
import by.ihar.weather.android.utility.Preferences;
import by.ihar.weather.android.utility.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ForecastWeatherFragment extends BaseFragment {

    private static final String ARG_LAT = "ARG_LAT";
    private static final String ARG_LON = "ARG_LON";

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ForecastWeather[] mForecastWeathers;
    private Double mLat;
    private Double mLon;


    public ForecastWeatherFragment() {
        // Required empty public constructor
    }


    public static ForecastWeatherFragment newInstance(Double lat, Double lon) {
        ForecastWeatherFragment fragment = new ForecastWeatherFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LAT, lat);
        args.putDouble(ARG_LON, lon);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLat = getArguments().getDouble(ARG_LAT);
            mLon = getArguments().getDouble(ARG_LON);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        ButterKnife.inject(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        renderView();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mForecastWeathers != null) {
            renderView();
        }
    }


    @Override
    public void loadData() {
        if (getActivity() != null) {
            ((WeatherActivity) getActivity()).showProgressState();
        }
        WeatherRestHelper.getRestInterface().getForecastWeathers(Preferences.getCountOfForecastResults(), mLat, mLon, new Callback<ForecastWeatherWrapper>() {
            @Override
            public void success(ForecastWeatherWrapper forecastWeatherWrapper, Response response) {
                if (forecastWeatherWrapper != null) {
                    mForecastWeathers = forecastWeatherWrapper.getForecastWeathers();
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
        return R.string.title_forecast;
    }


    private void renderView() {
        if (isSafeState()) {
            RecyclerView.Adapter mAdapter = new ForecastWeatherAdapter(mForecastWeathers, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }
    }


}
