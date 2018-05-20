package com.kadi_alabarbe.bibine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.Thread.sleep;

public class ListActivity  extends AppCompatActivity {
    private ListActivity.BeerUpdate Beer;
    public static final String BEER_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    // --Commented out by Inspection (20/05/2018 23:49):private static final String TAG = DrinkBeerService.class.getSimpleName();
    private ListAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        launchDownload();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final RecyclerView rv = findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        Adapter = new ListAdapter();
        rv.setAdapter(Adapter);
        IntentFilter intentFilter = new IntentFilter(BEER_UPDATE);
        Beer = new ListActivity.BeerUpdate();
        LocalBroadcastManager.getInstance(this).registerReceiver(Beer,intentFilter);
    }
    private void launchDownload() {
        DrinkBeerService.startActionPoke(this);
    }

    public class BeerUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d(TAG, getIntent().getAction());
            Adapter.setJArr(getBeerFromFile());
            Adapter.ConvertToArray();
            Log.d("TODO","UPDATE");
        }
        JSONArray getBeerFromFile(){
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
