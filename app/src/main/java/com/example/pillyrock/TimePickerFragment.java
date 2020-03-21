package com.example.pillyrock;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String formattedTime = "";
        String morningOrEve = hourOfDay >= 12 ? "PM" : "AM";
        int hourToUse = hourOfDay % 12;
        formattedTime += hourToUse + ":" + minute + morningOrEve + "\n";

        TextView timesTextView = getActivity().findViewById(R.id.timesTextView);
        String currentText = timesTextView.getText().toString();
        timesTextView.setText(currentText + formattedTime);
    }

}