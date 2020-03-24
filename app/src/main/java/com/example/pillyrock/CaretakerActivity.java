package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CaretakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);
        // Read from JSON file (if it exists, otherwise send a toast message)
        try {
            FileInputStream inputStream = new FileInputStream("caretaker.json");
            Scanner in = new Scanner(inputStream);
            String json = new String();
            while (in.hasNext()) {
                json += in.next();
            }
            JSONObject caretaker = new JSONObject();
        } catch (FileNotFoundException e) {
            //TODO send toast message to user
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Set textViews to contents of JSON
    }
    public void handleEditCaretaker(View view) {
        Intent intent = new Intent(this, EditCaretakerActivity.class);
        startActivity(intent);
    }
}
