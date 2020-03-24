package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeleteEventActivity extends AppCompatActivity implements DeleteListAdapter.ItemClickListener {

    DeleteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);

        List<Medication> medications = loadMedicationsFromJSON();

        RecyclerView eventList = findViewById(R.id.deleteList);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeleteListAdapter(this, medications);
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
        adapter.toggleEventSelected(position);
    }

    public void handleBack(View view) {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    public void handleDelete(View view) {
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
            // Events will remain an empty JSON array
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int idx = 0;
        while (idx < events.length()) {
            if (adapter.isEventSelected(idx)) {
                events.remove(idx);
                adapter.removeEvent(idx);
            } else {
                idx++;
            }
        }

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput("events.json", Context.MODE_PRIVATE);
            outputStream.write(events.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
