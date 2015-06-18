package by.ihar.weather.android.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import by.ihar.weather.android.R;
import by.ihar.weather.android.activity.base.BaseActivity;
import by.ihar.weather.android.dialog.GpsDisabledDialogFragment;
import by.ihar.weather.android.fragment.ForecastWeatherFragment;
import by.ihar.weather.android.fragment.TodayWeatherFragment;
import by.ihar.weather.android.fragment.base.BaseFragment;
import by.ihar.weather.android.utility.Utils;


public class WeatherActivity extends BaseActivity {

    private static final String KEY_LAT = "KEY_LAT";
    private static final String KEY_LON = "KEY_LON";
    private static final String KEY_CITY = "KEY_CITY";
    private static final String KEY_COUNTRY = "KEY_COUNTRY";

    private static final String DIALOG_LOCATION = "Location isnt enabled";

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.drawer)
    LinearLayout mDrawer;
    @InjectView(R.id.btn_reload)
    Button mBtnReload;
    @InjectView(R.id.layout_content)
    RelativeLayout mLayoutContent;
    @InjectView(R.id.layout_progress)
    RelativeLayout mLayoutProgress;
    @InjectView(R.id.layout_error)
    RelativeLayout mLayoutError;
    @InjectView(R.id.txt_error_message)
    TextView mTxtErrorMessage;
    @InjectView(R.id.txt_drawer_today)
    TextView mTxtDrawerToday;
    @InjectView(R.id.txt_drawer_forecast)
    TextView mTxtDrawerForecast;

    private GoogleApiClient mGoogleApiClient;

    private ActionBarDrawerToggle mDrawerToggle;

    private Double mLat;
    private Double mLon;
    private String mCity;
    private String mCountry;


    @OnClick(R.id.menu_item_today)
    void onTodayPressed() {
        if (mLat != null && mLon != null && (getCurrentFragment() == null || getCurrentFragment().getClass() != TodayWeatherFragment.class)) {
            mTxtDrawerToday.setTextColor(getResources().getColor(R.color.global_color_primary));
            mTxtDrawerForecast.setTextColor(getResources().getColor(R.color.global_text_primary));
            replaceFragment(TodayWeatherFragment.newInstance(mLat, mLon, mCity, mCountry));
        }
        if (mDrawerLayout.isDrawerOpen(mDrawer)) {
            mDrawerLayout.closeDrawer(mDrawer);
        }
    }


    @OnClick(R.id.menu_item_forecast)
    void onForecastPressed() {
        if (mLat != null && mLon != null && (getCurrentFragment() == null || getCurrentFragment().getClass() != ForecastWeatherFragment.class)) {
            mTxtDrawerForecast.setTextColor(getResources().getColor(R.color.global_color_primary));
            mTxtDrawerToday.setTextColor(getResources().getColor(R.color.global_text_primary));
            replaceFragment(ForecastWeatherFragment.newInstance(mLat, mLon));
        }
        if (mDrawerLayout.isDrawerOpen(mDrawer)) {
            mDrawerLayout.closeDrawer(mDrawer);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        setupDrawer();

        if (savedInstanceState != null) {
            mLat = savedInstanceState.getDouble(KEY_LAT);
            mLon = savedInstanceState.getDouble(KEY_LON);
            mCity = savedInstanceState.getString(KEY_CITY);
            mCountry = savedInstanceState.getString(KEY_COUNTRY);
            showNormalState();
        }

        if (!isDataLoaded()) {
            if (Utils.isLocationEnabled(getApplicationContext()) && Utils.isNetworkAvailable()) {
                showProgressState();
                loadData();
            } else {
                showErrorState();
            }
        } else {
            if (Utils.isNetworkAvailable()) {
                showNormalState();
            } else {
                showErrorState();
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble(KEY_LAT, mLat == null ? 0.0 : mLat);
        outState.putDouble(KEY_LON, mLon == null ? 0.0 : mLon);
        outState.putString(KEY_CITY, mCity);
        outState.putString(KEY_COUNTRY, mCountry);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawer)) {
            mDrawerLayout.closeDrawer(mDrawer);
        } else {
            super.onBackPressed();
        }
    }


    public void setupDrawer() {
        if (getCurrentFragment() != null && getCurrentFragment().getClass() == TodayWeatherFragment.class) {
            mTxtDrawerToday.setTextColor(getResources().getColor(R.color.global_color_primary));
            mTxtDrawerForecast.setTextColor(getResources().getColor(R.color.global_text_primary));
        }
        if (getCurrentFragment() != null && getCurrentFragment().getClass() == ForecastWeatherFragment.class) {
            mTxtDrawerForecast.setTextColor(getResources().getColor(R.color.global_color_primary));
            mTxtDrawerToday.setTextColor(getResources().getColor(R.color.global_text_primary));
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    public void showErrorState() {
        mLayoutError.setVisibility(View.VISIBLE);
        mBtnReload.setVisibility(View.VISIBLE);
        mTxtErrorMessage.setVisibility(View.VISIBLE);
        if (!Utils.isNetworkAvailable()) {
            mTxtErrorMessage.setText(R.string.error_message_no_internet);
        } else if (!Utils.isLocationEnabled(getApplicationContext()) && !isDataLoaded()) {
            mTxtErrorMessage.setText(R.string.error_message_no_location);
            showEnableLocationDialog();
        } else {
            mTxtErrorMessage.setVisibility(View.INVISIBLE);
        }
        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        mLayoutContent.setVisibility(View.GONE);
        mLayoutProgress.setVisibility(View.GONE);
    }


    public void showNormalState() {
        mLayoutError.setVisibility(View.GONE);
        mLayoutContent.setVisibility(View.VISIBLE);
        mLayoutProgress.setVisibility(View.GONE);
    }


    public void showProgressState() {
        mLayoutError.setVisibility(View.GONE);
        mLayoutContent.setVisibility(View.GONE);
        mLayoutProgress.setVisibility(View.VISIBLE);
    }


    private boolean isDataLoaded() {
        return mLat != null && mLat != 0.0;
    }


    private void loadData() {
        if (Utils.isNetworkAvailable() && Utils.isLocationEnabled(getApplicationContext()) && !isDataLoaded()) {
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            if (!Utils.isNetworkAvailable() || location == null) {
                                showErrorState();
                                return;
                            }
                            mLat = location.getLatitude();
                            mLon = location.getLongitude();
                            getAndSaveLocationName(mLat, mLon);
                            mGoogleApiClient.disconnect();
                            mGoogleApiClient = null;
                            onDataLoaded();
                        }


                        @Override
                        public void onConnectionSuspended(int i) {
                            showErrorState();
                        }
                    })
                    .build();
            mGoogleApiClient.connect();
        } else {
            if (Utils.isNetworkAvailable() && isDataLoaded()) {
                showNormalState();
                onDataLoaded();
            } else {
                showErrorState();
            }
        }
    }


    private void onDataLoaded() {
        if (getCurrentFragment() == null) {
            replaceFragment(TodayWeatherFragment.newInstance(mLat, mLon, mCity, mCountry));
            showNormalState();
        } else {
            try {
                ((BaseFragment) getCurrentFragment()).loadData();
            } catch (ClassCastException ignored) {
            }
        }
    }


    private void getAndSaveLocationName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                if (mCity == null || mCity.length() == 0) {
                    mCity = addresses.get(0).getLocality();
                }
                if (mCity == null || mCity.length() == 0) {
                    mCity = addresses.get(0).getSubAdminArea();
                }
                if (mCountry == null || mCountry.length() == 0) {
                    mCountry = addresses.get(0).getCountryName();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showEnableLocationDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_LOCATION);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commit();
        GpsDisabledDialogFragment.newInstance().show(getFragmentManager(), DIALOG_LOCATION);
    }


    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }


    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }


    public Toolbar getToolbar() {
        return mToolbar;
    }


}
