package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    String name;
    DatabaseReference doctors;
    Intent intent;
    ArrayList<String> data;
    ArrayList<String> names;
    ArrayList<String> dr_usernames;
    ValueEventListener drListener;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_appt);

        lv = (ListView)findViewById(R.id.doctor_list);

        intent = getIntent();
        user = intent.getStringExtra("user");
        name = intent.getStringExtra("name");

        doctors = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctors");

        data = new ArrayList<String>();
        names = new ArrayList<String>();
        dr_usernames = new ArrayList<String>();


         drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String str = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: None";

                    if(ds.child("specialization").exists()){
                        str = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: " + ds.child("specialization").getValue().toString();
                    }

                    data.add(str);
                    names.add(ds.child("name").getValue().toString());
                    dr_usernames.add(ds.child("username").getValue().toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String dr_name = names.get(i);
                        String dr_username = dr_usernames.get(i);
                        Intent intent2 = new Intent(patient_book_appt.this, view_doctor_availability.class);
                        intent2.putExtra("doctor_username", dr_username);
                        intent2.putExtra("doctor_name", dr_name);
                        intent2.putExtra("user", user);
                        intent2.putExtra("name", name);
                        startActivity(intent2);
                    }
                });


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

        data.clear();
        names.clear();
        dr_usernames.clear();
        lv.setAdapter(null);

        drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("name").toString().toLowerCase().contains(query)){

                        String str = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: None";

                        if(ds.child("specialization").exists()){
                            str = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: " + ds.child("specialization").getValue().toString();
                        }

                        data.add(str);
                        names.add(ds.child("name").getValue().toString());
                        dr_usernames.add(ds.child("username").getValue().toString());
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String dr_name = names.get(i);
                        String dr_username = dr_usernames.get(i);
                        Intent intent2 = new Intent(patient_book_appt.this, view_doctor_availability.class);
                        intent2.putExtra("doctor_username", dr_username);
                        intent2.putExtra("doctor_name", dr_name);
                        intent2.putExtra("user", user);
                        intent2.putExtra("name", name);
                        startActivity(intent2);
                    }
                });
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

        data.clear();
        names.clear();
        dr_usernames.clear();
        lv.setAdapter(null);

        drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("specialization").toString().toLowerCase().contains(query)){
                        String str = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: None";

                        if(ds.child("specialization").exists()){
                            str = "Dr." + ds.child("name").getValue().toString() + ", \t Specialization: " + ds.child("specialization").getValue().toString();
                        }

                        data.add(str);
                        names.add(ds.child("name").getValue().toString());
                        dr_usernames.add(ds.child("username").getValue().toString());
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String dr_name = names.get(i);
                        String dr_username = dr_usernames.get(i);
                        Intent intent2 = new Intent(patient_book_appt.this, view_doctor_availability.class);
                        intent2.putExtra("doctor_username", dr_username);
                        intent2.putExtra("doctor_name", dr_name);
                        intent2.putExtra("user", user);
                        intent2.putExtra("name", name);
                        startActivity(intent2);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        doctors.addValueEventListener(drListener);
    }




}