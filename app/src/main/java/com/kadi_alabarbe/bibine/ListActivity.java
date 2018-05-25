package com.kadi_alabarbe.bibine;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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


public class ListActivity extends AppCompatActivity {
    ListActivity.BeerUpdate Beer;
    String urlComplement = "startup";

    public static final String BEER_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    private static final String TAG = DrinkBeerService.class.getSimpleName();

    private ListAdapter Adapter;

    public void setUrlComplement(String urlComplement) {
        this.urlComplement = urlComplement;
    }

    public void notif(int notificationID, String content) {
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.downloadspongebob);
//          Buggy for now
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setLargeIcon(bm);
        mBuilder.setSmallIcon(R.drawable.downloadspongebob);
        mBuilder.setContentTitle("Beer");
        mBuilder.setContentText("content");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(notificationID, mBuilder.build());
    }

    public void OpenWebView(View target) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com/search?&sourceid=navclient&btnI=I&q=".concat(urlComplement)));
        startActivity(i);
    }

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
        LocalBroadcastManager.getInstance(this).registerReceiver(Beer, intentFilter);
        notif(42,"starting");
    }

    private void launchDownload() {
        DrinkBeerService.startActionBeer(this);
    }

    public class BeerUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d(TAG, getIntent().getAction());
            Adapter.setJArr(getBeerFromFile());
            Adapter.ConvertToArray();
            Log.d("TODO", "UPDATE");
        }

        JSONArray getBeerFromFile() {
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
            } catch (JSONException e){
                e.printStackTrace();
                return new JSONArray();
            }
        }
    }
}
