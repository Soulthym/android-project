package com.kadi_alabarbe.bibine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    public static final String BEER_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    private static final String TAG = DrinkBeerService.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchActivity(View target) {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
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
                return new JSONObject(new String(buffer, "UTF-8")).getJSONArray("objects"); // construction du tableau
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
