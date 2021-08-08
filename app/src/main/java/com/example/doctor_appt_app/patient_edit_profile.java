package com.example.doctor_appt_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class patient_edit_profile extends AppCompatActivity {

    User patient;
    DatabaseReference patients;
    EditText email;
    EditText healthcondition;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        email = (EditText)findViewById(R.id.edit_patient_profile_email);
        healthcondition = (EditText)findViewById(R.id.edit_patient_profile_healthcondition);

        patients = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("patients");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                patient = snapshot.child(username).getValue(User.class);

                email.setText(patient.getEmail());

                if(snapshot.child(username).child("healthcondition").exists()){
                    healthcondition.setText(snapshot.child(username).child("healthcondition").getValue().toString());
                }else{
                    healthcondition.setText("Enter healthcondition");
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        patients.addValueEventListener(listener);
    }

    public void submit(View view){
        patient.setHealthcondition(healthcondition.getText().toString());
        patient.setEmail(email.getText().toString());
        patients.child(username).child("email").setValue(patient.getEmail());
        patients.child(username).child("healthcondition").setValue(patient.getHealthcondition());
        Intent back = new Intent(this, patientHome.class);
        back.putExtra("user", username);
        startActivity(back);

    }

}
