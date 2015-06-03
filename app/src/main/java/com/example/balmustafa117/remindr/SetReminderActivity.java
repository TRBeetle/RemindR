package com.example.balmustafa117.remindr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import java.util.ArrayList;


public class SetReminderActivity extends Activity implements LocationListener {

    private CardScrollView mCardScroller;
    private CardScrollAdapter mAdapter;
    private View mView;
    private double latitude, longitude;
    private LocationManager lm;
    private Location loc;
    private String provider;

    Reminder newReminder;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        ArrayList<Reminder> reminderArrayList = getIntent().getParcelableArrayListExtra("Reminder");
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        provider = lm.getBestProvider(c, false);
        loc = lm.getLastKnownLocation(provider);

        if (provider != null && !provider.equals("")) {
            Location location = lm.getLastKnownLocation(provider);
            lm.requestLocationUpdates(provider, 2000, 1, this);
            if (location != null) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getApplicationContext(), "location not found", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Provider is null", Toast.LENGTH_LONG).show();
        }

        try {
            // http://labs.puneeth.org:5000/techlab/getrestaurants?latitude=39.011870&longitude=-77.527546

            longitude = loc.getLongitude();
            latitude = loc.getLatitude();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            cards.add(new CardBuilder(this, CardBuilder.Layout.TEXT).setText("Exception"));
        }

        mAdapter = new CardAdapter( cards );
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);





        newReminder = new Reminder();




        /*
        cards.add(0, new CardBuilder(this, CardBuilder.Layout.AUTHOR)
                        .setHeading(R.string.Reviewer1)
                        .setText(R.string.Review1)
                        .addImage(R.drawable.fivestar)
        );
        cards.add(1, new CardBuilder(this, CardBuilder.Layout.AUTHOR)
                        .setHeading(R.string.Reviewer2)
        */

        mAdapter = new CardAdapter( cards );
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
    }

    @Override
    public void onLocationChanged(Location loc) {
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
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
