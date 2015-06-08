package com.example.balmustafa117.remindr;

import android.app.Application;

import java.util.ArrayList;

public class GlobalArrayList extends Application {

    private ArrayList<Reminder> reminderArrayList;

    public ArrayList<Reminder> getState() {
        return reminderArrayList;
    }

    public void setState(ArrayList<Reminder> someVariable) {
        this.reminderArrayList = someVariable;
    }
}
