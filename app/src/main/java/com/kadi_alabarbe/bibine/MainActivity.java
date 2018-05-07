package com.kadi_alabarbe.bibine;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String BEER_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    private static final String TAG = DrinkBeerService.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DrinkBeerService.startActionPoke(this);
        IntentFilter intentFilter = new IntentFilter(BEER_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BeerUpdate(),intentFilter);
    }

    public void launchListActivity(View target) {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }

    public void launchDownload(View target) {
        DrinkBeerService.startActionPoke(this);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(android.R.drawable.alert_dark_frame, "Toc toc, c'est une notification !", System.currentTimeMillis());
        notificationManager.notify(0,notification);

    }

    public class BeerUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, getIntent().getAction());
            getBeerFromFile();
        }
        // prévoir une action de notification ici
        public JSONArray getBeerFromFile(){
            try {
                InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                // return new JSONObject(new String(buffer, "UTF-8")).getJSONArray("objects"); // new url "http://binouze.fabrigli.fr/bieres/:id.json"
                return new JSONArray(new String(buffer, "UTF-8")); // url = new URL("http://binouze.fabrigli.fr/bieres.json");
            } catch (IOException e) {
                e.printStackTrace();
                return new JSONArray();
            } catch (JSONException e) {
                e.printStackTrace();
                return new JSONArray();
            }
        }
    }
}
