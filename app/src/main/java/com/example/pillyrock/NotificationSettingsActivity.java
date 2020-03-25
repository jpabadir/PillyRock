package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class NotificationSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        setPreviousChoices();
    }

    public void setPreviousChoices() {
        try {
            FileInputStream inputStream = openFileInput("notification_settings.json");
            Scanner in = new Scanner(inputStream);
            JSONObject settings = new JSONObject(in.next());
            in.close();

            ((CheckBox) findViewById(R.id.email)).setChecked(settings.getBoolean("email"));
            ((CheckBox) findViewById(R.id.phone)).setChecked(settings.getBoolean("phone"));
            ((CheckBox) findViewById(R.id.sms)).setChecked(settings.getBoolean("sms"));
            ((CheckBox) findViewById(R.id.pushnotif)).setChecked(settings.getBoolean("pushnotif"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSaveClicked(View view) {
        JSONObject settings = settingsAsJSON();

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("notification_settings.json", Context.MODE_PRIVATE);
            outputStream.write(settings.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();
    }


    public void onCancelClicked(View view) {
        finish();
    }

    public JSONObject settingsAsJSON() {
        JSONObject settings = new JSONObject();

        Boolean email = ((CheckBox) findViewById(R.id.email)).isChecked();
        Boolean phone = ((CheckBox) findViewById(R.id.phone)).isChecked();
        Boolean sms = ((CheckBox) findViewById(R.id.sms)).isChecked();
        Boolean pushnotif = ((CheckBox) findViewById(R.id.pushnotif)).isChecked();

        try {
            settings.put("email", email);
            settings.put("phone", phone);
            settings.put("sms", sms);
            settings.put("pushnotif", pushnotif);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return settings;
    }
}
