package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
        //error checking for patient login
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.equals("") || pass.equals(""))
                {
                    showAlertDialog(v);
                }
                else
                {
                    login(v);
                }
            }
        });

        //error checking for doctor login
        Button button_doc = (Button) findViewById(R.id.doctor_sign_in);
        button_doc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.equals("") || pass.equals(""))
                {
                    showAlertDialog(v);
                }
                else{
//                    ////////buggy!!!!!!!!!!!!!!!!
//                    int answer = checkpw(v);
//                    System.out.println(answer);
//                    if (answer == -99)
//                    {
//                        showAlertDialog1(v);
//                    }
//                    /////////////
                    drLogin(v);



                }
            }
        });
    }

    public void showAlertDialog(View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error");
        alert.setMessage("Username or password cannot be blank!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    }

    public void showAlertDialog1(View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error");
        alert.setMessage("Invalid password or username");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    }



    public void login(View view){
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        String user = username.getText().toString();
        String pass = password.getText().toString();
        DatabaseReference patients = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("patients");
        ValueEventListener patientListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull  DataSnapshot snapshot) {
                    Object password = snapshot.child(user).child("password").getValue();
                    if (password != null) {
                        password = password.toString();
                        if (password.equals(pass)){
                            Intent I = new Intent(MainActivity.this, patientHome.class);
                            User u = snapshot.child(user).getValue(User.class);
                            I.putExtra("user", u.getUsername());
                            I.putExtra("name", u.getName());
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

    public int checkpw(View view)
    {
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        String user = username.getText().toString();
        String pass = password.getText().toString();
        final int[] ans = {-99};
        DatabaseReference patients = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("patients");
        ValueEventListener drListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull  DataSnapshot snapshot) {

                Object password = snapshot.child(user).child("password").getValue();
                if (password != null) {
                    password = password.toString();
                    if (password.equals(pass)){
                        ans[0] = 1;
                    }

                    else
                    {
                        ans[0] = -1;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        };
        return ans[0];
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
                    Object password = snapshot.child(user).child("password").getValue();
                    if (password != null)
                    {
                        password = password.toString();
                        if (password.equals(pass)){
                            Intent I = new Intent(MainActivity.this, doctorHome.class);
                            Doctor d = snapshot.child(user).getValue(Doctor.class);
                            I.putExtra("user", d.getUsername());
                            startActivity(I);
                        }

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