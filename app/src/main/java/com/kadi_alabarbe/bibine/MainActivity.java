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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
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

import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    SensorManager sm;

    private float acelVal;
    private float acelLast;
    private float shake;


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getResources().getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void Animate() {
        ImageView img = (ImageView)findViewById(R.id.imgvw);
        Animation slideup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideup);
        img.startAnimation(slideup);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        createNotificationChannel();
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double)(x*x + y*y + z*z));
            float delta = acelVal-acelLast;
            shake = shake*0.9f + delta;

            if (shake > 12) {
                Animate();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

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
                Toast.makeText(this, getResources().getString(R.string.lang)
                                .concat(" ")
                                .concat(getResources().getString(R.string.francais))
                        , Toast.LENGTH_SHORT).show();
                this.recreate();
                break;
            case R.id.anglais:
                LanguageSelection en = new LanguageSelection();
                en.changeLanguage(getResources(), "en-rUS");
                Toast.makeText(this, getResources().getString(R.string.lang)
                                .concat(" ")
                                .concat(getResources().getString(R.string.anglais))
                        , Toast.LENGTH_SHORT).show();
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
