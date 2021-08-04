package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class patient_view_appts extends AppCompatActivity {

    ListView lv;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_appts);

        lv = findViewById(R.id.listview1);

        items = new ArrayList<String>();

        Intent intent = getIntent();
        String username = intent.getStringExtra("user");

        DatabaseReference pDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointments");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child(username).exists()){
                    for (DataSnapshot ds : snapshot.child(username).getChildren()){
                        String data = ds.child("day").getValue().toString() + ", " + ds.child("month").getValue().toString() + ", " + ds.child("year").getValue().toString()
                                + ", with " + ds.child("dr_user_name").getValue().toString() + ", from " + ds.child("start_hour").getValue().toString() + ":00, to " + ds.child("end_hour").getValue().toString() + ":00";
                        items.add(data);
                    }
                }else{
                    items.add("No appointments yet");
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