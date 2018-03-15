package com.example.programmer.app_beta;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, year, month, day);

        return datePickerDialog;

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView textview = (TextView) getActivity().findViewById(R.id.dataText);

        textview.setText(day + "/" + (month + 1) + "/" + year);
    }

}
