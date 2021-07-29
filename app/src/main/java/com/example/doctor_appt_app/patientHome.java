package com.example.doctor_appt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class patientHome extends AppCompatActivity {

    ListView lv;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        lv = findViewById(R.id.listview1);

        items = new ArrayList<String>();

        items.add("Thursday, July 12th, 2022");
        items.add("Friday, July 13th, 2022");
        items.add("Saturday, July 14th, 2022");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);
    }
}