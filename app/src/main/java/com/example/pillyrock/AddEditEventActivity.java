package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AddEditEventActivity extends AppCompatActivity {
    int eventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("activityName"));
        if (intent.getStringExtra("activityName").equals("Edit Event")) {
            eventIndex = intent.getIntExtra("eventIndex", -1);
            setInfoOfEvent();
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onCancelClicked(View v) {
        startEventListActivity();
    }

    public void onSaveClicked(View v) {
        if (!getFirstEmptyRequiredField().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a " + getFirstEmptyRequiredField(), Toast.LENGTH_SHORT).show();
        } else {
            JSONArray events = new JSONArray();
            try {
                FileInputStream inputStream = openFileInput("events.json");
                Scanner in = new Scanner(inputStream);
                String currentContents = "";
                while (in.hasNext()) {
                    currentContents += in.next() + " ";
                }

                events = new JSONArray(currentContents);
                in.close();
            } catch (FileNotFoundException e) {
                // This will happen if this is the user's first event. In that case,
                // events will remain an empty JSON array.
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject event = buildJSONEventFromUserInput();
            events.put(event);

            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput("events.json", Context.MODE_PRIVATE);
                outputStream.write(events.toString().getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            startEventListActivity();
        }
    }

    public String getFirstEmptyRequiredField() {
        String medicationName = ((TextView) findViewById(R.id.medicationName)).getText().toString();
        String[] times = getTimes();
        String[] daysLong = getDaysLong();

        if (medicationName.isEmpty()) {
            return "Medication";
        }
        if (times[0].equals("noTimes")) {
            return "Time";
        }
        if (daysLong.length == 0) {
            return "Day";
        }
        return "";
    }

    public JSONObject buildJSONEventFromUserInput() {
        String medicationName = ((TextView) findViewById(R.id.medicationName)).getText().toString();
        String dose = ((TextView) findViewById(R.id.dose)).getText().toString();
        String dosesPerRefill = ((TextView) findViewById(R.id.dosesPerRefill)).getText().toString();
        String notes = ((TextView) findViewById(R.id.notesTextView)).getText().toString();

        String[] daysLong = getDaysLong();
        String[] daysShort = getDaysShort();
        String[] times = getTimes();

        JSONObject event = new JSONObject();

        try {
            event.put("medicationName", medicationName);
            event.put("dose", dose);
            event.put("dosesPerRefill", dosesPerRefill);
            event.put("times", new JSONArray(times));
            event.put("daysLong", new JSONArray(daysLong));
            event.put("daysShort", new JSONArray(daysShort));
            event.put("notes", notes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }

    public void startEventListActivity() {
        // Note to TA: I'm aware we should use finish() to avoid creating a stack of activities, but finish()
        // will show a different activity depending on which activity led us to the current one.
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    public String[] getDaysLong() {
        ArrayList<String> daysLong = new ArrayList<>();

        Boolean monday = ((CheckBox) findViewById(R.id.monday)).isChecked();
        Boolean tuesday = ((CheckBox) findViewById(R.id.tuesday)).isChecked();
        Boolean wednesday = ((CheckBox) findViewById(R.id.wednesday)).isChecked();
        Boolean thursday = ((CheckBox) findViewById(R.id.thursday)).isChecked();
        Boolean friday = ((CheckBox) findViewById(R.id.friday)).isChecked();
        Boolean saturday = ((CheckBox) findViewById(R.id.saturday)).isChecked();
        Boolean sunday = ((CheckBox) findViewById(R.id.sunday)).isChecked();

        if (monday) {
            daysLong.add("Monday");
        }
        if (tuesday) {
            daysLong.add("Tuesday");
        }
        if (wednesday) {
            daysLong.add("Wednesday");
        }
        if (thursday) {
            daysLong.add("Thursday");
        }
        if (friday) {
            daysLong.add("Friday");
        }
        if (saturday) {
            daysLong.add("Saturday");
        }
        if (sunday) {
            daysLong.add("Sunday");
        }

        return daysLong.toArray(new String[daysLong.size()]);
    }

    public String[] getDaysShort() {
        ArrayList<String> daysShort = new ArrayList<>();
        String[] daysLong = getDaysLong();

        for (int i = 0; i < daysLong.length; i++) {
            daysShort.add(daysLong[i].substring(0, 2));
        }

        return daysShort.toArray(new String[daysShort.size()]);
    }

    public String[] getTimes() {
        String times = ((TextView) findViewById(R.id.timesTextView)).getText().toString();
        if (times.isEmpty()) {
            times = "noTimes";
        }
        return times.split("\n");
    }

    public void setInfoOfEvent() {
        // System.out.println(eventIndex);
        try {
            FileInputStream inputStream = openFileInput("events.json");
            Scanner in = new Scanner(inputStream);
            String currentContents = "";
            while (in.hasNext()) {
                currentContents += in.next() + " ";
            }

            JSONArray events = new JSONArray(currentContents);
            in.close();

            JSONObject myEvent = (JSONObject) (events.get(eventIndex));

            ((TextView) findViewById(R.id.medicationName)).setText(myEvent.get("medicationName").toString());
            ((TextView) findViewById(R.id.dose)).setText(myEvent.get("dose").toString());
            ((TextView) findViewById(R.id.dosesPerRefill)).setText(myEvent.get("dosesPerRefill").toString());

            String[] times = getArrayFromJSONArray(myEvent.getJSONArray("times"));
            String formattedTimes = String.join("\n", times);
            ((TextView) findViewById(R.id.timesTextView)).setText(formattedTimes);


            String[] daysLong = getArrayFromJSONArray(myEvent.getJSONArray("daysLong"));
            setCheckBoxesStatusFromEventInfo(daysLong);

            ((TextView) findViewById(R.id.notesTextView)).setText(myEvent.get("notes").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getArrayFromJSONArray(JSONArray jsonArray) {
        String[] array = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                array[i] = (String) jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return array;
    }

    public void setCheckBoxesStatusFromEventInfo(String[] daysLong) {
        for (int i = 0; i < daysLong.length; i++) {
            switch (daysLong[i]) {
                case "Monday":
                    ((CheckBox) findViewById(R.id.monday)).setChecked(true);
                    break;
                case "Tuesday":
                    ((CheckBox) findViewById(R.id.tuesday)).setChecked(true);
                    break;
                case "Wednesday":
                    ((CheckBox) findViewById(R.id.wednesday)).setChecked(true);
                    break;
                case "Thursday":
                    ((CheckBox) findViewById(R.id.thursday)).setChecked(true);
                    break;
                case "Friday":
                    ((CheckBox) findViewById(R.id.friday)).setChecked(true);
                    break;
                case "Saturday":
                    ((CheckBox) findViewById(R.id.saturday)).setChecked(true);
                    break;
                case "Sunday":
                    ((CheckBox) findViewById(R.id.sunday)).setChecked(true);
                    break;
            }
        }
    }
}
