package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CaretakerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);
        // Read from JSON file (if it exists)
        try {
            FileInputStream inputStream = new FileInputStream("caretaker.json");
            Scanner in = new Scanner(inputStream);
            String json = new String();
            while (in.hasNext()) {
                json += in.next();
            }
            JSONObject caretaker = new JSONObject();

            TextView id = findViewById(R.id.caretakerID);
            TextView name = findViewById(R.id.caretakerName);

            id.setText(caretaker.getString("caretakerId"));
            name.setText(caretaker.getString("caretakerName"));
        } catch (FileNotFoundException e) {
            // send toast message to user if caretaker.json does not exist
            Context context = getApplicationContext();
            CharSequence msg = "No caretaker found, please set a caretaker";
            int dur = Toast.LENGTH_SHORT;
            Toast.makeText(context, msg, dur).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleEditCaretaker(View view) {
        Intent intent = new Intent(this, EditCaretakerActivity.class);
        startActivity(intent);
    }
}
