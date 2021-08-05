package com.example.doctor_appt_app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment {
    int start_hour;
    int start_minute;
    int end_hour;
    int end_minute;
    int day;
    String week;
    String month;
    int year;

    String dr_user_name;
    String patient_user_name;
    String dr_name;
    String patient_name;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
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
}
