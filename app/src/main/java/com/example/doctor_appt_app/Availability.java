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
        int x1 = a.getStart_hour() * 60 + a.getStart_minute();
        int x2 = a.getEnd_hour() * 60 + a.getEnd_minute();

        int y1 = this.start_hour * 60 + this.start_minute;
        int y2 = this.end_hour * 60 + this.end_minute;

        return x1 <= y2 && y1 <= x2;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "Availability From: " + String.valueOf(this.start_hour) + ":" + String.valueOf(this.start_minute)  + " To: " + String.valueOf(this.end_hour) + String.valueOf(this.end_minute);
    }
}
