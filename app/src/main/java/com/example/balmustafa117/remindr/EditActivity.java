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
        import android.media.AudioManager;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.speech.RecognitionListener;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Toast;
        import android.speech.RecognizerIntent;


        import android.provider.MediaStore;
        import android.os.FileObserver;
        import java.io.File;

        import com.google.android.glass.content.Intents;
        import com.google.android.glass.media.Sounds;
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
        import java.util.Locale;
        import android.content.ActivityNotFoundException;

/**
 * Created by balmustafa117 on 6/8/15.
 */
public class EditActivity extends Activity{

    private CardScrollView mCardScroller;
    private CardScrollAdapter mAdapter;
    private View mView;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(0, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Change Reminder").setIcon(R.drawable.ic_change));
        cards.add(1, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Delete Reminder").setIcon(R.drawable.ic_delete));

        mAdapter = new CardAdapter(cards);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
        setCardScrollerListener();
    }
    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
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
