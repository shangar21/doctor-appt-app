package com.example.doctor_appt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class doctorHome extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        TextView tv2 = (TextView)findViewById(R.id.textView2);
        tv2.setText("Hello, " + username);
    }

    public void view_appts(View view){
        Intent I = new Intent(this, doctor_view_appts.class);
        I.putExtra("user", username);
        startActivity(I);
    }

    public void set_availability(View view){
        Intent I = new Intent(this, doctor_set_availability.class);
        I.putExtra("user", username);
        startActivity(I);
    }

    public void change_profile(View view){
        Intent I = new Intent(this, doctor_edit_profile.class);
        I.putExtra("user", username);
        startActivity(I);
    }


}