package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.Scanner;

public class ViewEventActivity extends AppCompatActivity {
    int eventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        Intent intent = getIntent();
        eventIndex = intent.getIntExtra("eventIndex", -1);

        setInfoOfEvent();
    }

    public void onBackClicked(View v) {
        finish();
    }

    public void onEditClicked(View v) {
        Intent intent = new Intent(this, AddEditEventActivity.class);
        intent.putExtra("activityName", "Edit Event");
        intent.putExtra("eventIndex", eventIndex);
        startActivity(intent);
    }

    public void setInfoOfEvent() {
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
