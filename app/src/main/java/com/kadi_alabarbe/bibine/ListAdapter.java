package com.kadi_alabarbe.bibine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private JSONArray JArr;
    private final ArrayList<Pair<String, String>> Arr = new ArrayList<>();

    void setJArr(JSONArray Array) {
        JArr = Array;
    }

    public void ConvertToArray() {
        JSONArray jsonArray = JArr;
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                String name;
                String description;
                JSONObject sub;
                try {
                    sub = JArr.getJSONObject(i);
                    Log.d("sub", String.valueOf(sub));
                    name = sub.getString("name");
                    Log.d("name", String.valueOf(name));
                    description = sub.getString("description");
                    Log.d("description", String.valueOf(description));
                    Pair<String, String> p = new Pair<>(name, description);
                    Arr.add(p);
                    Log.d("ARRAY", String.valueOf(Arr));
                } catch (JSONException | UnsupportedOperationException e) {
                    e.printStackTrace();
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return Arr.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pair<String, String> pair = Arr.get(position);
        holder.display(pair);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView description;
        private Activity activity;

        private Pair<String, String> currentPair;

        ViewHolder(final View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            Button click = itemView.findViewById(R.id.btnd2);
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setView(R.layout.celldialog);
                    builder.setTitle(currentPair.first);
                    builder.setMessage(currentPair.second);
                    ((ListActivity) view.getContext()).setUrlComplement(currentPair.first); //wtf android? seriously????? at least it works without an interface...
//                    builder.setCancelable(true);
//                    builder.setPositiveButton("More Info", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    ListActivity act = new ListActivity();
//                                    act.OpenWebView("test");
//                                }
//                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        void display(Pair<String, String> pair) {
            currentPair = pair;
            name.setText(pair.first);
            description.setText(pair.second);
        }
    }
}