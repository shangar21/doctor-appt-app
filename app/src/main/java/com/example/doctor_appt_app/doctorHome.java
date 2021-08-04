package com.example.doctor_appt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class doctorHome extends AppCompatActivity {

    Intent intent;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        intent = getIntent();
        username = intent.getStringExtra("user");
    }

    public void view_appts(View view){
        Intent I = new Intent(this, doctor_view_appts.class);
        I.putExtra("user", username);
        startActivity(I);
    }


}