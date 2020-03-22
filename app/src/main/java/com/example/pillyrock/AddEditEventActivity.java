package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AddEditEventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onSaveClicked(View v) {
        JSONObject event = getJSONEvent();

        JSONArray events = new JSONArray();
        events.put(event);


        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream = openFileOutput("data.json", Context.MODE_APPEND);
            outputStream.write(events.toString().getBytes());
            outputStream.close();

            // close app and display a toast to confirm that we are writing the info to a files
            // finish();
            Toast.makeText(getApplicationContext(), "Writing info to file", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JSONObject getJSONEvent() {
        String medicationName = ((TextView) findViewById(R.id.medicationName)).getText().toString();
        String dose = ((TextView) findViewById(R.id.dose)).getText().toString();
        String dosesPerRefill = ((TextView) findViewById(R.id.dosesPerRefill)).getText().toString();
        String times = ((TextView) findViewById(R.id.timesTextView)).getText().toString();
        Boolean monday = ((CheckBox) findViewById(R.id.monday)).isChecked();
        Boolean tuesday = ((CheckBox) findViewById(R.id.tuesday)).isChecked();
        Boolean wednesday = ((CheckBox) findViewById(R.id.wednesday)).isChecked();
        Boolean thursday = ((CheckBox) findViewById(R.id.thursday)).isChecked();
        Boolean friday = ((CheckBox) findViewById(R.id.friday)).isChecked();
        Boolean saturday = ((CheckBox) findViewById(R.id.saturday)).isChecked();
        Boolean sunday = ((CheckBox) findViewById(R.id.sunday)).isChecked();
        String notes = ((TextView) findViewById(R.id.notesTextView)).getText().toString();

        JSONObject event = new JSONObject();

        try {
            event.put("medicationName", medicationName);
            event.put("dose", dose);
            event.put("dosesPerRefill", dosesPerRefill);
            event.put("times", times);
            event.put("monday", monday);
            event.put("tuesday", tuesday);
            event.put("wednesday", wednesday);
            event.put("thursday", thursday);
            event.put("friday", friday);
            event.put("saturday", saturday);
            event.put("sunday", sunday);
            event.put("notes", notes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }
}
