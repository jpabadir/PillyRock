package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventListActivity extends AppCompatActivity implements EventListAdapter.ItemClickListener {

    EventListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        List<Medication> medications = loadMedicationsFromJSON();

        RecyclerView eventList = findViewById(R.id.eventList);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventListAdapter(this, medications);
        adapter.setClickListener(this);
        eventList.setAdapter(adapter);
    }

    public List<Medication> loadMedicationsFromJSON() {
        JSONArray events = new JSONArray();
        try {
            FileInputStream inputStream = openFileInput("events.json");
            Scanner in = new Scanner(inputStream);
            String currentContents = "";
            while (in.hasNext()) {
                currentContents += in.next();
            }
            events = new JSONArray(currentContents);
            in.close();
        } catch (FileNotFoundException e) {
            // Events will remain an empty JSON array
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Medication> result = new ArrayList<>();
        try {
            for (int i = 0; i < events.length(); i ++) {
                JSONObject obj = events.getJSONObject(i);
                Medication medication = new Medication(obj);
                result.add(medication);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ViewEventActivity.class);
        intent.putExtra("eventIndex", position);
        startActivity(intent);
    }

    public void handleBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void handleDelete(View view) {
        Intent intent = new Intent(this, DeleteEventActivity.class);
        startActivity(intent);
    }

    public void handleAdd(View view) {
        Intent intent = new Intent(this, AddEditEventActivity.class);
        intent.putExtra("activityName", "Add Event");
        startActivity(intent);
    }
}
