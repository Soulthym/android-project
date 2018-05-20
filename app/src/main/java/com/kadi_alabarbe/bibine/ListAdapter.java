package com.kadi_alabarbe.bibine;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private JSONArray JArr;
    private ArrayList<Pair<String,String>> Arr = new ArrayList<>();/* = Arrays.asList(
            Pair.create("Lyra Belacqua", "Brave, curious, and crafty, she has been prophesied by the witches to help the balance of life"),
            Pair.create("Pantalaimon", "Lyra's daemon, nicknamed Pan."),
            Pair.create("Roger Parslow", "Lyra's friends"),
            Pair.create("Lord Asriel", "Lyra's uncle"),
            Pair.create("Marisa Coulter", "Intelligent and beautiful, but extremely ruthless and callous."),
            Pair.create("Iorek Byrnison", "Armoured bear, Rightful king of the panserbjørne. Reduced to a slave of the human village Trollesund."),
            Pair.create("Serafina Pekkala", "Witch who closely follows Lyra on her travels."),
            Pair.create("Lee Scoresby", "Texan aeronaut who transports Lyra in his balloon. Good friend with Iorek Byrnison."),
            Pair.create("Ma Costa", "Gyptian woman whose son, Billy Costa is abducted by the \"Gobblers\"."),
            Pair.create("John Faa", "The King of all gyptian people.")
    );*/
    void setJArr(JSONArray Array) {
        JArr = Array;
    }
    public void ConvertToArray(){
        JSONArray jsonArray = JArr;
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++){
                String name;
                String description;
                JSONObject sub;
                try {
                    sub = JArr.getJSONObject(i);
                    Log.d("sub", String.valueOf(sub));
                    try {
                        name = sub.getString("name");
                        Log.d("name", String.valueOf(name));
                        try {
                            description = sub.getString("description");
                            Log.d("description", String.valueOf(description));
                            Pair<String,String> p = new Pair<String, String>(name,description);
                            Arr.add(p);
                            Log.d("ARRAY", String.valueOf(Arr));
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        catch (UnsupportedOperationException e){
                            e.printStackTrace();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } catch (JSONException e){
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView description;

        private Pair<String, String> currentPair;

        public ViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(currentPair.first)
                            .setMessage(currentPair.second)
                            .show();
                }
            });
        }

        public void display(Pair<String, String> pair) {
            currentPair = pair;
            name.setText(pair.first);
            description.setText(pair.second);
        }
    }

}