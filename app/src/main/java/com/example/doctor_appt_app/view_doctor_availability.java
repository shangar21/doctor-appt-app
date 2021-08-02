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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.Year;
import java.util.ArrayList;

public class view_doctor_availability extends AppCompatActivity {
    String [] MONTHS = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    ArrayList<Integer> day;
    ArrayList<Integer> year;
    ArrayList<Integer> start_hours;
    ArrayList<Integer> end_hours;
    String username;
    String dr_name;

    String m;
    int d;
    int y;
    int sh;
    int eh;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_availability);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        dr_name = intent.getStringExtra("doctor_name");

        TextView text = (TextView)findViewById(R.id.textView);
        String greeting = "Booking appointment for Dr."  + dr_name;
        text.setText(greeting);

        Spinner day_spinner = (Spinner)findViewById(R.id.day_spinner);
        Spinner month_spinner = (Spinner)findViewById(R.id.month_spinner);
        Spinner year_spinner = (Spinner)findViewById(R.id.year_spinner);
        Spinner start_time_spinner = (Spinner)findViewById(R.id.start_time_spinner);
        Spinner end_time_spinner = (Spinner)findViewById(R.id.end_time_spinner);

        writeArrays();

        ArrayAdapter<String> month_spinner_populate = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, MONTHS);
        ArrayAdapter<Integer> day_spinner_populate = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, day);
        ArrayAdapter<Integer> year_spinner_populate = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, year);
        ArrayAdapter<Integer> start_time_spinner_populate = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, start_hours);
        ArrayAdapter<Integer> end_time_spinner_populate = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, end_hours);

        day_spinner.setAdapter(day_spinner_populate);
        month_spinner.setAdapter(month_spinner_populate);
        year_spinner.setAdapter(year_spinner_populate);
        start_time_spinner.setAdapter(start_time_spinner_populate);
        end_time_spinner.setAdapter(end_time_spinner_populate);

        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                d = day.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                d = 1;
            }
        });

        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m = MONTHS[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                m = "jan";
            }
        });

        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                y = year.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                y = year.get(0);
            }
        });

        start_time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sh = start_hours.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sh = start_hours.get(0);
            }
        });

        end_time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eh = end_hours.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eh = end_hours.get(0);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void writeArrays(){
        day = new ArrayList<>();
        year = new ArrayList<>();
        start_hours = new ArrayList<>();
        end_hours = new ArrayList<>();

        for(int i=1;i<=31;i++)
            day.add(i);

        year.add(Year.now().getValue());
        year.add(Year.now().getValue() + 1);

        for(int i=9;i<=16;i++)
            start_hours.add(i);

        for(int i=10;i<=17;i++)
            end_hours.add(i);

    }

    public void submit(View view){
        Appointment a = new Appointment(sh, eh, d, m, y);
        a.setDr_user_name(dr_name);
        a.setPatient_user_name(username);

        DatabaseReference pDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointments");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.child(a.getPatient_user_name()).exists()){
                    pDatabaseReference.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount() + (long)1))).setValue(a);
                }
                else {
                    pDatabaseReference.child(a.getPatient_user_name()).child("1").setValue(a);
                }

                Intent i = new Intent(getApplicationContext(), patientHome.class);
                i.putExtra("user", username);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        pDatabaseReference.addListenerForSingleValueEvent(listener);

    }
}