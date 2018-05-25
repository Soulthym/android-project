package com.kadi_alabarbe.bibine;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Bibine", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        createNotificationChannel();
        //        DrinkBeerService.startActionPoke(this);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.credit:
                Toast.makeText(getApplicationContext(), "KADI Antonin_ALABARBE Thybault_2017_2018", Toast.LENGTH_SHORT).show();
                break;
            case R.id.francais:
                LanguageSelection fr = new LanguageSelection();
                fr.changeLanguage(getResources(), "fr-rFR");
                Toast toast_fr = Toast.makeText(this, "Français", Toast.LENGTH_SHORT);
                toast_fr.show();
                this.recreate();
                break;
            case R.id.anglais:
                LanguageSelection en = new LanguageSelection();
                en.changeLanguage(getResources(), "en-rUS");
                Toast toast_en = Toast.makeText(this, "English", Toast.LENGTH_SHORT);
                toast_en.show();
                this.recreate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void launchListActivity(View target) {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }
}
