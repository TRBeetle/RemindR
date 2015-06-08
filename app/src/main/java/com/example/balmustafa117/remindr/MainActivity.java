package com.example.balmustafa117.remindr;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import android.content.ServiceConnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
        cards.add(0, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Set Reminder").setFootnote("Create a new Reminder").setIcon(R.drawable.ic_new_logo));
        cards.add(1, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Reminders").setFootnote("View active Reminders").setIcon(R.drawable.ic_list));

        //Sample Reminders
        reminderArrayList = new ArrayList<Reminder>();

        Reminder r1 = (new Reminder("'Buy Shrimp Instant Noodles'     "));
        Reminder r2 = (new Reminder("'Do laundry'                                 "));
        Reminder r4 = (new Reminder("'Finish Linear Algebra HW 3'      "));
        Reminder r3 = (new Reminder("'Work on quad-copter project'        "));

        reminderArrayList.add(r1);
        reminderArrayList.add(r2);
        reminderArrayList.add(r3);
        reminderArrayList.add(r4);


    }

    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, SetReminderActivity.class);
                        intent.putParcelableArrayListExtra("Reminder", reminderArrayList);
                        android.util.Log.d("SReminderActivity", "Success");
                        startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                reminderArrayList = data.getParcelableArrayListExtra("result");
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
