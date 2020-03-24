package com.example.pillyrock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class EditCaretakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caretaker);
    }
    public void onDoneClicked(View v) {
        JSONObject caretaker = new JSONObject();
        try {
            FileOutputStream outputStream = openFileOutput("caretaker.json", MODE_PRIVATE);
            caretaker = createJSONCaretakerFromInput();
            outputStream.write(caretaker.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    private JSONObject createJSONCaretakerFromInput() {
        EditText id = findViewById(R.id.caretakerID);
        String caretakerID = id.getText().toString();

        EditText name = findViewById(R.id.caretakerName);
        String caretakerName = name.getText().toString();

        JSONObject caretaker = new JSONObject();
        try{
            caretaker.put("caretakerID", caretakerID);
            caretaker.put("caretakerName", caretakerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caretaker;
    }
}
