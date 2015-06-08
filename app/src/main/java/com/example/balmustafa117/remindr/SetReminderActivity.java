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



public class SetReminderActivity extends Activity implements LocationListener{

    private CardScrollView mCardScroller;
    private CardScrollAdapter mAdapter;
    private View mView;

    private double latitude, longitude;
    private LocationManager lm;
    private Location loc;
    private String provider;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private Reminder newReminder;
    private String message;

    public ArrayList<Reminder> reminderArrayList;

    private static final int TAKE_PICTURE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(0, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Set Message").setIcon(R.drawable.ic_message));
        cards.add(1, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Take Picture").setIcon(R.drawable.ic_camera));
        cards.add(2, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Set Due Date").setIcon(R.drawable.ic_duedate));
        cards.add(3, new CardBuilder(this, CardBuilder.Layout.MENU).setText("Finish Reminder").setIcon(R.drawable.ic_done));

        mAdapter = new CardAdapter(cards);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
        setCardScrollerListener();

        reminderArrayList = getIntent().getParcelableArrayListExtra("Reminder");
        android.util.Log.d("ArrayListSizeAtFirst", reminderArrayList.size() + "");
    }
    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        promptSpeechInput();
                        break;
                    case 1:
                        takePicture();
                    case 2:
                    case 3:
                        newReminder = new Reminder(message);
                        reminderArrayList.add(newReminder);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", reminderArrayList);
                        setResult(RESULT_OK, returnIntent);
                        android.util.Log.d("ArrayListAfterFinish", reminderArrayList.size() + "");
                        finish();
                        break;

                }
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.TAP);
            }
        });
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a) {

        }
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_REQUEST);
    }

    private void processPictureWhenReady(final String picturePath) {
        final File pictureFile = new File(picturePath);

        if (pictureFile.exists()) {
            // The picture is ready; process it.
        } else {
            // The file does not exist yet. Before starting the file observer, you
            // can update your UI to let the user know that the application is
            // waiting for the picture (for example, by displaying the thumbnail
            // image and a progress indicator).

            final File parentDirectory = pictureFile.getParentFile();
            FileObserver observer = new FileObserver(parentDirectory.getPath(),
                    FileObserver.CLOSE_WRITE | FileObserver.MOVED_TO) {
                // Protect against additional pending events after CLOSE_WRITE
                // or MOVED_TO is handled.
                private boolean isFileWritten;

                @Override
                public void onEvent(int event, String path) {
                    if (!isFileWritten) {
                        // For safety, make sure that the file that was created in
                        // the directory is actually the one that we're expecting.
                        File affectedFile = new File(parentDirectory, path);
                        isFileWritten = affectedFile.equals(pictureFile);

                        if (isFileWritten) {
                            stopWatching();

                            // Now that the file is ready, recursively call
                            // processPictureWhenReady again (on the UI thread).
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    processPictureWhenReady(picturePath);
                                }
                            });
                        }
                    }
                }
            };
            observer.startWatching();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
            String thumbnailPath = data.getStringExtra(Intents.EXTRA_THUMBNAIL_FILE_PATH);
            String picturePath = data.getStringExtra(Intents.EXTRA_PICTURE_FILE_PATH);

            processPictureWhenReady(picturePath);
            // TODO: Show the thumbnail to the user while the full picture is being
            // processed.
        }

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
                    if (resultCode == RESULT_OK && null != data) {

                        ArrayList<String> result = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        String messageBefore = result.toString();
                        message = messageBefore.substring(1, messageBefore.length() - 1);
                    }
                }
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

