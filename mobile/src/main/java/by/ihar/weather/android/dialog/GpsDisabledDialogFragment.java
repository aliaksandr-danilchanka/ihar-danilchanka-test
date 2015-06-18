package by.ihar.weather.android.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import by.ihar.weather.android.R;

public class GpsDisabledDialogFragment extends DialogFragment {

    public static GpsDisabledDialogFragment newInstance() {
        GpsDisabledDialogFragment fragment = new GpsDisabledDialogFragment();
        return fragment;
    }


    public GpsDisabledDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.gps_disabled_error_title)
                .setMessage(R.string.gps_disabled_error_message)
                .setPositiveButton(R.string.settings,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .create();
    }
}
