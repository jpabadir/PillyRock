package com.example.pillyrock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class CaretakerActivity extends AppCompatActivity
        implements DeleteCaretakerDialog.NoticeDialogListener {
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
            if(caretaker.getString("caretakerID").isEmpty()) {
                id.setText("No caretaker added");
                name.setText("");
                ((Button) findViewById(R.id.caretakerEdit)).setText("Add");
            } else {
                id.setText("Caretaker ID: " + caretaker.getString("caretakerID"));
                name.setText("Caretaker Name: " + caretaker.getString("caretakerName"));
                ((Button) findViewById(R.id.caretakerEdit)).setText("Edit");
            }
        } catch (FileNotFoundException e) {
            // send toast message to user if caretaker.json does not exist
            Context context = getApplicationContext();
            CharSequence msg = "No caretaker found";
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
        DeleteCaretakerDialog dialog = new DeleteCaretakerDialog();
        dialog.show(getSupportFragmentManager(), "deleteCaretaker");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        try {
            FileOutputStream outputStream = openFileOutput("caretaker.json", MODE_PRIVATE);
            JSONObject caretaker = new JSONObject();
            caretaker.put("caretakerID", "");
            caretaker.put("caretakerName", "");
            outputStream.write(caretaker.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // send toast to user
        Context context = getApplicationContext();
        CharSequence msg = "Caretaker deleted";
        int dur = Toast.LENGTH_SHORT;
        Toast.makeText(context, msg, dur).show();
        // reset fields to default values
        TextView id = findViewById(R.id.caretakerID);
        TextView name = findViewById(R.id.caretakerName);

        id.setText("No caretaker added");
        name.setText("");
        ((Button) findViewById(R.id.caretakerEdit)).setText("Add");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        // do nothing
    }

}


