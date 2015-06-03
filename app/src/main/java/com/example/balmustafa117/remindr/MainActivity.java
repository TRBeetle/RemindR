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

        //Build cards
        cards.add(0, new CardBuilder(this, CardBuilder.Layout.TEXT).setText("Set Reminder"));
        cards.add(1, new CardBuilder(this, CardBuilder.Layout.TEXT).setText("Reminders"));

        //Sample Reminders
        reminderArrayList = new ArrayList<Reminder>();

        Reminder r1 = (new Reminder("(TEST)Pick up peaches"));
        Reminder r2 = (new Reminder("(TEST)Drop off laundry"));
        Reminder r3 = (new Reminder("(TEST)Do homework"));

        reminderArrayList.add(r1);
        reminderArrayList.add(r2);
        reminderArrayList.add(r3);

    }

    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, SetReminderActivity.class);
                        intent.putParcelableArrayListExtra("Reminder", reminderArrayList);
                        android.util.Log.d("SReminderActivity","Success");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity.this, ReminderActivity.class);
                        intent2.putParcelableArrayListExtra("Reminder", reminderArrayList);
                        android.util.Log.d("SSetReminderActivity", "Success");
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
