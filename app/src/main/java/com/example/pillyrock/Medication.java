package com.example.pillyrock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Medication {
    public String medicationName;
    public String dose;
    public String dosesPerRefill;
    public List<String> times;
    public List<String> daysLong;
    public List<String> daysShort;
    public String notes;

    public Medication(JSONObject obj) {
        try {
            medicationName = obj.getString("medicationName");
            dose = obj.getString("dose");
            dosesPerRefill = obj.getString("dosesPerRefill");
            times = new ArrayList<String>();
            JSONArray jsonTimes = obj.getJSONArray("times");
            for (int i = 0; i < jsonTimes.length(); i++) {
                times.add(jsonTimes.getString(i));
            }
            daysLong = new ArrayList<String>();
            JSONArray jsonDaysLong = obj.getJSONArray("daysLong");
            for (int i = 0; i < jsonDaysLong.length(); i++) {
                daysLong.add(jsonDaysLong.getString(i));
            }
            daysShort = new ArrayList<String>();
            JSONArray jsonDaysShort = obj.getJSONArray("daysShort");
            for (int i = 0; i < jsonDaysShort.length(); i++) {
                daysShort.add(jsonDaysShort.getString(i));
            }
            notes = obj.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
