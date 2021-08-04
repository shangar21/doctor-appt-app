package com.example.doctor_appt_app;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class doctor_view_appts extends AppCompatActivity {

    String username;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appts);

        intent = getIntent();
        username = intent.getStringExtra("user");
    }



}