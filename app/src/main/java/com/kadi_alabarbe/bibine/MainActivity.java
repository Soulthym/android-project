package com.kadi_alabarbe.bibine;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DrinkBeerService.startActionPoke(this);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.credit) {
            Toast.makeText(getApplicationContext(), "KADI Antonin_ALABARBE Thybault_2017_2018", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.francais) {
            Locale locale = new Locale("fr-rFR");
            Locale.setDefault(locale);
            Configuration config= new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext(). getResources().getDisplayMetrics());
            Toast.makeText(getApplicationContext(), "Langue Française", Toast.LENGTH_SHORT).show();

        }
        if(id == R.id.anglais) {
            Locale locale1 = new Locale("en-us");
            Locale.setDefault(locale1);
            Configuration config1= new Configuration();
            config1.locale = locale1;
            getBaseContext().getResources().updateConfiguration(config1, getBaseContext(). getResources().getDisplayMetrics());
            Toast.makeText(getApplicationContext(), "English Language", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
    public void launchListActivity(View target) {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }
}
