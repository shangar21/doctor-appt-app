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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class view_doctor_availability extends AppCompatActivity {
    String [] MONTHS = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    ArrayList<Integer> day;
    ArrayList<Integer> year;
    ArrayList<Integer> start_hours;
    ArrayList<Integer> end_hours;
    String dr_username;
    String username;
    String dr_name;
    String name;

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
        dr_username = intent.getStringExtra("doctor_username");
        username = intent.getStringExtra("user");
        dr_name = intent.getStringExtra("doctor_name");
        name = intent.getStringExtra("name");


        TextView text = (TextView)findViewById(R.id.textView);
        String greeting = "Booking appointment for Dr."  + dr_name;
        text.setText(greeting);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void submit(View view){

        DatePicker picker = (DatePicker)findViewById(R.id.datePicker1);
        TimePicker start = (TimePicker)findViewById(R.id.timePicker1);
        TimePicker end = (TimePicker) findViewById(R.id.timePicker2);

        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year = picker.getYear();
        int start_hour = start.getHour();
        int start_minute = start.getMinute();
        int end_hour = end.getHour();
        int end_minute = end.getMinute();

        Appointment a = new Appointment(start_hour, end_hour, day, MONTHS[month], year);


        a.setDr_user_name(dr_username);
        a.setPatient_user_name(username);
        a.setDr_name(dr_name);
        a.setPatient_name(name);
        a.setStart_minute(start_minute);
        a.setEnd_minute(end_minute);

        Calendar clnd1 = new GregorianCalendar(y, month, d);

        int woy = clnd1.get(Calendar.WEEK_OF_YEAR);

        a.setWeekOfYear(woy);

        DatabaseReference pDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsPatient");
        DatabaseReference dDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsDoctor");

        pDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                i.putExtra("name", name);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(a.getDr_user_name()).exists()){
                    dDatabaseReference.child(a.getDr_user_name()).child(String.valueOf((int)(snapshot.child(a.getDr_user_name()).getChildrenCount() + (long)1))).setValue(a);
                }
                else {
                    dDatabaseReference.child(a.getDr_user_name()).child("1").setValue(a);
                }

                Intent i = new Intent(getApplicationContext(), patientHome.class);
                i.putExtra("user", username);
                i.putExtra("name", name);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }
}