package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class patient_view_doctor_info extends AppCompatActivity {
    String id;
    String patient_username;
    String doctor_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_doctor_info);

        Intent intent = getIntent();
        patient_username = intent.getStringExtra("user");
        doctor_username = intent.getStringExtra("doctor_username");
        id = intent.getStringExtra("appointment_id");

        DatabaseReference patients = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctors");

        ValueEventListener patientListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Doctor dr;
                if(snapshot.child(doctor_username).exists()){
                    dr = snapshot.child(doctor_username).getValue(Doctor.class);
                    TextView doctor_name = (TextView)findViewById(R.id.patient_view_doctor_name);
                    TextView doctor_gender = (TextView)findViewById(R.id.patient_view_doctor_gender);
                    TextView doctor_username = (TextView)findViewById(R.id.patient_view_doctor_username);
                    TextView doctor_email = (TextView)findViewById(R.id.patient_view_doctor_email);
                    TextView doctor_specialization = (TextView)findViewById(R.id.patient_view_doctor_specialization);

                    doctor_name.setText("Name: " + dr.getName());
                    doctor_gender.setText("Gender: " + dr.getGender());
                    doctor_username.setText("Username: " + dr.getUsername());
                    doctor_email.setText("Email: " + dr.getEmail());
                    doctor_specialization.setText("Specialization: " + dr.getSpecialization());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        patients.addValueEventListener(patientListener);
    }

    public void delete(View view){
        DatabaseReference patient_appts = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsPatient");
        DatabaseReference doctor_appts = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsDoctor");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child(patient_username).exists()){
                    snapshot.child(patient_username).child(id).getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        ValueEventListener listener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child(doctor_username).exists()){
                    snapshot.child(doctor_username).child(id).getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        patient_appts.addListenerForSingleValueEvent(listener);
        doctor_appts.addListenerForSingleValueEvent(listener2);

        Intent i = new Intent(this, patient_view_appts.class);
        i.putExtra("user", patient_username);
        startActivity(i);
    }
}