package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class doctor_edit_profile extends AppCompatActivity {

    Doctor doctor;
    DatabaseReference doctors;
    EditText email;
    EditText specialization;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        email = (EditText)findViewById(R.id.edit_doctor_profile_email);
        specialization = (EditText)findViewById(R.id.edit_doctor_profile_specialization);

        doctors = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctors");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               doctor = snapshot.child(username).getValue(Doctor.class);

               email.setText(doctor.getEmail());

               if(snapshot.child(username).child("specialization").exists()){
                   specialization.setText(snapshot.child(username).child("specialization").getValue().toString());
               }else{
                   specialization.setText("Enter Specialization");
               }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        doctors.addValueEventListener(listener);
    }

    public void submit(View view){
        doctor.setSpecialization(specialization.getText().toString());
        doctor.setEmail(email.getText().toString());
        doctors.child(username).child("email").setValue(doctor.getEmail());
        doctors.child(username).child("Specialization").setValue(doctor.getSpecialization());
        Intent back = new Intent(this, doctorHome.class);
        back.putExtra("user", username);
        startActivity(back);

    }
}