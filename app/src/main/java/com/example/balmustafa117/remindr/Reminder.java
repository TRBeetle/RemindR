package com.example.balmustafa117.remindr;

import java.util.Date;

/**
 * Created by balmustafa117 on 5/2/15.
 */
public class Reminder {

    //fields
    private String reminderMessage;
    private Date dateCreated;
    private double locationLatitude;
    private double locationLongtitude;

    //constructor
    public Reminder(String message, Date date, double latitude, double longtitude){
        reminderMessage = message;
        dateCreated = date;
        locationLatitude = latitude;
        locationLongtitude = longtitude;
    }

    //methods
    public void setReminderMessage(String newMessage) {
        reminderMessage = newMessage;
    }

}
