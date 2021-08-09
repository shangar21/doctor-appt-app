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

public class forgotPasswordDoctor extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DataSnapshot dataSnapshot;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_doctor);

    }

    public void Submit(View view){
        mDatabase = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctors");

        EditText username = findViewById(R.id.editTextTextUsername);
        EditText pass = findViewById(R.id.editTextTextPassword);

        ValueEventListener doctorlistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child(username.getText().toString()).exists()){
                    mDatabase.child(username.getText().toString()).child("password").setValue(pass.getText().toString());
                    Intent I =  new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(I);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        mDatabase.addListenerForSingleValueEvent(doctorlistener);
    }


}