package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class doctor_view_patient_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_patient_info);

        Intent intent = getIntent();
        String user = intent.getStringExtra("patient_username");

        DatabaseReference patients = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("patients");

        ValueEventListener patientListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User u;
                if(snapshot.child(user).exists()){
                    u = snapshot.child(user).getValue(User.class);
                    TextView patient_name = (TextView)findViewById(R.id.doctor_view_patient_name);
                    TextView patient_gender = (TextView)findViewById(R.id.doctor_view_patient_gender);
                    TextView patient_username = (TextView)findViewById(R.id.doctor_view_patient_username);
                    TextView patient_email = (TextView)findViewById(R.id.doctor_view_patient_email);
                    TextView patient_birthday = (TextView)findViewById(R.id.doctor_view_patient_birthday);

                    patient_name.setText("Name: " + u.getName());
                    patient_gender.setText("Gender: " + u.getGender());
                    patient_username.setText("Username: " + u.getUsername());
                    patient_email.setText("Email: " + u.getEmail());
                    patient_birthday.setText("Birthday: " + String.valueOf(u.birth_day) + "/" + String.valueOf(u.birth_month + 1) + "/" + String.valueOf(u.birth_year));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        patients.addValueEventListener(patientListener);
    }
}