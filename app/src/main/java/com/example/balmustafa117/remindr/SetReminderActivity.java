package com.example.balmustafa117.remindr;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;


public class SetReminderActivity extends Activity {

    private CardScrollView mCardScroller;
    private CardScrollAdapter mAdapter;
    private View mView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        ArrayList<Reminder> reminderArrayList = getIntent().getExtras().getParcelable("Reminder");

        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        //Create test Reminders
        Reminder reminder1 = new Reminder();

        /*
        cards.add(0, new CardBuilder(this, CardBuilder.Layout.AUTHOR)
                        .setHeading(R.string.Reviewer1)
                        .setText(R.string.Review1)
                        .addImage(R.drawable.fivestar)
        );
        cards.add(1, new CardBuilder(this, CardBuilder.Layout.AUTHOR)
                        .setHeading(R.string.Reviewer2)
                        .setText(R.string.Review2)
                        .addImage(R.drawable.onestar)
        );
        */

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
