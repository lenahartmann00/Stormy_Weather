package com.lenahartmann00.stormyweather.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.lenahartmann00.stormyweather.R;

public class AlterDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(getString(R.string.error_title)).
                setMessage(getString(R.string.error_message)).
                setPositiveButton(getString(R.string.error_button_ok), null);
        return builder.create();
    }
}
