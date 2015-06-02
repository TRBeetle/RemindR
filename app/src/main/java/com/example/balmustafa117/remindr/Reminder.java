package com.example.balmustafa117.remindr;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    DateFormat df6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //constructor
    public Reminder() {
        reminderMessage = "do project";
        dateCreated = new Date();
        dateDue = new Date();
        locationLatitude = 35.3333;
        locationLongtitude = 61.11111;
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
        locationLatitude = 35.3333;
        locationLongtitude = 61.11111;
    }

    public Reminder(Parcel in) {
        reminderMessage = in.readString();
        dateCreated = new Date(in.readLong());
        dateDue = new Date(in.readLong());
        locationLongtitude = in.readDouble();
        locationLongtitude = in.readDouble();
        //String s = in.readString();
        //System.out.println( s ) ;
       // df6 = new SimpleDateFormat(s);

    }


    //methods
    public void setReminderMessage(String newMessage) {
        reminderMessage = newMessage;
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public String getDateCreated() {
        String str5 = df6.format(dateCreated);
        return ("Created on " + str5);
    }

    public String getDateDue() {
        String str6 = df6.format(dateDue);
        return ("Due on " + str6);
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
        //dest.writeString(df6.toString());
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
