package by.ihar.weather.android.activity.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Base class for activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, false);
    }


    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainerId(), fragment, ((Object) fragment).getClass().getName());
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    @Nullable
    public Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(getFragmentContainerId());
    }


    protected abstract int getFragmentContainerId();


}
