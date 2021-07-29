package com.example.doctor_appt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void submit(View view){
        database = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/");

        EditText name = (EditText)findViewById(R.id.register_name);
        EditText email = (EditText)findViewById(R.id.register_email);
        EditText username = (EditText)findViewById(R.id.register_username);
        EditText password = (EditText)findViewById(R.id.register_password);
        Switch isDoctor = (Switch)findViewById(R.id.register_doctor);
        RadioButton male = (RadioButton)findViewById(R.id.register_male);

        String gender = male.isChecked() ? "male" : "female";

        if(isDoctor.isChecked()){
            mDatabase = database.getReference("doctors");
            Doctor doctor = new Doctor(name.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString(), gender);
            mDatabase.child(doctor.getUsername()).setValue(doctor);
            Intent I = new Intent(this, doctorHome.class);
            startActivity(I);
        }
        mDatabase = database.getReference("patients");
        User user = new User(name.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString(), gender);
        mDatabase.child(user.getUsername()).setValue(user);
        Intent I = new Intent(this, patientHome.class);
        startActivity(I);
    }
}