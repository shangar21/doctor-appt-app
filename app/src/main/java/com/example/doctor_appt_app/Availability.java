package com.example.doctor_appt_app;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Availability {
    int start_hour;
    int start_minute;
    int end_hour;
    int end_minute;

    public Availability(){}

    public Availability(int start_hour, int start_minute, int end_hour, int end_minute) {
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(int start_minute) {
        this.start_minute = start_minute;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getEnd_minute() {
        return end_minute;
    }

    public void setEnd_minute(int end_minute) {
        this.end_minute = end_minute;
    }

    public boolean isUnavailable(){
        return this.start_hour == this.end_hour && this.start_minute == this.end_minute;
    }

    public boolean isInRange(Appointment a){
        int sh = a.getStart_hour();
        int sm  = a.getStart_minute();

        int eh = a.getEnd_hour();
        int em = a.getEnd_minute();

        if(sh == this.start_hour){
            return this.start_minute <= sm;
        }

        if(eh == this.end_hour){
            return em <= this.end_minute;
        }

        return sh >= this.start_hour && eh <= this.end_hour ;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "Availability From: " + String.valueOf(this.start_hour) + ":" + String.valueOf(this.start_minute)  + " To: " + String.valueOf(this.end_hour) + String.valueOf(this.end_minute);
    }
}
