package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity implements EventListAdapter.ItemClickListener {

    EventListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ArrayList<String> medicationNames = new ArrayList<>();
        medicationNames.add("test");
        medicationNames.add("test2");

        RecyclerView eventList = findViewById(R.id.eventList);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventListAdapter(this, medicationNames);
        adapter.setClickListener(this);
        eventList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        // Toast.makeText(this, "You clicked " + adapter.getMedicationName(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ViewEventActivity.class);
        intent.putExtra("eventIndex", position);
        startActivity(intent);
    }

    public void handleBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void handleDelete(View view) {

    }

    public void handleAdd(View view) {
        Intent intent = new Intent(this, AddEditEventActivity.class);
        intent.putExtra("activityName", "Add Event");
        startActivity(intent);
    }
}
