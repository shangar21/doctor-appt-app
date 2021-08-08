package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class doctor_set_availability extends AppCompatActivity {

    Intent i;
    String user;

    TimePicker ms, me, ts, te, ws, we, ths, the, fs, fe, ss, se, sus, sue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_set_availability);

        i = getIntent();
        user = i.getStringExtra("user");

        ms = (TimePicker)findViewById(R.id.mondayStartTime);
        me = (TimePicker)findViewById(R.id.mondayEndtTime);

        ts = (TimePicker)findViewById(R.id.tuesdayStartTime);
        te = (TimePicker)findViewById(R.id.tuesdayEndTime);

        ws = (TimePicker)findViewById(R.id.wednesdayStartTime);
        we = (TimePicker)findViewById(R.id.wednesdayEndTIme);

        ths = (TimePicker)findViewById(R.id.thursdayStartTime);
        the = (TimePicker)findViewById(R.id.thursdayEndTime);

        fs = (TimePicker)findViewById(R.id.fridayStartTime);
        fe = (TimePicker)findViewById(R.id.fridayEndTime);

        ss = (TimePicker)findViewById(R.id.saturdayStartTime);
        se = (TimePicker)findViewById(R.id.saturdayEndTime);

        sus = (TimePicker)findViewById(R.id.sundayStartTime);
        sue = (TimePicker)findViewById(R.id.sundayEndTime);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void submit(View view){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctorAvailability");

        Availability monday = new Availability(ms.getHour(), ms.getMinute(), me.getHour(), me.getMinute());
        Availability tuesday = new Availability(ts.getHour(), ts.getMinute(), te.getHour(), te.getMinute());
        Availability wednesday = new Availability(ws.getHour(), ws.getMinute(), we.getHour(), we.getMinute());
        Availability thursday = new Availability(ths.getHour(), ths.getMinute(), the.getHour(), the.getMinute());
        Availability friday = new Availability(fs.getHour(), fs.getMinute(), fe.getHour(), fe.getMinute());
        Availability saturday = new Availability(ss.getHour(), ss.getMinute(), se.getHour(), se.getMinute());
        Availability sunday = new Availability(sus.getHour(), sus.getMinute(), sue.getHour(), sue.getMinute());

        reference.child(user).child("monday").setValue(monday);
        reference.child(user).child("tuesday").setValue(tuesday);
        reference.child(user).child("wednesday").setValue(wednesday);
        reference.child(user).child("thursday").setValue(thursday);
        reference.child(user).child("friday").setValue(friday);
        reference.child(user).child("saturday").setValue(saturday);
        reference.child(user).child("sunday").setValue(sunday);

        Intent redir = new Intent(this, doctorHome.class);
        redir.putExtra("user", user);
        startActivity(redir);

    }
}