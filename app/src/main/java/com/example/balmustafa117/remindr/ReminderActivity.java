package com.example.balmustafa117.remindr;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReminderActivity extends Activity {
    /** {@link CardScrollView} to use as the main content view. */
    private CardScrollView mCardScroller;
    private CardScrollAdapter mAdapter;

    // /** "Hello World!" {@link View} generated by {@link #buildView()}. */
    private View mView;

    public ArrayList<Reminder> reminderArrayList;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        reminderArrayList = getIntent().getParcelableArrayListExtra("Reminder");
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();
        ArrayList<Integer> picArrayList = new ArrayList<Integer>();
        picArrayList.add(R.drawable.ic_shrimpnoodle);
        picArrayList.add(R.drawable.ic_laundry);
        picArrayList.add(R.drawable.ic_drone);
        picArrayList.add(R.drawable.ic_linal);
        android.util.Log.d("printSizeArrayList",reminderArrayList.size() + "");


        for (int n = 0; n < reminderArrayList.size(); n++) {

            Reminder thisReminder = reminderArrayList.get(n);
            String thisReminderMessage = thisReminder.getReminderMessage();
            Date thisReminderDateCreated = thisReminder.getDateCreated();
            Date thisReminderDateDue = thisReminder.getDateDue();
            String thisReminderLocation = thisReminder.getLocationCoordinatesString();
            cards.add(n, new CardBuilder(this, CardBuilder.Layout.CAPTION).setText("" + thisReminderMessage + " " +
                    "Due:" + thisReminderDateDue + "." ).setFootnote("Location:" + thisReminderLocation + "").addImage(picArrayList.get(n)));
        }

        mAdapter = new CardAdapter( cards );
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }


}
