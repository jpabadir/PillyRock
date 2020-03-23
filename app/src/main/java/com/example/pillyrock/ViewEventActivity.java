package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public void onDoneClicked(View v) {
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

            String[] days = getArrayFromJSONArray(myEvent.getJSONArray("daysLong"));
            String formattedDays = String.join(", ", days);
            ((TextView) findViewById(R.id.daysOfWeekTextView)).setText(formattedDays);

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
}
