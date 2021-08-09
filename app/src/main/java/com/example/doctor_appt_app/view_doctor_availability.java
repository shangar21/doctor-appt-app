package com.example.doctor_appt_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class view_doctor_availability extends AppCompatActivity {
    String [] MONTHS = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    ArrayList<Integer> day;
    ArrayList<Integer> year;
    ArrayList<Integer> start_hours;
    ArrayList<Integer> end_hours;
    ArrayList<Availability> availabilities;
    String dr_username;
    String username;
    String dr_name;
    String name;

    String m;
    int d;
    int y;
    int sh;
    int eh;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_availability);

        Intent intent = getIntent();
        dr_username = intent.getStringExtra("doctor_username");
        username = intent.getStringExtra("user");
        dr_name = intent.getStringExtra("doctor_name");
        name = intent.getStringExtra("name");
        availabilities = new ArrayList<>();

        DatabaseReference avail = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("doctorAvailability");

        ValueEventListener avail_listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.child(dr_username).exists()){
                    String sunday = "Sunday: " + snapshot.child(dr_username).child("sunday").child("start_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("sunday").child("start_minute").getValue().toString() + ", to "
                            + snapshot.child(dr_username).child("sunday").child("end_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("sunday").child("end_minute").getValue().toString() + "\n";

                    String monday = "Monday: " + snapshot.child(dr_username).child("monday").child("start_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("monday").child("start_minute").getValue().toString() + ", to "
                            + snapshot.child(dr_username).child("monday").child("end_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("monday").child("end_minute").getValue().toString() + "\n";

                    String tuesday = "Tuesday: " + snapshot.child(dr_username).child("tuesday").child("start_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("tuesday").child("start_minute").getValue().toString() + ", to "
                            + snapshot.child(dr_username).child("tuesday").child("end_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("tuesday").child("end_minute").getValue().toString() + "\n";

                    String wednesday = "Wednesday: " + snapshot.child(dr_username).child("wednesday").child("start_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("wednesday").child("start_minute").getValue().toString() + ", to "
                            + snapshot.child(dr_username).child("wednesday").child("end_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("wednesday").child("end_minute").getValue().toString() + "\n";

                    String thursday = "Thursday: " + snapshot.child(dr_username).child("thursday").child("start_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("thursday").child("start_minute").getValue().toString() + ", to "
                            + snapshot.child(dr_username).child("thursday").child("end_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("thursday").child("end_minute").getValue().toString() + "\n";

                    String friday = "Friday: " + snapshot.child(dr_username).child("friday").child("start_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("friday").child("start_minute").getValue().toString() + ", to "
                            + snapshot.child(dr_username).child("friday").child("end_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("friday").child("end_minute").getValue().toString() + "\n";

                    String saturday = "saturday: " + snapshot.child(dr_username).child("saturday").child("start_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("saturday").child("start_minute").getValue().toString() + ", to "
                            + snapshot.child(dr_username).child("saturday").child("end_hour").getValue().toString() + ":"
                            + snapshot.child(dr_username).child("saturday").child("end_minute").getValue().toString() + "\n";

                    TextView tv = (TextView)findViewById(R.id.textView5);
                    tv.setText("Booking appointment for Dr."  + dr_name + ":\n" + sunday+monday+tuesday+wednesday+thursday+friday+saturday);

                    availabilities.add(snapshot.child(dr_username).child("sunday").getValue(Availability.class));
                    availabilities.add(snapshot.child(dr_username).child("monday").getValue(Availability.class));
                    availabilities.add(snapshot.child(dr_username).child("tuesday").getValue(Availability.class));
                    availabilities.add(snapshot.child(dr_username).child("wednesday").getValue(Availability.class));
                    availabilities.add(snapshot.child(dr_username).child("thursday").getValue(Availability.class));
                    availabilities.add(snapshot.child(dr_username).child("friday").getValue(Availability.class));
                    availabilities.add(snapshot.child(dr_username).child("saturday").getValue(Availability.class));

                }
                else{
                    TextView tv = (TextView)findViewById(R.id.textView5);
                    tv.setText("No availability has been set yet");
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        avail.addValueEventListener(avail_listener);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void submit(View view){

        DatePicker picker = (DatePicker)findViewById(R.id.datePicker1);
        TimePicker start = (TimePicker)findViewById(R.id.timePicker1);
        TimePicker end = (TimePicker) findViewById(R.id.timePicker2);

        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year = picker.getYear();
        int start_hour = start.getHour();
        int start_minute = start.getMinute();
        int end_hour = end.getHour();
        int end_minute = end.getMinute();

        Appointment a = new Appointment(start_hour, end_hour, day, MONTHS[month], year);


        a.setDr_user_name(dr_username);
        a.setPatient_user_name(username);
        a.setDr_name(dr_name);
        a.setPatient_name(name);
        a.setStart_minute(start_minute);
        a.setEnd_minute(end_minute);

        Calendar clnd1 = new GregorianCalendar(year, month, day);

        int woy = clnd1.get(Calendar.WEEK_OF_YEAR);

        int dow = clnd1.get(Calendar.DAY_OF_WEEK);

        long uniqueIdentifier = System.currentTimeMillis() + username.hashCode();

        a.setUniqueID(uniqueIdentifier);


        a.setWeekOfYear(woy);
        a.setWeek(dow);

        if(availabilities.size() <= 0){
            outsideRangeDialog("no availabilities");
            return;
        }

        Availability ref = availabilities.get(dow - 1);

        //checks if time is outside of availability, if the doctor is not available, or is longer than an hour and redirects to book appointment search page
        if(!ref.isInRange(a) || ref.isUnavailable()){
            outsideRangeDialog("outside range: " + a.toString() + " " + ref.toString() + " " + String.valueOf(dow));
            return;
        }

        if(!a.isValid()){
            tooLongDialog();
            return;
        }

        DatabaseReference pDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsPatient");
        DatabaseReference dDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsDoctor");



        ValueEventListener checkOverlap = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child(dr_username).exists()){
                    for(DataSnapshot ds : snapshot.child(dr_username).getChildren()){
                        Appointment drAppt = ds.getValue(Appointment.class);
                        if(a.isOverLap(drAppt) || a.equals(drAppt)){
                            conflictDialog();
                            return;
                        }else{
                            DatabaseReference pDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsPatient");
                            DatabaseReference dDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsDoctor");

                            pDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (snapshot.child(a.getPatient_user_name()).exists()){
                                        if(snapshot.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount() + (long)1))).exists()){
                                            pDatabaseReference.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount()))).setValue(a);
                                        }
                                        pDatabaseReference.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount() + (long)1))).setValue(a);
                                        Map<String, Object> identifierUpdate = new HashMap<>();
                                        identifierUpdate.put("uniqueID", uniqueIdentifier);
                                        pDatabaseReference.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount() + (long)1))).updateChildren(identifierUpdate);
                                    }
                                    else {
                                        pDatabaseReference.child(a.getPatient_user_name()).child("1").setValue(a);
                                        Map<String, Object> identifierUpdate = new HashMap<>();
                                        identifierUpdate.put("uniqueID", uniqueIdentifier);
                                        pDatabaseReference.child(a.getPatient_user_name()).child("1").updateChildren(identifierUpdate);
                                    }

                                    Intent i = new Intent(getApplicationContext(), patientHome.class);
                                    i.putExtra("user", username);
                                    i.putExtra("name", name);
                                    startActivity(i);
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                            dDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.child(a.getDr_user_name()).exists()){
                                        if(snapshot.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount() + (long)1))).exists()){
                                            pDatabaseReference.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount()))).setValue(a);
                                        }
                                        dDatabaseReference.child(a.getDr_user_name()).child(String.valueOf((int)(snapshot.child(a.getDr_user_name()).getChildrenCount() + (long)1))).setValue(a);
                                        Map<String, Object> identifierUpdate = new HashMap<>();
                                        identifierUpdate.put("uniqueID", uniqueIdentifier);
                                        dDatabaseReference.child(a.getDr_user_name()).child(String.valueOf((int)(snapshot.child(a.getDr_user_name()).getChildrenCount() + (long)1))).updateChildren(identifierUpdate);

                                    }
                                    else {
                                        dDatabaseReference.child(a.getDr_user_name()).child("1").setValue(a);
                                        Map<String, Object> identifierUpdate = new HashMap<>();
                                        identifierUpdate.put("uniqueID", uniqueIdentifier);
                                        dDatabaseReference.child(a.getDr_user_name()).child("1").updateChildren(identifierUpdate);
                                    }

                                    Intent i = new Intent(getApplicationContext(), patientHome.class);
                                    i.putExtra("user", username);
                                    i.putExtra("name", name);
                                    startActivity(i);
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }else{
                    DatabaseReference pDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsPatient");
                    DatabaseReference dDatabaseReference = FirebaseDatabase.getInstance("https://doctor-appt-app-default-rtdb.firebaseio.com/").getReference("appointmentsDoctor");

                    pDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.child(a.getPatient_user_name()).exists()){
                                pDatabaseReference.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount() + (long)1))).setValue(a);
                                Map<String, Object> identifierUpdate = new HashMap<>();
                                identifierUpdate.put("uniqueID", uniqueIdentifier);
                                pDatabaseReference.child(a.getPatient_user_name()).child(String.valueOf((int)(snapshot.child(a.getPatient_user_name()).getChildrenCount() + (long)1))).updateChildren(identifierUpdate);
                            }
                            else {
                                pDatabaseReference.child(a.getPatient_user_name()).child("1").setValue(a);
                                Map<String, Object> identifierUpdate = new HashMap<>();
                                identifierUpdate.put("uniqueID", uniqueIdentifier);
                                pDatabaseReference.child(a.getPatient_user_name()).child("1").updateChildren(identifierUpdate);
                            }

                            Intent i = new Intent(getApplicationContext(), patientHome.class);
                            i.putExtra("user", username);
                            i.putExtra("name", name);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                    dDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(a.getDr_user_name()).exists()){
                                dDatabaseReference.child(a.getDr_user_name()).child(String.valueOf((int)(snapshot.child(a.getDr_user_name()).getChildrenCount() + (long)1))).setValue(a);
                                Map<String, Object> identifierUpdate = new HashMap<>();
                                identifierUpdate.put("uniqueID", uniqueIdentifier);
                                dDatabaseReference.child(a.getDr_user_name()).child(String.valueOf((int)(snapshot.child(a.getDr_user_name()).getChildrenCount() + (long)1))).updateChildren(identifierUpdate);

                            }
                            else {
                                dDatabaseReference.child(a.getDr_user_name()).child("1").setValue(a);
                                Map<String, Object> identifierUpdate = new HashMap<>();
                                identifierUpdate.put("uniqueID", uniqueIdentifier);
                                dDatabaseReference.child(a.getDr_user_name()).child("1").updateChildren(identifierUpdate);
                            }

                            Intent i = new Intent(getApplicationContext(), patientHome.class);
                            i.putExtra("user", username);
                            i.putExtra("name", name);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        dDatabaseReference.addListenerForSingleValueEvent(checkOverlap);





    }

    private void outsideRangeDialog(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(view_doctor_availability.this);
        builder.setTitle("Not Valid Appointment");
        builder.setMessage(s);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void tooLongDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view_doctor_availability.this);
        builder.setTitle("Not Valid Appointment");
        builder.setMessage("The appointment invalid (either longer than 1 hour, or the start time is after the end time)");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    private void conflictDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view_doctor_availability.this);
        builder.setTitle("Not Valid Appointment");
        builder.setMessage("Someone has already booked for that time!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }



}