package com.example.doctor_appt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class patientHome extends AppCompatActivity {

    String username;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);


        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        name = intent.getStringExtra("name");

        TextView text = (TextView)findViewById(R.id.greeting);
        String greeting = "Hello, "  + username;
        text.setText(greeting);


    }

    public void checkAppointments(View view){
        Intent I = new Intent(this, patient_view_appts.class);
        I.putExtra("user", username);
        startActivity(I);
    }

    public void bookAppointments(View view){
        Intent I = new Intent(this, patient_book_appt.class);
        I.putExtra("user", username);
        I.putExtra("name", name);

        startActivity(I);
    }

    public void pchange_profile(View view){
        Intent I = new Intent(this, patient_edit_profile.class);
        I.putExtra("user", username);
        startActivity(I);
    }
}