package myApp.michal.crossNote.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import myApp.michal.app_02.R;

import java.util.Calendar;
import java.text.DateFormatSymbols;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance(Locale.US);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, this, year, month, day);
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView textview = getActivity().findViewById(R.id.dataData);
        String m = new DateFormatSymbols().getShortMonths()[month];
        textview.setText(day + " " + m + " " + year);
    }
}
