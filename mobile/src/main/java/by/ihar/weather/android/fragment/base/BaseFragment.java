package by.ihar.weather.android.fragment.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import by.ihar.weather.android.R;
import by.ihar.weather.android.activity.WeatherActivity;
import by.ihar.weather.android.activity.base.BaseActivity;
import by.ihar.weather.android.dialog.AboutDialogFragment;
import by.ihar.weather.android.fragment.SettingsFragment;

/**
 * Base class for fragments.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            ((WeatherActivity) getActivity()).getSupportActionBar().setTitle(getTitle());
            ((WeatherActivity) getActivity()).setupDrawer();
            ((WeatherActivity) getActivity()).getDrawerToggle().setDrawerIndicatorEnabled(true);
            ((WeatherActivity) getActivity()).getDrawerToggle().syncState();
            ((WeatherActivity) getActivity()).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_today_weather, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                ((BaseActivity) getActivity()).replaceFragment(SettingsFragment.newInstance(), true);
                return true;
            case R.id.action_about:
                AboutDialogFragment.newInstance().show(getFragmentManager(), "About");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected abstract int getTitle();


    public abstract void loadData();


    // checks if layout is created and activity isn't null
    protected boolean isSafeState() {
        return getView() != null && getActivity() != null;
    }
}
