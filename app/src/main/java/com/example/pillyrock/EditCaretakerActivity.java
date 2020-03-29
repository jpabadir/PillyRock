package com.example.pillyrock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.FileOutputStream;

public class EditCaretakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caretaker);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView id = findViewById(R.id.caretakerID);
        TextView name = findViewById(R.id.caretakerName);

        if(bundle.getString("caretakerID").equals("No caretaker added")) {
            id.setText("");
        } else {
            id.setText(bundle.getString("caretakerID"));
            name.setText(bundle.getString("caretakerName"));
        }
    }

    public void onCancelClicked(View v) {
        finish();
    }

    public void onSaveClicked(View v) {
        EditText id = findViewById(R.id.caretakerID);
        String caretakerID = id.getText().toString();

        EditText name = findViewById(R.id.caretakerName);
        String caretakerName = name.getText().toString();

        if(caretakerID.isEmpty() || caretakerName.isEmpty()) {
            Context context = getApplicationContext();
            CharSequence msg = "Fields cannot be empty";
            int dur = Toast.LENGTH_LONG;
            Toast.makeText(context, msg, dur).show();
        } else {
            JSONObject caretaker;
            try {
                FileOutputStream outputStream = openFileOutput("caretaker.json", MODE_PRIVATE);
                caretaker = createJSONCaretakerFromInput();
                outputStream.write(caretaker.toString().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
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
