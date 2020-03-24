package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleEventList(View view) {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    public void handleNotifications(View view) {

    }

    public void handleCaretaker(View view) {
        Intent intent = new Intent(this, CaretakerActivity.class);
        startActivity(intent);
    }

    public void handleAddEvent(View view) {
        Intent intent = new Intent(this, AddEditEventActivity.class);
        intent.putExtra("activityName", "Add Event");
        startActivity(intent);
    }
}
