package com.jason.usedcar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

/**
 * @author t77yq @2014-08-06.
 */
public class DatePicker extends DialogFragment {

    private OnDateSetListener listener;

    public DatePicker setListener(final OnDateSetListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                new OnDateSetListener() {
                    @Override
                    public void onDateSet(final android.widget.DatePicker view, final int year,
                                          final int monthOfYear, final int dayOfMonth) {
                        listener.onDateSet(view, year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "完成", dialog);
        return dialog;
    }
}
