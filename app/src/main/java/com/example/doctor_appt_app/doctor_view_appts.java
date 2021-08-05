package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        Calendar currentTime = new GregorianCalendar();
        int currentYear = currentTime.get(Calendar.YEAR);
        int currentWeek = currentTime.get(Calendar.WEEK_OF_YEAR);

        Intent intent = getIntent();
        String username = intent.getStringExtra("user");


        DatabaseReference dDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsDoctor");

        dDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<String> patient_names = new ArrayList<>();
                if(snapshot.child(username).exists()){
                    for(DataSnapshot ds : snapshot.child(username).getChildren()){
                        int apptYear = ds.child("year").getValue(Integer.class);
                        int apptWeek = ds.child("weekOfYear").getValue(Integer.class);
                        if(currentYear == apptYear && currentWeek == apptWeek){
                            String data = ds.child("day").getValue().toString() + ", " + ds.child("month").getValue().toString() + ", " + ds.child("year").getValue().toString()
                                    + ", with " + ds.child("patient_name").getValue().toString() + ", from " + ds.child("start_hour").getValue().toString() + ":00, to " + ds.child("end_hour").getValue().toString() + ":00";
                            items.add(data);
                            patient_names.add(ds.child("patient_user_name").getValue().toString());
                        }
                    }
                }else{
                    items.add("No appointments yet");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent vi = new Intent(getApplicationContext(), doctor_view_patient_info.class);
                            vi.putExtra("patient_username", patient_names.get(i));
                            startActivity(vi);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



}