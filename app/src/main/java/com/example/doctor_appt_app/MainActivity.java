package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        String user = username.getText().toString();
        String pass = password.getText().toString();

        DatabaseReference patients = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("patients");

        ValueEventListener patientListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.child(user).exists()){
                    String password = snapshot.child(user).child("password").getValue().toString();
                    if (password.equals(pass)){
                        Intent I = new Intent(MainActivity.this, patientHome.class);
                        User u = snapshot.child(user).getValue(User.class);
                        I.putExtra("user", u.getUsername());
                        startActivity(I);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        patients.addValueEventListener(patientListener);

    }

    public void drLogin(View view){
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        String user = username.getText().toString();
        String pass = password.getText().toString();

        DatabaseReference doctors = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctors");


        ValueEventListener drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child(user).exists()){
                    String password = snapshot.child(user).child("password").getValue().toString();
                    if (password.equals(pass)){
                        Intent I = new Intent(MainActivity.this, doctorHome.class);
                        Doctor d = snapshot.child(user).getValue(Doctor.class);
                        I.putExtra("user", d.getUsername());
                        startActivity(I);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };

        doctors.addValueEventListener(drListener);
    }

    public void register(View view){
        Intent I = new Intent(this, Register.class);
        startActivity(I);
    }


}