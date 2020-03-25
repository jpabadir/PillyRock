package com.example.pillyrock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CaretakerActivity extends AppCompatActivity {

    JSONObject caretaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);
        setCaretaker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCaretaker();
    }

    private void setCaretaker() {
        // Read from JSON file (if it exists)
        try {
            FileInputStream inputStream = openFileInput("caretaker.json");
            Scanner in = new Scanner(inputStream);
            String json = in.nextLine();
            in.close();
            caretaker = new JSONObject(json);

            TextView id = findViewById(R.id.caretakerID);
            TextView name = findViewById(R.id.caretakerName);

            id.setText(caretaker.getString("caretakerID"));
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
        try {
            Intent intent = new Intent(this, EditCaretakerActivity.class);
            Bundle bundle = new Bundle();

            TextView id = findViewById(R.id.caretakerID);
            TextView name = findViewById(R.id.caretakerName);

            bundle.putString("caretakerID", id.getText().toString());
            bundle.putString("caretakerName", name.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickDone(View view) {
        finish();
    }

    public void onClickDelete(View view) {
        // delete the caretaker.json data
        File caretaker = new File("caretaker.json");
        caretaker.delete();
        // send toast to user
        Context context = getApplicationContext();
        CharSequence msg = "Caretaker deleted";
        int dur = Toast.LENGTH_SHORT;
        Toast.makeText(context, msg, dur).show();
        // reset fields to default values
        TextView id = findViewById(R.id.caretakerID);
        TextView name = findViewById(R.id.caretakerName);

        id.setText("Enter Caretaker ID");
        name.setText("Enter Caretaker Name");

    }
}
