package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class patient_book_appt extends AppCompatActivity {

    String user;
    DatabaseReference doctors;
    Intent intent;
    ArrayList<String> names;
    ValueEventListener drListener;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_appt);

        lv = (ListView)findViewById(R.id.doctor_list);

        intent = getIntent();
        user = intent.getStringExtra("user");

        doctors = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctors");

        names = new ArrayList<String>();

         drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String data = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: " + ds.child("specialization").getValue().toString();
                    names.add(data);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);

                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        doctors.addValueEventListener(drListener);


    }

    public void search_name(View view){
        EditText name_search = (EditText)findViewById(R.id.search_by_name);
        String query = name_search.getText().toString();

        names.clear();
        lv.setAdapter(null);

        drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("name").toString().toLowerCase().contains(query)){
                        String data = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: " + ds.child("specialization").getValue().toString();
                        names.add(data);
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);

                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        doctors.addValueEventListener(drListener);
    }

    public void search_specialization(View view){
        EditText name_search = (EditText)findViewById(R.id.search_by_specialization);
        String query = name_search.getText().toString();

        names.clear();
        lv.setAdapter(null);

        drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("specialization").toString().toLowerCase().contains(query)){
                        String data = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: " + ds.child("specialization").getValue().toString();
                        names.add(data);
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);

                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        doctors.addValueEventListener(drListener);
    }




}