package com.example.balmustafa117.remindr;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import com.example.balmustafa117.remindr.Reminder;


import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p/>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 *
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class MainActivity extends Activity {

    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;
    private View mView;
    public ArrayList<Reminder> reminderArrayList;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

//java database connectivity!!!!
        mAdapter = new CardAdapter(cards);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
        setCardScrollerListener();

        //Sample Reminders
        reminderArrayList = new ArrayList<Reminder>();
//
//        reminderArrayList.add(new Reminder("Pick up peaches."));
//        reminderArrayList.add(new Reminder("Drop off laundry."));
//        reminderArrayList.add(new Reminder("Do homework."));

//        android.util.Log.d("blah", reminderArrayList.get(0).toString());


        cards.add(0, new CardBuilder(this, CardBuilder.Layout.TEXT).setText("Set Reminder"));

        cards.add(1, new CardBuilder(this, CardBuilder.Layout.TEXT).setText("Reminders"));


    }

    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.util.Log.d("test1", position + "");
                Reminder r1 = (new Reminder("Pick up peaches."));
                Reminder r2 = (new Reminder("Drop off laundry."));
                Reminder r3 = (new Reminder("Do homework."));
                switch (position) {
                    case 0:
//                        android.util.Log.d("blah2", reminderArrayList.get(0).toString());
                        Intent intent = new Intent(MainActivity.this, SetReminderActivity.class);
                        //intent.putExtra("Reminder", reminderArrayList);
                        intent.putExtra("r1", r1);
                        intent.putExtra("r2", r2);
                        intent.putExtra("r3", r3);
                        android.util.Log.d("blah","Hello");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity.this, ReminderActivity.class);
                        intent2.putExtra("Reminder", reminderArrayList);
                        startActivity(intent2);
                        break;
                }
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.TAP);
            }
        });
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
