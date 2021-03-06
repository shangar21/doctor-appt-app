package com.example.doctor_appt_app;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.lang.*;

public class Appointment {
    int start_hour;
    int start_minute;
    int end_hour;
    int end_minute;
    int day;
    int week;
    String month;
    int year;

    String dr_user_name;
    String patient_user_name;
    String dr_name;
    String patient_name;
    long uniqueID;
    int weekOfYear;


    public Appointment(){}

    public Appointment(int start_hour, int end_hour, int day, String month, int year) {
        this.start_hour = start_hour;
        this.end_hour = end_hour;
        this.day = day;
        this.month = month;
        this.year = year;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDr_user_name() {
        return dr_user_name;
    }

    public void setDr_user_name(String dr_user_name) {
        this.dr_user_name = dr_user_name;
    }

    public String getPatient_user_name() {
        return patient_user_name;
    }

    public void setPatient_user_name(String patient_user_name) {
        this.patient_user_name = patient_user_name;
    }

    public String getDr_name() {return dr_name;}

    public void setDr_name(String a) {this.dr_name = a;}

    public String getPatient_name() {return patient_name;}

    public void setPatient_name(String a) {this.patient_name = a;}

    public int getWeekOfYear() {return weekOfYear;}

    public void setWeekOfYear(int a) {this.weekOfYear = a;}

    public void setUniqueID(long a) {this.uniqueID = a;}

    public boolean isValid(){
        return (this.end_hour == this.start_hour+1 && this.end_hour <= this.start_minute) || (this.end_hour == this.start_hour) || this.start_hour > this.end_hour || (this.start_hour == this.end_hour && this.start_minute > this.end_minute);
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "Appointment From: " + String.valueOf(this.start_hour) + ":" + String.valueOf(this.start_minute)  + " To: " + String.valueOf(this.end_hour)
                + String.valueOf(this.end_minute) + " Month: " + month + " Day: " + String.valueOf(day);
    }

    public boolean isOverLap(Appointment a){
        if(a.getMonth().equals(this.month) && a.getDay() == this.day && a.getYear() == this.year){
            int x1 = a.getStart_hour() * 60 + a.getStart_minute();
            int x2 = a.getEnd_hour() * 60 + a.getEnd_minute();

            int y1 = this.start_hour * 60 + this.start_minute;
            int y2 = this.end_hour * 60 + this.end_minute;

            return x1 <= y2 && y1 <= x2;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return start_hour == that.start_hour &&
                start_minute == that.start_minute &&
                end_hour == that.end_hour &&
                end_minute == that.end_minute &&
                day == that.day &&
                year == that.year &&
                month.equals(that.month);
    }

    public boolean isInPast(int y, int m, int d){
        Calendar today = Calendar.getInstance();
        today.setTime(new Date(System.currentTimeMillis()));
        Calendar appt = new GregorianCalendar(y,m,d);
        appt.set(Calendar.HOUR, this.start_hour);
        appt.set(Calendar.MINUTE, this.start_minute);
        return appt.compareTo(today) <= 0;
    }

}