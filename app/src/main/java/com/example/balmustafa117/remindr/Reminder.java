package com.example.balmustafa117.remindr;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.location.Location;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by balmustafa117 on 5/2/15.
 */
public class Reminder implements Parcelable {

    //fields
    private String reminderMessage;
    private Date dateCreated;
    private Date dateDue;
    private double locationLatitude;
    private double locationLongtitude;
    private Location location;

    //constructor
    public Reminder() {
        reminderMessage = "do project";
        dateCreated = new Date();
        dateDue = new Date();
        locationLatitude = 35.3333;
        locationLongtitude = -77.16886749999998;
    }

    public Reminder(String message, Date date, Date dateD, double latitude, double longtitude) {
        reminderMessage = message;
        dateCreated = date;
        dateDue = dateD;
        locationLatitude = latitude;
        locationLongtitude = longtitude;
    }

    public Reminder(String message) {
        reminderMessage = message;
        dateCreated = new Date();
        dateDue = new Date();
        locationLatitude = 38.8187477;
        locationLongtitude = -77.16886749999998;
    }

    public Reminder(Parcel in) {
        reminderMessage = in.readString();
        dateCreated = new Date(in.readLong());
        dateDue = new Date(in.readLong());
        locationLatitude = in.readDouble();
        locationLongtitude = in.readDouble();

    }

    public String toString(){
        String s = ("Text: " + reminderMessage + ". Created on: " + getDateCreated() + ". Due on: " + getDateDue() + ", at location(la/lo): " + getLocationCoordinatesString());
        return s;

    }

    //methods
    public void setReminderMessage(String newMessage) {
        reminderMessage = newMessage;
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public Date getDateCreated() {
        return ( dateCreated);
    }

    public Date getDateDue() {
        return ( dateDue);
    }

    public String getLocationCoordinatesString(){
        return ("" + locationLatitude +","+locationLongtitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reminderMessage);
        dest.writeLong(dateCreated.getTime());
        dest.writeLong(dateDue.getTime());
        dest.writeDouble(locationLatitude);
        dest.writeDouble(locationLongtitude);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };



}
