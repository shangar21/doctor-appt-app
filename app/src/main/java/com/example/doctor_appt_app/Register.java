package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Register extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DataSnapshot dataSnapshot;
    RadioButton male ;
    RadioButton female ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         male = (RadioButton) findViewById(R.id.register_male);
         female = (RadioButton) findViewById(R.id.register_female);
        male.setOnCheckedChangeListener(this::onCheckedChanged);
        female.setOnCheckedChangeListener(this::onCheckedChanged);
    }
    //@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(isChecked){
            disableOthers(buttonView.getId());
             buttonView.setTextColor(getResources().getColor(R.color.design_default_color_primary));
        }else{
            buttonView.setTextColor(getResources().getColor(R.color.design_default_color_primary_dark));
        }

    }
    private void disableOthers(int viewId){
        if(R.id.register_male!=viewId && male.isChecked()){
            male.setChecked(false);
        }
        if(R.id.register_female!=viewId && female.isChecked()){
            female.setChecked(false);
        }
    }

    public void showAlertDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error");
        alert.setMessage("Username has already been used, please try again");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.show();
        return;
    }
    public void submit(View view) {
        database = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/");

        EditText name = (EditText) findViewById(R.id.register_name);
        EditText email = (EditText) findViewById(R.id.register_email);
        EditText username = (EditText) findViewById(R.id.register_username);
        EditText password = (EditText) findViewById(R.id.register_password);
        DatePicker picker = (DatePicker)findViewById(R.id.datePickerBirthday);
        Switch isDoctor = (Switch) findViewById(R.id.register_doctor);
      //  RadioButton male = (RadioButton) findViewById(R.id.register_male);
      //  RadioButton female = (RadioButton) findViewById(R.id.register_female);
        String gender = male.isChecked() ? "male" : "female";
        String usern = username.getText().toString();
        String emailn = email.getText().toString();
        DatabaseReference patients = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("patients");

        if(!isDoctor.isChecked()) {
            ValueEventListener patientListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (password.getText().length() != 0) {
                        if (snapshot.child(usern).exists()) {
                            showAlertDialog();
                            return;

                        }

                        else
                        {
                            mDatabase = database.getReference("patients");
                            User user = new User(name.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString(), gender);
                            user.setBirth_day(picker.getDayOfMonth());
                            user.setBirth_month(picker.getMonth());
                            user.setBirth_year(picker.getYear());
                            mDatabase.child(user.getUsername()).setValue(user);
                            Intent I = new Intent(Register.this, patientHome.class);
                            I.putExtra("user", user.getUsername());
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
        }else {


            DatabaseReference doctors = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctors");

            ValueEventListener doctorsListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (password.getText().length() != 0) {
                        if (!snapshot.child(usern).exists()) {
                            mDatabase = database.getReference("doctors");
                            Doctor doctor = new Doctor(name.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString(), gender);
                            doctor.setBirth_day(picker.getDayOfMonth());
                            doctor.setBirth_month(picker.getMonth());
                            doctor.setBirth_year(picker.getYear());
                            mDatabase.child(doctor.getUsername()).setValue(doctor);
                            Intent I = new Intent(Register.this, doctorHome.class);
                            I.putExtra("user", doctor.getUsername());
                            startActivity(I);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    System.out.println(error);
                }
            };
            doctors.addValueEventListener(doctorsListener);
        }
    }
}