package com.example.doctor_appt_app;

public class Doctor extends User{
    String specialization;


    public Doctor(String name, String email, String username, String password, String gender) {
        super(name, email, username, password, gender);
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
