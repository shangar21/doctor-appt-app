package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class doctor_view_appts extends AppCompatActivity {

    ListView lv;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appts);

        lv = findViewById(R.id.listview2);

        items = new ArrayList<String>();

        Intent intent = getIntent();
        String username = intent.getStringExtra("user");

        DatabaseReference pDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsDoctor");

        ValueEventListener listener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Calendar calendar = new GregorianCalendar();
                int week_of_year = calendar.get(Calendar.WEEK_OF_YEAR) - 2;

                if(snapshot.child(username).exists()){
                    for (DataSnapshot ds : snapshot.child(username).getChildren()){
                        if(String.valueOf(week_of_year).equals(ds.child("weekOfYear").getValue().toString())){
                            String data = ds.child("day").getValue().toString() + ", " + ds.child("month").getValue().toString() + ", " + ds.child("year").getValue().toString()
                                    + ", with " + ds.child("patient_name").getValue().toString() + ", from " + ds.child("start_hour").getValue().toString() + ":" + ds.child("start_minute").getValue().toString() +
                                    ", to " + ds.child("end_hour").getValue().toString() + ":" + ds.child("end_minute").getValue().toString();
                            items.add(data);
                        }else{
                            items.add("No appointments this week. " + String.valueOf(week_of_year) + ", " + ds.child("weekOfYear").getValue().toString());
                        }
                    }
                }else{
                    items.add("No appointments have been booked yet");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        pDatabaseReference.addValueEventListener(listener);
    }



}