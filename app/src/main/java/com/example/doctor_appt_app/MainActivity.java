package com.example.doctor_appt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        if(username.getText().toString().equals("patient") && password.getText().toString().equals("password")){
            Intent I = new Intent(this, patientHome.class);
            startActivity(I);
        }
        else if (username.getText().toString().equals("doctor") && password.getText().toString().equals("password")){
            Intent I = new Intent(this, doctorHome.class);
            startActivity(I);
        }
    }

    public void register(View view){
        Intent I = new Intent(this, Register.class);
        startActivity(I);
    }
}